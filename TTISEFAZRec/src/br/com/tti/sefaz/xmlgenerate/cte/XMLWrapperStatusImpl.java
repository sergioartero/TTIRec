package br.com.tti.sefaz.xmlgenerate.cte;

import br.com.taragona.nfe.classes.estadoservicos.TConsStatServ;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperStatus;

public class XMLWrapperStatusImpl implements XMLWrapperStatus {

	private TConsStatServ stat;
	private XMLGenerator xmls;

	public XMLWrapperStatusImpl(String xml) throws Exception {
		this.xmls = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.status");
		this.stat = (TConsStatServ) this.xmls.toObject(xml);
	}

	@Override
	public String getCStat() {
		return this.stat.getXServ();
	}

	@Override
	public String getXMotivo() {
		return this.stat.getXServ();
	}

}
