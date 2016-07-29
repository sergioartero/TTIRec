package br.com.tti.sefaz.xmlgenerate.nfe;

import javax.xml.bind.JAXBElement;

import br.com.taragona.nfe.classes.cancelamento.TRetCancNFe;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperCancel;

public class XMLWrapperCancleNFeImpl implements XMLWrapperCancel {

	private XMLGenerator xmls;
	private TRetCancNFe ret;
	private String xml;

	public XMLWrapperCancleNFeImpl(String xmlResult) throws Exception {
		this.xmls = XMLGenerator
				.createXMLGenerator("br.com.taragona.nfe.classes.cancelamento");
		this.xml = this.extractElement(xmlResult);
		// this.ret = (TRetCancNFe) this.xmls.toObject(this.xml);
	}

	private String extractElement(String xml) {
		int pos1 = xml.indexOf("<retCanc");
		int pos2 = xml.indexOf("</nfeCancelamentoNF");

		xml = xml.substring(pos1, pos2);

		// xml = xml.replace("xmlns=\"http://www.portalfiscal.inf.br/nfe\"",
		// "");

		return xml;
	}

	@Override
	public String getCStat() {
		return this.ret.getInfCanc().getCStat();
	}

	@Override
	public String getCh() {
		return this.ret.getInfCanc().getChNFe();
	}

	@Override
	public String getDhSefaz() {
		// TODO Auto-generated method stub
		return this.ret.getInfCanc().getDhRecbto().toString();
	}

	@Override
	public String getProtCancel() {
		// TODO Auto-generated method stub
		return this.ret.getInfCanc().getNProt();
	}

	@Override
	public String getTpAmb() {
		// TODO Auto-generated method stub
		return this.ret.getInfCanc().getTpAmb();
	}

	@Override
	public String getXMotivo() {
		// TODO Auto-generated method stub
		return this.ret.getInfCanc().getXMotivo();
	}

	@Override
	public String getXmlProtocol() {
		// TODO Auto-generated method stub
		return this.xml;
	}

	@Override
	public String getXml() {
		return this.xml;
	}

}
