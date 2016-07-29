package br.com.tti.sefaz.xmlgenerate.cte;

import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.classes.cancelamento.TRetCancCTe;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperCancel;

public class XMLWrapperCancelCTeImpl implements XMLWrapperCancel {

	private TRetCancCTe cancel;
	private XMLGenerator gen;

	public XMLWrapperCancelCTeImpl(String xml) throws Exception {
		this.gen = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.cancelamento");
		xml = this.getStringXML(xml);
		this.cancel = ((JAXBElement<TRetCancCTe>) this.gen.toObject(xml))
				.getValue();
	}

	private String getStringXML(String xml) {
		int pos1 = xml.indexOf("<retCancCTe");
		int pos2 = xml.indexOf("</cteCancelamentoCTResult>");

		if (pos1 != -1 && pos2 != -1)
			return xml.substring(pos1, pos2);
		return xml;
	}

	@Override
	public String getCStat() {
		return this.cancel.getInfCanc().getCStat();
	}

	@Override
	public String getCh() {
		return this.cancel.getInfCanc().getChCTe();
	}

	@Override
	public String getDhSefaz() {
		return this.cancel.getInfCanc().getDhRecbto().toString();
	}

	@Override
	public String getProtCancel() {
		return this.cancel.getInfCanc().getNProt();
	}

	@Override
	public String getTpAmb() {
		return this.cancel.getInfCanc().getTpAmb();
	}

	@Override
	public String getXMotivo() {
		return this.cancel.getInfCanc().getXMotivo();
	}

	@Override
	public String getXmlProtocol() {
		return null;
	}

	@Override
	public String getXml() {
		// TODO Auto-generated method stub
		return null;
	}

}
