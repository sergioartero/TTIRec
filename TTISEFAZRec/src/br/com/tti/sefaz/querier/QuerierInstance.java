package br.com.tti.sefaz.querier;

import java.io.File;

import java.io.FileWriter;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.xml.rpc.soap.SOAPFaultException;

import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.sender.axis.SenderCallController;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.KeyXmlManager;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;
import br.com.tti.sefaz.xmlgenerate.cte.XMLWrapperQueryCTeImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperQueryNFeEventImpl;

public class QuerierInstance {

	// private SenderDispatchController sender;
	private ResourceBundle bundle;
	private String ambient;
	private String xmlFile;
	private SenderDispatchController sender;
	private SenderCallController axissender;

	public QuerierInstance() {
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

		/*
		 * sender = new SenderPortController(configSystem.getSenderConfig(),
		 * configSystem.getServiceConfig());
		 */

		this.sender = new SenderDispatchController(
				configSystem.getSenderConfig(), configSystem.getServiceConfig());

		this.axissender = new SenderCallController(
				configSystem.getSenderConfig(), configSystem.getServiceConfig());
	}

	public QuerierInstance(String xmlfile) {
		// this.bundle = ResourceBundle.getBundle("configure");
		this.ambient = "producaov200";
		this.xmlFile = xmlfile;

		String[] args = new String[3];
		args[0] = "-xml";
		args[1] = this.xmlFile;
		args[2] = "-localdb";

		MainParameters.processArguments(args);

		XMLConfigSystem configSystem = new XMLConfigSystem(
				MainParameters.getXml());
		configSystem.make();

		this.sender = new SenderDispatchController(
				configSystem.getSenderConfig(), configSystem.getServiceConfig());

	}

	public String convertAmbient(String ambient) {
		if (ambient.startsWith("homolo"))
			return "2";
		if (ambient.startsWith("produ"))
			return "1";
		return "2";
	}

	public String createQueryNFeXmlMessage(String keyXml) {
		String version = this.bundle.getString("versao");
		String tpAmb = this.convertAmbient(this.ambient);
		String xmlString = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta2\"><consSitNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><xServ>CONSULTAR</xServ><chNFe>"
				+ keyXml.replace("NFe", "")
				+ "</chNFe></consSitNFe></nfeDadosMsg>";
		return xmlString;
	}

	public String createQueryNFeV310XmlMessage(String keyXml) {
		String version = "3.10";
		String tpAmb = this.convertAmbient(this.ambient);
		String xmlString = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta\"><consSitNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><xServ>CONSULTAR</xServ><chNFe>"
				+ keyXml.replace("NFe", "")
				+ "</chNFe></consSitNFe></nfeDadosMsg>";
		return xmlString;
	}

	public String createQueryNFeV310XmlMessageWithoutVersion(String keyXml) {
		String version = "3.10";
		String tpAmb = this.convertAmbient(this.ambient);
		String xmlString = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta2\"><consSitNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><xServ>CONSULTAR</xServ><chNFe>"
				+ keyXml.replace("NFe", "")
				+ "</chNFe></consSitNFe></nfeDadosMsg>";
		return xmlString;
	}

	public String createQueryCTeXmlMessage(String keyXml) {
		String version = this.bundle.getString("versaocte");
		String tpAmb = this.convertAmbient(this.ambient);
		String xmlString = "<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteConsulta\"><consSitCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><xServ>CONSULTAR</xServ><chCTe>"
				+ keyXml.replace("CTe", "")
				+ "</chCTe></consSitCTe></cteDadosMsg>";
		return xmlString;
	}

	public String makeQuery(String key) {

		KeyXmlManager keyM = new KeyXmlManager(key);

		String version = this.bundle.getString("versao");
		String cabecalho = "<nfeCabecMsg xmlns =\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta2\">"
				+ "<cUF>"
				+ keyM.getUF()
				+ "</cUF>"
				+ "<versaoDados>"
				+ version
				+ "</versaoDados>" + "</nfeCabecMsg>";
		String dados = this.createQueryNFeXmlMessage(key);

		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", ambient);
		prop.put("UF", keyM.getUF());

		String result = "";
		try {
			MyLogger.getLog().info("Header:" + cabecalho);
			MyLogger.getLog().info("Body:" + dados);
			result = this.sender.sendXMLMessage(
					SystemProperties.ID_SERVICO_CONSULTA, cabecalho, dados,
					prop);
			MyLogger.getLog().info("Result:" + result);
			XMLWrapperQuery prot = XMLWrapperFactory
					.createReturnQueryWrapper(result);

			FileWriter fw = new FileWriter(new File(key + "_canc_prot.xml"));
			fw.write(prot.getXmlProtocol());
			fw.close();

			return prot.getXMotivo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public XMLWrapperQuery makeQueryCTeComplete(String uf, String key) {

		KeyXmlManager keyM = new KeyXmlManager(key);

		String version = this.bundle.getString("versaocte");
		String cabecalho = "<cteCabecMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteConsulta\">"
				+ "<cUF>"
				+ keyM.getUF()
				+ "</cUF>"
				+ "<versaoDados>"
				+ version
				+ "</versaoDados>" + "</cteCabecMsg>";
		String dados = this.createQueryCTeXmlMessage(key);

		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", ambient);

		// colocar UF do SVC
		// svcsp svcrs
		if (keyM.getTpEmis().equals("8")) {
			uf = "svcsp";
		}

		if (keyM.getTpEmis().equals("7")) {
			uf = "svcrs";
		}

		prop.put("UF", uf + "cte");

		String result = "";
		try {
			dados = dados.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
					"");

			MyLogger.getLog().info("Header:" + cabecalho);
			MyLogger.getLog().info("Body:" + dados);
			result = this.sender.sendXMLMessage(
					SystemProperties.ID_SERVICO_CONSULTA, cabecalho, dados,
					prop);
			MyLogger.getLog().info("Result:" + result);
			XMLWrapperQuery prot = new XMLWrapperQueryCTeImpl(result);

			return prot;
		} catch (OutOfMemoryError e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		return null;
	}

	public XMLWrapperQuery makeQueryNFeV310Complete(String uf, String key) {

		KeyXmlManager keyM = new KeyXmlManager(key);

		String version = "3.10";
		String cabecalho = "<nfeCabecMsg xmlns =\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta\">"
				+ "<cUF>"
				+ keyM.getUF()
				+ "</cUF>"
				+ "<versaoDados>"
				+ version
				+ "</versaoDados>" + "</nfeCabecMsg>";

		String dados = "";
		// dont works more
		if (keyM.getUF().equals("29_NAO_FUNCIONA")) {
			dados = this.createQueryNFeV310XmlMessageWithoutVersion(key);
		} else if (keyM.getUF().equals("35") || keyM.getUF().equals("31")) {
			cabecalho = "<nfeCabecMsg xmlns =\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta2\">"
					+ "<cUF>"
					+ keyM.getUF()
					+ "</cUF>"
					+ "<versaoDados>"
					+ version + "</versaoDados>" + "</nfeCabecMsg>";
			dados = this.createQueryNFeV310XmlMessageWithoutVersion(key);

		} else {
			dados = this.createQueryNFeV310XmlMessage(key);
		}

		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", ambient);
		prop.put("UF", uf);

		String result = "";
		try {
			dados = dados.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
					"");

			MyLogger.getLog().info("Header:" + cabecalho);
			MyLogger.getLog().info("Body:" + dados);

			if ("26".equals(uf)) {
				result = this.axissender.sendXMLMessage(
						SystemProperties.ID_SERVICO_CONSULTA + "v310",
						cabecalho, dados, prop);
			} else {
				result = this.sender.sendXMLMessage(
						SystemProperties.ID_SERVICO_CONSULTA + "v310",
						cabecalho, dados, prop);
			}

			MyLogger.getLog().info("Result:" + result);

			XMLWrapperQuery prot = new XMLWrapperQueryNFeEventImpl(result);

			return prot;
		} catch (OutOfMemoryError e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			throw e;
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		return null;
	}

	public XMLWrapperQuery makeQueryNFeComplete(String uf, String key) {

		KeyXmlManager keyM = new KeyXmlManager(key);

		String version = this.bundle.getString("versao");
		String cabecalho = "<nfeCabecMsg xmlns =\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta2\">"
				+ "<cUF>"
				+ keyM.getUF()
				+ "</cUF>"
				+ "<versaoDados>"
				+ version
				+ "</versaoDados>" + "</nfeCabecMsg>";
		String dados = this.createQueryNFeXmlMessage(key);

		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", ambient);

		// colocar UF do SVC
		// svcan svcrs
		if (keyM.getTpEmis().equals("6")) {
			uf = "svcan";
		}

		if (keyM.getTpEmis().equals("7")) {
			uf = "svcrs";
		}

		if (keyM.getTpEmis().equals("3")) {
			uf = "scan";
		}

		prop.put("UF", uf);

		String result = "";
		try {
			dados = dados.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
					"");

			MyLogger.getLog().info("Header:" + cabecalho);
			MyLogger.getLog().info("Body:" + dados);

			if ("26".equals(uf)) {
				result = this.axissender.sendXMLMessage(
						SystemProperties.ID_SERVICO_CONSULTA, cabecalho, dados,
						prop);
			} else {
				result = this.sender.sendXMLMessage(
						SystemProperties.ID_SERVICO_CONSULTA, cabecalho, dados,
						prop);
			}

			MyLogger.getLog().info("Result:" + result);

			XMLWrapperQuery prot = new XMLWrapperQueryNFeEventImpl(result);

			return prot;
		} catch (OutOfMemoryError error) {
			MyLogger.getLog().log(Level.INFO, error.getLocalizedMessage(),
					error);
			throw error;
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		return null;
	}

	public XMLWrapperQuery makeQueryComplete(String uf, String key) {

		if (key.startsWith("CTe")) {
			return this.makeQueryCTeComplete(uf, key);
		} else {

			XMLWrapperQuery rr = null;
			try {
				rr = this.makeQueryNFeComplete(uf, key);
			} catch (OutOfMemoryError error) {
				throw error;
			} catch (Exception ex) {
				MyLogger.getLog().log(Level.INFO, ex.getLocalizedMessage(), ex);
				System.out.println("ex: " + ex);
			}

			if (rr != null) {

				MyLogger.getLog()
						.info("cStat consulta v2.10: " + rr.getCStat());

				if ("217".equals(rr.getCStat()) || "216".equals(rr.getCStat())
						|| "656".equals(rr.getCStat())
						|| "239".equals(rr.getCStat())) {
					MyLogger.getLog()
							.info("**************************************************************");
					MyLogger.getLog()
							.info("**************************************************************");
					MyLogger.getLog()
							.info("NF-e nao encontrada na v2.00 tentando consuta v3.10");
					MyLogger.getLog()
							.info("**************************************************************");
					MyLogger.getLog()
							.info("**************************************************************");
					XMLWrapperQuery rrv310 = this.makeQueryNFeV310Complete(uf,
							key);
					if (rrv310 != null) {
						rr = rrv310;
					}
				}
			} else {

				MyLogger.getLog()
						.info("**************************************************************");
				MyLogger.getLog()
						.info("**************************************************************");
				MyLogger.getLog().info(
						"erro na consulta v2.00 tentando consuta v3.10");
				MyLogger.getLog()
						.info("**************************************************************");
				MyLogger.getLog()
						.info("**************************************************************");

				XMLWrapperQuery rrv310 = this.makeQueryNFeV310Complete(uf, key);
				if (rrv310 != null) {
					rr = rrv310;
				}

			}
			return rr;
		}
		// return null;
		// key = key.replace("NFe", "");
		// return this.makeQueryNFeComplete(key);
	}

	public static void main(String[] args) {
		// MainParameters.processArguments(args);

		QuerierInstance q = new QuerierInstance();
		XMLWrapperQuery prot = q.makeQueryComplete("32",
				"NFe32160111990110000164550010000245061000344060");

		// NOTA NA VERSAO 3.10
		// 32141001771935000800550010000190651014604148
		System.out.println(prot.getProt());
		System.out.println(prot.getDhSefaz());
		System.out.println(prot.getCh());
		System.out.println(prot.getCStat());
		System.out.println(prot.getXMotivo());
		System.out.println(prot.getProtCancel());
	}

	/*
	 * KeyXmlManager keyM = new KeyXmlManager(key); String cabecalho =
	 * "<cteCabecMsg
	 * xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteConsulta\">" + "<cUF>"
	 * + keyM.getUF() + "</cUF>" + "<versaoDados>1.01</versaoDados>" +
	 * "</cte	CabecMsg>";
	 * 
	 * String dados = "<cteDadosMsg
	 * xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteConsulta\"><consSitCTe
	 * xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\"" + "1.01" +
	 * "\"><tpAmb>" + "2" + "</tpAmb><xServ>CONSULTAR</xServ><chCTe>" + key +
	 * "</chCTe></consSitCTe></cteDadosMsg>";
	 */
}
