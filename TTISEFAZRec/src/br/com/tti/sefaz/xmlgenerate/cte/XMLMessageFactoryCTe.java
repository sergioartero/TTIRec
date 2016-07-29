package br.com.tti.sefaz.xmlgenerate.cte;

import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;

public class XMLMessageFactoryCTe implements XMLMessageFactory {

	private Hashtable<Vector<String>, Vector<SchemaVersionConfig>> schemasVersion;

	// uf, schema name, value
	private Hashtable<String, Hashtable<String, String>> tableSchemas;

	public XMLMessageFactoryCTe(
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

	private String convertIdService(String ambient) {
		if (ambient.equals(SystemProperties.AMBIENT_HOMOLOGACAO))
			return "2";
		if (ambient.equals(SystemProperties.AMBIENT_PRODUCAO))
			return "1";
		return "3";
	}

	public static String createEmptyMessage(Vector<String> xmls) {
		String version = "1.07";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<enviNFe xmlns=\"http://www.prefeitura.sp.gov.br/nfe\" versao=\""
				+ "1\">";
		for (String str : xmls) {
			xml += str;
		}
		xml += "</enviNFe>";
		return xml;
	}

	@Override
	public String createSendMessageXML(String xmlLote, String uf, String ambient) {
		String version = this.tableSchemas.get(uf).get("enviNFe");
		String xml = "<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao\">";
		xml += xmlLote;
		xml += "</enviCTe></cteDadosMsg>";
		return xml;
	}

	@Override
	public String createSendMessage(String id, Vector<String> xmls, String uf,
			String ambient, String idService) {
		String version = this.tableSchemas.get(uf).get("enviNFe");
		String xml = "<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao\"><enviCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\""
				+ version + "\">";
		xml += "<idLote>" + id + "</idLote>";
		for (String str : xmls) {
			xml += str;
		}
		xml += "</enviCTe></cteDadosMsg>";
		return xml;
	}

	@Override
	public String createCallbackMessage(String id, String uf, String ambient) {
		String version = this.tableSchemas.get(uf).get("consReciNFe");
		String ambientType = this.convertIdService(ambient);

		String xml = "<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteRetRecepcao\"><consReciCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ ambientType
				+ "</tpAmb><nRec>"
				+ id
				+ "</nRec></consReciCTe></cteDadosMsg>";

		return xml;
	}

	@Override
	public String createQueryXmlMessage(String keyXml, String uf, String ambient) {
		String version = this.tableSchemas.get(uf).get("consSitNFe");
		String ambientType = this.convertIdService(ambient);

		String xml = "<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteConsulta\"><consSitCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\""
				+ version
				+ "\"><tpAmb>"
				+ ambientType
				+ "</tpAmb><xServ>CONSULTAR</xServ><chCTe>"
				+ keyXml
				+ "</chCTe></consSitCTe></cteDadosMsg>";
		return xml;
	}

	@Override
	public String createCancelMessage(String keyXml, String protocolNumber,
			String just, String uf, String ambient) {
		String version = this.tableSchemas.get(uf).get("cancNFe");
		String ambientType = this.convertIdService(ambient);

		String xml = "<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteCancelamento\"><cancCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\""
				+ version
				+ "\"><infCanc Id=\"ID"
				+ keyXml
				+ "\"><tpAmb>"
				+ ambientType
				+ "</tpAmb><xServ>CANCELAR</xServ><chCTe>"
				+ keyXml
				+ "</chCTe><nProt>"
				+ protocolNumber
				+ "</nProt><xJust>"
				+ just
				+ "</xJust></infCanc></cancCTe></cteDadosMsg>";
		return xml;
	}

	@Override
	public String createInutMessage(String uf, String ano, String cnpj,
			String mod, String serie, String ini, String fim, String just,
			String ambient) {
		String version = this.tableSchemas.get(uf).get("inutCTe");
		String ambientType = this.convertIdService(ambient);

		String xml = "<inutCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\""
				+ version
				+ "\"><infInut Id=\"ID"
				+ uf
				+ cnpj
				+ mod
				+ serie
				+ ini
				+ fim
				+ "\"><tpAmb>"
				+ ambientType
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
				+ "</nNFFin><xJust>" + just + "</xJust></infInut></inutCTe>";

		return xml;
	}

	private String getNameSpace(String idService) {
		if (idService.equals(SystemProperties.ID_SERVICO_RECEPCAO)) {
			return "\"http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao\"";
		}

		if (idService.equals(SystemProperties.ID_SERVICO_RETRECEPCAO)) {
			return "\"http://www.portalfiscal.inf.br/cte/wsdl/CteRetRecepcao\"";
		}

		if (idService.equals(SystemProperties.ID_SERVICO_CONSULTA)) {
			return "\"http://www.portalfiscal.inf.br/cte/wsdl/CteConsulta\"";
		}

		if (idService.equals(SystemProperties.ID_SERVICO_CANCELAMENTO)) {
			return "\"http://www.portalfiscal.inf.br/cte/wsdl/CteCancelamento\"";
		}
		return "";
	}

	@Override
	public String createHeader(String uf, String ambient, String idService) {
		String value = this.getSchemaVersion(uf, idService);
		String namespace = this.getNameSpace(idService);
		String xmlString = "<cteCabecMsg xmlns =" + namespace + ">" + "<cUF>"
				+ uf + "</cUF>" + "<versaoDados>" + value + "</versaoDados>"
				+ "</cteCabecMsg>";

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

	public String getSchemaVersion(String uf, String idServ) {
		String schemaName = null;
		if (idServ.equals(SystemProperties.ID_SERVICO_RECEPCAO))
			schemaName = "enviNFe";
		if (idServ.equals(SystemProperties.ID_SERVICO_RETRECEPCAO))
			schemaName = "consReciNFe";
		if (idServ.equals(SystemProperties.ID_SERVICO_CONSULTA))
			schemaName = "consSitNFe";
		if (idServ.equals(SystemProperties.ID_SERVICO_CANCELAMENTO))
			schemaName = "cancNFe";
		if (idServ.equals(SystemProperties.ID_SERVICO_INUTILIZACAO))
			schemaName = "inutNFe";

		return this.tableSchemas.get(uf).get(schemaName);
	}

	@Override
	public String createStatusService(String uf, String ambient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createCancelMessageXML(String xml, String uf, String ambient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createInutMessageXML(String xml, String ambient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createQueryXmlMessageXML(String xml, String uf, String ambient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createCallbackMessageXML(String xml, String uf, String ambient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createEventXML(String xml, String keyxml, String idevento,
			String uf, String ambient) {
		// TODO Auto-generated method stub
		return null;
	}

}
