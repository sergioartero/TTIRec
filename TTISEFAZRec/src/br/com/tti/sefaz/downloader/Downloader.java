package br.com.tti.sefaz.downloader;

import java.io.FileWriter;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.remote.CertificatesConfig;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.KeyXmlManager;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperRetNFeDown;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperRetNFeDownImpl;

public class Downloader implements DownloaderInterface {

	// private DownloaderController controller;
	private ResourceBundle bundle;
	private String ambient;
	private String xmlFile;
	private SenderDispatchController sender;

	// private EventManagerImpl eventm;

	public Downloader(String pfxfile) {
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

		MyLogger.getLog()
				.info("Downloader versao para NF-e 3.10, CT-e e NFS-e");
		// this.eventm = new EventManagerImpl(this.sender);
	}

	/*
	 * private SimpleDateFormat sdf = new SimpleDateFormat(
	 * "yyyy-MM-dd'T'HH:mm:00'-02:00'");
	 */
	@Override
	public XMLWrapperRetNFeDown downloadNFeWithProtocol(String keyxml,
			Hashtable<String, Object> props) throws Exception {
		MyLogger.getLog().info(
				"confirm NF-e:" + keyxml + " params: " + props.toString());

		/*
		 * this.eventm.sendConfirmationDest(keyxml, "", props.get("cnpjdest")
		 * .toString());
		 */

		// long currentlonf = Calendar.getInstance().getTimeInMillis();
		// - (60 * 60 * 1000L);
		// Date currentdata = new Date(currentlonf);
		// String datahora = this.sdf.format(currentdata);

		/*
		 * this.eventm.sendEvent(keyxml, TIPO_EVENTO.CIENCIA_OP.getCodigo(),
		 * TIPO_EVENTO.CIENCIA_OP, TIPO_EVENTO.CIENCIA_OP.getMensagem(), "",
		 * datahora, props.get("cnpjdest").toString());
		 */

		MyLogger.getLog().info(
				"download NF-e:" + keyxml + " params: " + props.toString());

		KeyXmlManager keyM = new KeyXmlManager(keyxml.replace("NFe", "")
				.replace("CTe", ""));

		String cnpjdest = props.get("cnpjdest").toString();

		String header = this.createDownloadHeader(keyM.getUF());
		String data = this.createDownloaderNFeXmlMessage(keyxml, cnpjdest);

		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", ambient);
		prop.put("UF", "91");

		// MyLogger.getLog().info("Header:" + header);
		// MyLogger.getLog().info("Body:" + data);

		String result = this.sender.sendXMLMessage(
				SystemProperties.ID_SERVICO_DOWNLOAD_NF, header, data, prop);

		// MyLogger.getLog().info("Results:" + result);

		XMLWrapperRetNFeDown prot = XMLWrapperFactory.createRetNFeDown(result);

		MyLogger.getLog().info(
				"prot cstat: " + prot.getCStat() + " xmotivo:"
						+ prot.getXMotivo());

		return prot;
	}

	@Override
	public String downloadNFe(String keyxml, Hashtable<String, Object> props)
			throws Exception {
		MyLogger.getLog().info(
				"confirm NF-e:" + keyxml + " params: " + props.toString());

		/*
		 * this.eventm.sendConfirmationDest(keyxml, "", props.get("cnpjdest")
		 * .toString());
		 */

		// long currentlonf = Calendar.getInstance().getTimeInMillis();
		// - (60 * 60 * 1000L);
		// Date currentdata = new Date(currentlonf);
		// String datahora = this.sdf.format(currentdata);

		/*
		 * this.eventm.sendEvent(keyxml, TIPO_EVENTO.CIENCIA_OP.getCodigo(),
		 * TIPO_EVENTO.CIENCIA_OP, TIPO_EVENTO.CIENCIA_OP.getMensagem(), "",
		 * datahora, props.get("cnpjdest").toString());
		 */

		MyLogger.getLog().info(
				"download NF-e:" + keyxml + " params: " + props.toString());

		KeyXmlManager keyM = new KeyXmlManager(keyxml.replace("NFe", "")
				.replace("CTe", ""));

		String cnpjdest = props.get("cnpjdest").toString();

		String header = this.createDownloadHeader(keyM.getUF());
		String data = this.createDownloaderNFeXmlMessage(keyxml, cnpjdest);

		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", ambient);
		prop.put("UF", "91");

		try {
			// MyLogger.getLog().info("Header:" + header);
			// MyLogger.getLog().info("Body:" + data);

			String result = this.sender
					.sendXMLMessage(SystemProperties.ID_SERVICO_DOWNLOAD_NF,
							header, data, prop);

			// MyLogger.getLog().info("Results:" + result);

			XMLWrapperRetNFeDown prot = XMLWrapperFactory
					.createRetNFeDown(result);

			String cstat = prot.getCStat();
			MyLogger.getLog().info("cStat:" + cstat);
			MyLogger.getLog().info("xMotivo:" + prot.getXMotivo());

			if ("140".equals(cstat)) {
				if (props.containsKey("fileout")) {
					try {
						MyLogger.getLog().info(
								"salvando XML baixado no arquivo:"
										+ props.get("fileout").toString());
						String fileout = props.get("fileout").toString();
						FileWriter fw = new FileWriter(fileout);
						fw.write(prot.getProcNFe());
						fw.close();
					} catch (Exception e) {
					}
				}
				return prot.getProcNFe();
			} else {
				if (props.containsKey("fileout")) {
					try {
						MyLogger.getLog().info(
								"salvando erro baixado XML:"
										+ props.get("fileout").toString());
						String fileout = props.get("fileout").toString();
						FileWriter fw = new FileWriter(fileout);
						fw.write(prot.getXMotivo());
						fw.close();
					} catch (Exception e) {
					}
				}
				return prot.getXMotivo();
			}

		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		return null;
	}

	public String createDownloaderNFeXmlMessage(String keyXml, String cnpjdest) {
		String version = this.bundle.getString("versaodownload");
		String tpAmb = this.convertAmbient(this.ambient);
		String xmlString = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeDownloadNF\"><downloadNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><xServ>DOWNLOAD NFE</xServ><CNPJ>"
				+ cnpjdest
				+ "</CNPJ><chNFe>"
				+ keyXml.replace("NFe", "")
				+ "</chNFe></downloadNFe></nfeDadosMsg>";
		return xmlString;
	}

	public String createDownloadHeader(String uf) {
		String version = this.bundle.getString("versaodownload");
		String cabecalho = "<nfeCabecMsg xmlns =\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeDownloadNF\">"
				+ "<cUF>"
				+ uf
				+ "</cUF>"
				+ "<versaoDados>"
				+ version
				+ "</versaoDados>" + "</nfeCabecMsg>";
		return cabecalho;
	}

	public String convertAmbient(String ambient) {
		if (ambient.startsWith("homolo"))
			return "2";
		if (ambient.startsWith("produ"))
			return "1";
		return "2";
	}

	/*
	 * public static void main2(String[] args) { try { Downloader d = new
	 * Downloader( "C:\\TTIRec\\certificados\\tome2013.pfx"); Hashtable<String,
	 * Object> props = new Hashtable<String, Object>(); props.put("cnpjdest",
	 * "11245802000188"); String xml = d.downloadNFe(
	 * "35130111245802000188550020000104751010791918", props);
	 * System.out.println(xml); } catch (Exception e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } }
	 */

	public static void main(String[] args) {
		try {
			Downloader d = new Downloader(args[0]);
			Hashtable<String, Object> props = new Hashtable<String, Object>();
			props.put("cnpjdest", args[1]);
			props.put("fileout", args[3]);
			String xml = d.downloadNFe(args[2], props);
			System.out.println(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isAlive() throws Exception {
		return true;
	}
}
