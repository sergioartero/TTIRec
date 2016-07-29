package br.com.tti.sefaz.xmlgenerate.nfe;

import javax.xml.bind.JAXBElement;

import br.com.taragona.nfe.classes.inutilizacao.TRetInutNFe;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperInut;

public class XMLWrapperInutNFeImpl implements XMLWrapperInut {

	private String xml;
	private XMLGenerator xmls;
	private JAXBElement<TRetInutNFe> ret;

	public XMLWrapperInutNFeImpl(String xml) throws Exception {
		this.xml = xml;

		this.xml = this.extractElement(this.xml);
		this.xmls = XMLGenerator
				.createXMLGenerator("br.com.taragona.nfe.classes.inutilizacao");
		this.ret = (JAXBElement<TRetInutNFe>) this.xmls.toObject(this.xml);
	}

	private String extractElement(String xml) {
		int pos1 = xml.indexOf("<retInutNF");
		int pos2 = xml.indexOf("</nfeInutilizacaoNF2Result>");

		return xml.substring(pos1, pos2);
	}

	@Override
	public String getCStat() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getInfInut().getCStat();
	}

	@Override
	public String getDhSefaz() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getInfInut().getDhRecbto().toString();
	}

	@Override
	public String getProtInut() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getInfInut().getNProt();
	}

	@Override
	public String getTpAmb() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getInfInut().getTpAmb();
	}

	@Override
	public String getXMotivo() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getInfInut().getXMotivo();
	}

	@Override
	public String getXmlProtocol() {
		// TODO Auto-generated method stub
		return this.xml;
	}

	@Override
	public String getNFim() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getInfInut().getNNFFin();
	}

	@Override
	public String getNIni() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getInfInut().getNNFIni();
	}

	@Override
	public String getXml() {
		return this.xml;
	}

}
