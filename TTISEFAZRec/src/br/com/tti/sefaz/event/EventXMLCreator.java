package br.com.tti.sefaz.event;

import br.com.tti.sefaz.persistence.EventData;

public class EventXMLCreator {

	private EventData eventoinfo;

	public EventXMLCreator(EventData eventoinfo) {
		super();
		this.eventoinfo = eventoinfo;
	}

	public String extractXML() {
		String ctexml = eventoinfo.getXmlString();
		String protocoloxml = eventoinfo.getProtocoloXML();

		String xml = "<procEventoNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">"
				+ ctexml + protocoloxml + "</procEventoNFe>";
		return xml;
	}
}
