package br.com.tti.sefaz.xmlgenerate.nfe;

import javax.xml.bind.JAXBElement;

import br.com.taragona.nfe.classes.recepcao.TRetEnviNFe;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperReturnSend;

public class XMLWrapperReturnNFeImpl implements XMLWrapperReturnSend {
	private JAXBElement<TRetEnviNFe> ret;

	private XMLGenerator xmlGen;

	private String xml;

	public XMLWrapperReturnNFeImpl(String xmlString) throws Exception {
		this.xmlGen = XMLGenerator
				.createXMLGenerator("br.com.taragona.nfe.classes.recepcao");
		this.xml = this.extractElement(xmlString);
		MyLogger.getLog().info(xmlString);
		ret = (JAXBElement<TRetEnviNFe>) this.xmlGen.toObject(this.xml);
	}

	private String extractElement(String xml) {
		int pos1 = xml.indexOf("<retEnvi");
		int pos2 = xml.indexOf("</nfeRecepcaoLote2Result>");

		return xml.substring(pos1, pos2);
	}

	@Override
	public String getCStat() {
		return this.ret.getValue().getCStat();
	}

	@Override
	public String getNumberRecibo() {
		return this.ret.getValue().getInfRec().getNRec();
	}

	@Override
	public String getTpAmb() {
		return this.ret.getValue().getTpAmb();
	}

	@Override
	public String getXMotivo() {
		return this.ret.getValue().getXMotivo();
	}

	@Override
	public String getDhRecbto() {
		if (this.ret.getValue().getInfRec() != null
				&& this.ret.getValue().getInfRec().getDhRecbto() != null)
			return this.ret.getValue().getInfRec().getDhRecbto().toString();
		return "";
	}

	@Override
	public String getXml() {
		return this.xml;
	}

}
