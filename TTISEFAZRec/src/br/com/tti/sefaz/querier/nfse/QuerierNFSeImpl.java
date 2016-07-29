package br.com.tti.sefaz.querier.nfse;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;

import javax.persistence.Query;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.ConsultaNotaServico;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.remote.CertificatesConfig;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.com.tti.sefaz.sender.campinas.CampinasSenderController;
import br.com.tti.sefaz.sender.saopaulo.SaoPauloSenderController;
import br.com.tti.sefaz.signer.Signer;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.xmlgenerate.XMLQueryCreator;
import br.com.tti.sefaz.xmlgenerate.nfse.SaoPauloXMLQueryCreator;

public class QuerierNFSeImpl implements QuerierNFSe {

	// private DAO<ConsultaNotaServico> daoconsulta;
	private SenderConfig senderConfig;
	private Hashtable<String, SenderGenericController> senders;

	private ResourceBundle bundle;
	private String ambient;
	private String xmlFile;
	private XMLConfigSystem configSystem;
	private Vector<CertificatesConfig> certificates;
	private Signer signer;
	private SimpleDateFormat sdf;
	private String driver;

	public QuerierNFSeImpl(String pfxfile) {
		this.bundle = ResourceBundle.getBundle("configure");
		this.ambient = this.bundle.getString("ambient");
		this.xmlFile = this.bundle.getString("fileconfig");

		String[] args = new String[3];
		args[0] = "-xml";
		args[1] = this.xmlFile;
		args[2] = "-localdb";

		MainParameters.processArguments(args);

		this.configSystem = new XMLConfigSystem(MainParameters.getXml());
		this.configSystem.make();

		DBConfig dbconfig = this.configSystem.getDbConfig();
		this.driver = dbconfig.getDriver();

		this.certificates = this.configSystem.getCertificates();

		String pfxFile = null;
		for (CertificatesConfig cert : this.certificates) {
			if (cert.getPfxFile().equals(pfxfile)) {
				pfxFile = cert.getPfxFile();
			}
		}

		if (pfxFile == null) {
			MyLogger.getLog().info("Certificado nao encontrado:" + pfxfile);
			System.exit(2000);
		}

		// settar pfx certo para o cnpj
		this.configSystem.getSenderConfig().setPfxTransmission(pfxFile);

		this.senderConfig = this.configSystem.getSenderConfig();

		// this.daoconsulta = DaoFactory.createDAO(ConsultaNotaServico.class);
		this.senders = new Hashtable<String, SenderGenericController>();

		this.signer = new Signer(this.certificates);
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");

		MyLogger.getLog().info(
				"Consulta NFS-e versao para NF-e 3.10, CT-e e NFS-e");
	}

	private ConsultaNotaServico findLastQuery(String cnpj, String municipio) {
		try {
			String sql = "";

			if (this.driver.contains("mysql")) {
				sql = "select * from ConsultaNotaServico where cnpjPrestador = '"
						+ cnpj + "' " + " order by dataEnvio desc limit 0,5";
			}
			if (this.driver.contains("jtds")) {
				sql = "select top 5 * from ConsultaNotaServico where cnpjPrestador = '"
						+ cnpj + "' " + " order by dataEnvio desc";
			}

			if (this.driver.contains("oracle")) {
				sql = "select * from ConsultaNotaServico where cnpjPrestador = '"
						+ cnpj
						+ "' and rownum < 5"
						+ " order by dataEnvio desc";
			}

			MyLogger.getLog().info("ConsultaNotaServico sql: " + sql);

			Hashtable<String, Object> props = new Hashtable<String, Object>();
			props.put("class", ConsultaNotaServico.class);

			DAO<ConsultaNotaServico> daoconsulta = DaoFactory
					.createDAO(ConsultaNotaServico.class);
			Query q = daoconsulta.createNativerQuery(sql, props);

			List<ConsultaNotaServico> result = q.getResultList();

			if (result == null || result.isEmpty()) {
				return null;
			}

			ConsultaNotaServico rr = result.get(0);

			MyLogger.getLog().info("usando data final: " + rr.getDataFim());

			daoconsulta.clean();

			return rr;
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);

		}

		return null;

	}

	private XMLQueryCreator getXMLCreator(String municipio) {
		if ("saopaulo".equals(municipio)) {

			return new SaoPauloXMLQueryCreator(this.signer);
		}

		if ("campinas".equals(municipio)) {

		}

		return null;
	}

	private SenderGenericController getSender(String muninicipio) {
		SenderGenericController sender = this.senders.get(muninicipio);

		if (sender == null) {
			if ("saopaulo".equals(muninicipio)) {
				sender = new SaoPauloSenderController(this.senderConfig);
				this.senders.put(muninicipio, sender);
			}

			if ("campinas".equals(muninicipio)) {
				sender = new CampinasSenderController(this.senderConfig);
				this.senders.put(muninicipio, sender);
			}
		}

		return sender;
	}

	@Override
	public <T> T consultarNFSe(String cnpj, String municipio,
			Hashtable<String, Object> params, Class<T> classreturn)
			throws Exception {

		XMLQueryCreator creator = this.getXMLCreator(municipio);
		String xml = this.consultarNFSe(cnpj, municipio, params);
		System.out.println(xml);
		T result = creator.convertResult(xml, classreturn);

		try {
			System.gc();
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		} catch (Throwable t) {
			MyLogger.getLog().log(Level.INFO, t.getLocalizedMessage(), t);
		}

		return result;
	}

	@Override
	public String consultarNFSe(String cnpj, String municipio,
			Hashtable<String, Object> props2abc) throws RemoteException {

		ConsultaNotaServico last = this.findLastQuery(cnpj, municipio);

		Date datainicio = null;
		Date datafinal = null;
		String xml = null;

		{
			if (last == null) {
				try {
					datainicio = this.sdf.parse("01/01/2014");
				} catch (ParseException e) {
					MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(),
							e);
				}
				datafinal = Calendar.getInstance().getTime();
			} else {
				// data de inicio eh a data final da ultima consulta
				datainicio = last.getDataFim();
				datafinal = Calendar.getInstance().getTime();
			}

			Hashtable<String, Object> parameters = new Hashtable<String, Object>();

			parameters.put("dataini", datainicio);
			parameters.put("datafim", datafinal);

			XMLQueryCreator creator = this.getXMLCreator(municipio);

			xml = creator.createXMLQuery(cnpj, municipio, parameters);
		}

		{
			ConsultaNotaServico consulta = new ConsultaNotaServico();

			consulta.setId("consulta" + UUID.randomUUID().toString());
			consulta.setDataEnvio(Calendar.getInstance().getTime());
			consulta.setDataIni(datainicio);
			consulta.setDataFim(datafinal);
			consulta.setRequestObj(xml.getBytes());
			consulta.setMunicipio(municipio);
			consulta.setCnpjPrestador(cnpj);

			DAO<ConsultaNotaServico> daoconsulta = DaoFactory
					.createDAO(ConsultaNotaServico.class);
			daoconsulta.saveEntity(consulta);
			daoconsulta.clean();
		}

		if (xml == null) {
			throw new RemoteException("XML request nao foi criado");
		}

		{
			SenderGenericController sender = this.getSender(municipio);

			String xmlresult = sender.sendXMLMessage(
					SystemProperties.ID_SERVICO_CONSULTA, null, xml, null);

			if (xmlresult == null) {
				throw new RemoteException(
						"Nao foi possivel ter retorno do web service");
			}

			return xmlresult;
		}
	}

	public static void main(String[] args) {
		try {
			String pfxparam = args[0];
			Registry reg = LocateRegistry.getRegistry("localhost", 0);
			QuerierNFSeImpl dest = new QuerierNFSeImpl(pfxparam);
			QuerierNFSe stub = (QuerierNFSe) UnicastRemoteObject.exportObject(
					dest, 0);

			reg.rebind("queriernfse" + pfxparam, stub);
			MyLogger.getLog().info("queriernfse inicializado...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
