package br.com.tti.sefaz.xmlgenerate.nfe;

import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;

public class XMLMessageFactoryNFe implements XMLMessageFactory {

	private Hashtable<Vector<String>, Vector<SchemaVersionConfig>> schemasVersion;

	// uf, schema name, value
	private Hashtable<String, Hashtable<String, String>> tableSchemas;

	public XMLMessageFactoryNFe(
			Hashtable<Vector<String>, Vector<SchemaVersionConfig>> schemasVersion) {
		super();
		this.schemasVersion = schemasVersion;
		this.tableSchemas = new Hashtable<String, Hashtable<String, String>>();
		this.processSchemas();
	}

	private void processSchemas() {
		for (Vector<String> ufs : this.schemasVersion.keySet()) {
			Hashtable<String, String> versions = new Hashtable<String, String>();
			Vector<SchemaVersionConfig> table = this.schemasVersion.get(ufs);

			for (SchemaVersionConfig svc : table) {
				versions.put(svc.getSchemaName(), svc.getValue());
			}

			for (String uf : ufs) {
				this.tableSchemas.put(uf, versions);
			}
		}
	}

	@Override
	public String createCallbackMessage(String id, String uf, String ambient) {
		String version = this.tableSchemas.get(uf).get("consReciNFe");
		String tpAmb = this.convertAmbient(ambient);
		String xmlString = "<nfeRetRecepcao2 xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetRecepcao2\"><nfeDadosMsg><consReciNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><nRec>"
				+ id
				+ "</nRec></consReciNFe></nfeDadosMsg></nfeRetRecepcao2>";
		return xmlString;
	}

	@Override
	public String createCallbackMessageXML(String xml, String uf, String ambient) {
		/*
		 * String xmlString =
		 * "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\">"
		 * + xml + "</nfeDadosMsg>";
		 */

		String xmlString = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetRecepcao2\">"
				+ xml + "</nfeDadosMsg>";
		/*
		 * String xmlString =
		 * "<nfeRetRecepcao2 xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetRecepcao2\"><nfeDadosMsg>"
		 * + xml + "</nfeDadosMsg></nfeRetRecepcao2>";
		 */
		return xmlString;
	}

	@Override
	public String createCancelMessage(String keyXml, String protocolNumber,
			String just, String uf, String ambient) {
		String version = this.tableSchemas.get(uf).get("cancNFe");
		String tpAmb = this.convertAmbient(ambient);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cancNFe versao=\""
				+ version
				+ "\" xmlns=\"http://www.portalfiscal.inf.br/nfe\"><infCanc Id=\"ID"
				+ keyXml
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><xServ>CANCELAR</xServ><chNFe>"
				+ keyXml
				+ "</chNFe><nProt>"
				+ protocolNumber
				+ "</nProt><xJust>"
				+ just
				+ "</xJust></infCanc></cancNFe>";

		return xml;
	}

	@Override
	public String createCancelMessageXML(String xml, String uf, String ambient) {
		String soap = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeCancelamento2\">"
				+ xml + "</nfeDadosMsg>";
		return soap;
	}

	@Override
	public String createHeader(String uf, String ambient, String idService) {
		/*
		 * String version = "1.02"; String value = this.getSchemaVersion(uf,
		 * idService); String xmlString =
		 * "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><cabecMsg versao=\""
		 * + version +
		 * "\" xmlns=\"http://www.portalfiscal.inf.br/nfe\"><versaoDados>" +
		 * value + "</versaoDados></cabecMsg>"; return xmlString;
		 */
		String value = this.getSchemaVersion(uf, idService);
		String namespace = this.getNameSpace(idService);
		String xmlString = "<nfeCabecMsg xmlns =" + namespace + ">" + "<cUF>"
				+ uf + "</cUF>" + "<versaoDados>" + value + "</versaoDados>"
				+ "</nfeCabecMsg>";

		/*
		 * String version = "1.02";
		 * 
		 * String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"
		 * standalone=\"yes\"?><cabecMsg versao=\"" + version + "\"
		 * xmlns=\"http://www.portalfiscal.inf.br/nfe\"><versaoDados>" + value +
		 * "</versaoDados></cabecMsg>";
		 */
		return xmlString;
	}

	private String getNameSpace(String idService) {
		if (idService.equals(SystemProperties.ID_SERVICO_RECEPCAO)) {
			return "\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\"";
		}

		if (idService.equals(SystemProperties.ID_SERVICO_RETRECEPCAO)) {
			return "\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetRecepcao2\"";
		}

		if (idService.equals(SystemProperties.ID_SERVICO_CONSULTA)) {
			return "\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta2\"";
		}

		if (idService.equals(SystemProperties.ID_SERVICO_CANCELAMENTO)) {
			return "\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeCancelamento2\"";
		}

		if (idService.equals(SystemProperties.ID_SERVICO_INUTILIZACAO)) {
			return "\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2\"";
		}
		return "";
	}

	@Override
	public String createInutMessage(String uf, String ano, String cnpj,
			String mod, String serie, String ini, String fim, String just,
			String ambient) {
		String version = this.tableSchemas.get(uf).get("inutNFe");
		String tpAmb = this.convertAmbient(ambient);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><inutNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\""
				+ version
				+ "\"><infInut Id=\"ID"
				+ uf
				+ cnpj
				+ mod
				+ serie
				+ ini
				+ fim
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><xServ>INUTILIZAR</xServ><cUF>"
				+ uf
				+ "</cUF><ano>"
				+ ano
				+ "</ano><CNPJ>"
				+ cnpj
				+ "</CNPJ><mod>"
				+ mod
				+ "</mod><serie>"
				+ Integer.parseInt(serie)
				+ "</serie><nNFIni>"
				+ ini
				+ "</nNFIni><nNFFin>"
				+ fim
				+ "</nNFFin><xJust>" + just + "</xJust></infInut></inutNFe>";

		return xml;
	}

	@Override
	public String createInutMessageXML(String xml, String ambient) {
		String soapXML = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2\">"
				+ xml + "</nfeDadosMsg>";
		return soapXML;
	}

	@Override
	public String createQueryXmlMessage(String keyXml, String uf, String ambient) {
		String version = this.tableSchemas.get(uf).get("consSitNFe");
		String tpAmb = this.convertAmbient(ambient);
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><consSitNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ tpAmb
				+ "</tpAmb><xServ>CONSULTAR</xServ><chNFe>"
				+ keyXml
				+ "</chNFe></consSitNFe>";
		return xmlString;
	}

	@Override
	public String createQueryXmlMessageXML(String xml, String uf, String ambient) {
		String xmlString = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeConsulta2\">"
				+ xml + "</nfeDadosMsg>";
		return xmlString;
	}

	@Override
	public String createSendMessageXML(String xmlLote, String uf, String ambient) {
		String xmlString = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\">"
				+ xmlLote + "</nfeDadosMsg>";

		/*
		 * String xmlString =
		 * "<nfeRecepcaoLote2 xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\">"
		 * + "<nfeDadosMsg>" + xmlLote + "</nfeDadosMsg></nfeRecepcaoLote2>";
		 */
		return xmlString;
	}

	@Override
	public String createSendMessage(String id, Vector<String> xmls, String uf,
			String ambient, String idService) {
		String version = this.tableSchemas.get(uf).get("enviNFe");
		String xmlString = "<nfeRecepcaoLote2 xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\">"
				+ "<nfeDadosMsg><enviNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\""
				+ version + "\">";
		xmlString += "<idLote>" + id + "</idLote>";
		for (String xml : xmls) {
			xmlString += xml;
		}
		xmlString += "</enviNFe></nfeDadosMsg></nfeRecepcaoLote2>";
		return xmlString;
	}

	public String convertAmbient(String ambient) {
		if (ambient.equals(SystemProperties.AMBIENT_HOMOLOGACAO))
			return "2";
		if (ambient.equals(SystemProperties.AMBIENT_PRODUCAO))
			return "1";
		return "3";
	}

	public String getSchemaVersion(String uf, String idServ) {
		String schemaName = null;
		if (idServ.equals(SystemProperties.ID_SERVICO_RECEPCAO))
			schemaName = "enviNFev200";
		if (idServ.equals(SystemProperties.ID_SERVICO_RETRECEPCAO))
			schemaName = "consReciNFev200";
		if (idServ.equals(SystemProperties.ID_SERVICO_CONSULTA))
			schemaName = "consSitNFev200";
		if (idServ.equals(SystemProperties.ID_SERVICO_CANCELAMENTO))
			schemaName = "cancNFev200";
		if (idServ.equals(SystemProperties.ID_SERVICO_INUTILIZACAO))
			schemaName = "inutNFev200";

		if (idServ.equals(SystemProperties.ID_SERVICO_RECEPCAO_EVENTO))
			schemaName = "eventoNFev200";

		return this.tableSchemas.get(uf).get(schemaName);
	}

	@Override
	public String createStatusService(String uf, String ambient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createEventXML(String xml, String keyxml, String idevento,
			String uf, String ambient) {
		String soapxml = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">"
				+ xml + "</nfeDadosMsg>";
		return soapxml;
	}

}
