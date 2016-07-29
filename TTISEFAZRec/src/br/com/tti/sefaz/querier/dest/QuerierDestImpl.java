package br.com.tti.sefaz.querier.dest;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;

import javax.persistence.Query;

import br.com.tti.sefaz.consultadest.ResCCe;
import br.com.tti.sefaz.consultadest.ResCanc;
import br.com.tti.sefaz.consultadest.ResNFe;
import br.com.tti.sefaz.consultadest.TConsNFeDest;
import br.com.tti.sefaz.consultadest.TRetConsNFeDest;
import br.com.tti.sefaz.downloader.Downloader;
import br.com.tti.sefaz.downloader.DownloaderInterface;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.ConsultaDest;
import br.com.tti.sefaz.persistence.ConsultaNotaServico;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.remote.CertificatesConfig;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.signer.Signer;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.xml.XMLGenerator;

public class QuerierDestImpl implements QuerierDest {

	// private XMLGenerator gen;

	private SenderDispatchController sender;

	private ResourceBundle bundle;

	private String ambient;

	private String xmlFile;
	// private DAO<ConsultaDest> daoconsdest;

	private String driver;

	public QuerierDestImpl(String pfxfile) {
		this.bundle = ResourceBundle.getBundle("configure");
		this.ambient = this.bundle.getString("ambient");
		this.xmlFile = this.bundle.getString("fileconfig");

		String[] args = new String[3];
		args[0] = "-xml";
		args[1] = this.xmlFile;
		args[2] = "-localdb";

		MainParameters.processArguments(args);

		XMLConfigSystem configSystem = new XMLConfigSystem(
				MainParameters.getXml());
		configSystem.make();

		Vector<CertificatesConfig> certificates = configSystem
				.getCertificates();

		String pfxFile = null;
		for (CertificatesConfig cert : certificates) {
			if (cert.getPfxFile().equals(pfxfile)) {
				pfxFile = cert.getPfxFile();
			}
		}

		this.driver = configSystem.getDbConfig().getDriver();

		/*
		 * sender = new SenderPortController(configSystem.getSenderConfig(),
		 * configSystem.getServiceConfig());
		 */

		if (pfxFile == null) {
			MyLogger.getLog().info("Certificado nao encontrado:" + pfxfile);
			System.exit(2000);
		}

		// settar pfx certo para o cnpj
		configSystem.getSenderConfig().setPfxTransmission(pfxFile);

		this.sender = new SenderDispatchController(
				configSystem.getSenderConfig(), configSystem.getServiceConfig());

		this.sender = new SenderDispatchController(
				configSystem.getSenderConfig(), configSystem.getServiceConfig());

		// this.daoconsdest = DaoFactory.createDAO(ConsultaDest.class);
		MyLogger.getLog().info(
				"Consulta NF-e versao para NF-e 3.10, CT-e e NFS-e");
	}

	public QuerierDestImpl(SenderDispatchController sender) {
		super();
		this.bundle = ResourceBundle.getBundle("configure");
		this.ambient = this.bundle.getString("ambient");
		this.xmlFile = this.bundle.getString("fileconfig");

		this.sender = sender;

		// this.gen = new XMLGenerator("br.com.tti.sefaz.consultadest");
		// this.daoconsdest = DaoFactory.createDAO(ConsultaDest.class);
		MyLogger.getLog().info(
				"Consulta NF-e versao para NF-e 3.10, CT-e e NFS-e");
	}

	public String createEventHeader(String uf) {
		String version = this.bundle.getString("versaoconsultadest");
		String cabecalho = "<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsultaDest\">"
				+ "<cUF>"
				+ uf
				+ "</cUF>"
				+ "<versaoDados>"
				+ version
				+ "</versaoDados>" + "</nfeCabecMsg>";
		return cabecalho;
	}

	private String calculateUltNSU(String cnpj) {
		try {
			String sql = "";

			if (this.driver.contains("mysql")) {
				sql = "select * from CONSULTADEST where cnpj = '" + cnpj + "' "
						+ " order by dataproc desc limit 0,5";
			}
			if (this.driver.contains("jtds")) {
				sql = "select top 5 * from ConsultaDest where cnpj = '" + cnpj
						+ "' " + " order by dataproc desc";
			}

			if (this.driver.contains("oracle")) {
				sql = "select * from ConsultaDest where cnpj = '" + cnpj
						+ "' and rownum < 5" + " order by dataproc desc";
			}

			MyLogger.getLog().info("calculateUltNSU sql: " + sql);

			Hashtable<String, Object> props = new Hashtable<String, Object>();
			props.put("class", ConsultaDest.class);

			DAO<ConsultaDest> daoconsdest = DaoFactory
					.createDAO(ConsultaDest.class);
			Query query = daoconsdest.createNativerQuery(sql, props);

			List<ConsultaDest> result = query.getResultList();

			if (result != null && !result.isEmpty()) {
				MyLogger.getLog().info("size:" + result.size());

				ConsultaDest rr = result.get(0);
				MyLogger.getLog()
						.info("UltNSU:" + rr.getResposta().getUltNSU());
				MyLogger.getLog().info("Data Proc:" + rr.getDataproc());

				String number = rr.getResposta().getUltNSU();

				daoconsdest.clean();

				return number;
			}
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);

		}
		return "0";
	}

	@Override
	public TRetConsNFeDest consultarNFeDest(String cnpj,
			Hashtable<String, Object> params) throws RemoteException {
		TConsNFeDest consdest = new TConsNFeDest();
		consdest.setCNPJ(cnpj);
		consdest.setIndEmi("1");
		consdest.setIndNFe("0");
		consdest.setTpAmb((this.ambient.equals("homologacao") ? "2" : "1"));
		consdest.setUltNSU(this.calculateUltNSU(cnpj));
		consdest.setVersao(this.bundle.getString("versaoconsultadest"));
		consdest.setXServ("CONSULTAR NFE DEST");

		if (consdest.getUltNSU() == null || consdest.getUltNSU().isEmpty()) {
			consdest.setUltNSU("0");
		}

		XMLGenerator gen = new XMLGenerator("br.com.tti.sefaz.consultadest");
		String xml = null;
		try {
			xml = gen.toXMLString(consdest);
		} catch (Exception e) {
			throw new RemoteException(e.getLocalizedMessage(), e);
		}
		xml = xml
				.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
						"");
		MyLogger.getLog().info(xml);

		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("UF", "91");
		props.put("AMBIENT", this.ambient);

		String header = this.createEventHeader("35");
		String res = this.sender
				.sendXMLMessage(
						SystemProperties.ID_SERVICO_CONSULTA_DEST,
						header,
						"<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsultaDest\">"
								+ xml + "</nfeDadosMsg>", props);

		int pos1 = res.indexOf("<retConsNFeDest");
		int pos2 = res.indexOf("</nfeConsultaNFDestResult>");

		if (pos1 != -1 && pos2 != -1) {
			res = res.substring(pos1, pos2);
		}

		TRetConsNFeDest response = null;
		try {
			response = (TRetConsNFeDest) gen.toObject(res);
		} catch (Exception e) {
			throw new RemoteException(e.getLocalizedMessage(), e);
		}

		Vector<ResNFe> resnfe = new Vector<ResNFe>();
		Vector<ResCanc> rescanc = new Vector<ResCanc>();
		Vector<ResCCe> rescce = new Vector<ResCCe>();
		if (response != null && response.getRet() != null) {
			for (TRetConsNFeDest.Ret ret : response.getRet()) {
				if (ret.getResNFe() != null) {
					resnfe.add(ret.getResNFe());
				}
				if (ret.getResCanc() != null) {
					rescanc.add(ret.getResCanc());
				}
				if (ret.getResCCe() != null) {
					rescce.add(ret.getResCCe());
				}
			}
		}

		ConsultaDest cons = new ConsultaDest();
		cons.setConsulta(consdest);
		cons.setResposta(response);
		cons.setDataproc(Calendar.getInstance().getTime());

		DAO<ConsultaDest> daoconsdest = DaoFactory
				.createDAO(ConsultaDest.class);
		daoconsdest.saveEntity(cons);
		daoconsdest.clean();

		/*
		 * Hashtable<String, Object> results = new Hashtable<String, Object>();
		 * 
		 * results.put("resnfe", resnfe); results.put("rescanc", rescanc);
		 * results.put("rescce", rescce); results.put("reponse", response);
		 */

		try {
			System.gc();
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		} catch (Throwable t) {
			MyLogger.getLog().log(Level.INFO, t.getLocalizedMessage(), t);
		}

		return response;

	}

	public static void main(String[] args) {
		try {
			String pfxparam = args[0];
			Registry reg = LocateRegistry.getRegistry("localhost", 0);
			QuerierDest dest = new QuerierDestImpl(pfxparam);
			QuerierDest stub = (QuerierDest) UnicastRemoteObject.exportObject(
					dest, 0);

			reg.rebind("querierdest" + pfxparam, stub);
			MyLogger.getLog().info("querierdest inicializado...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
