package br.com.tti.sefaz.xmlgenerate;

import java.util.Vector;

public interface XMLMessageFactory {

	// public String createEmptyMessage(Vector<String> xmls);
	public String createHeader(String uf, String ambient, String idService);

	public String createSendMessage(String id, Vector<String> xmls, String uf,
			String ambient, String idService);

	public String createSendMessageXML(String xmlLote, String uf, String ambient);

	public String createCallbackMessage(String id, String uf, String ambient);

	public String createCallbackMessageXML(String xml, String uf, String ambient);

	public String createQueryXmlMessage(String keyXml, String uf, String ambient);

	public String createQueryXmlMessageXML(String xml, String uf, String ambient);

	public String createCancelMessage(String keyXml, String protocolNumber,
			String just, String uf, String ambient);

	public String createCancelMessageXML(String xml, String uf, String ambient);

	public String createInutMessage(String uf, String ano, String cnpj,
			String mod, String serie, String ini, String fim, String just,
			String ambient);

	public String createInutMessageXML(String xml, String ambient);

	public String createStatusService(String uf, String ambient);

	String createEventXML(String xml, String keyxml, String idevento,
			String uf, String ambient);
}
