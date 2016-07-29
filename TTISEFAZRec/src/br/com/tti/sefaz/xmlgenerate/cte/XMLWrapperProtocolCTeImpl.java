package br.com.tti.sefaz.xmlgenerate.cte;

import br.com.tti.sefaz.classes.retrecepcao.TProtCTe;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperProtocol;

public class XMLWrapperProtocolCTeImpl implements XMLWrapperProtocol {

	private String xmlProtocol;
	private TProtCTe prot;
	private XMLGenerator gen;

	public XMLWrapperProtocolCTeImpl(TProtCTe prot) {
		super();
		this.prot = prot;
	}

	@Override
	public String getCStat() {
		return this.prot.getInfProt().getCStat();
	}

	@Override
	public String getCh() {
		return this.prot.getInfProt().getChCTe();
	}

	@Override
	public String getDhSefaz() {
		if (this.prot.getInfProt().getDhRecbto() != null)
			return this.prot.getInfProt().getDhRecbto().toString();
		return "";
	}

	@Override
	public String getProt() {
		return this.prot.getInfProt().getNProt();
	}

	@Override
	public String getTpAmb() {
		return this.prot.getInfProt().getTpAmb();
	}

	@Override
	public String getXMotivo() {
		return this.prot.getInfProt().getXMotivo();
	}

	@Override
	public String getXmlProtocol() {
		this.gen = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.retrecepcao");
		try {
			this.xmlProtocol = this.gen.toXMLString(this.prot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.xmlProtocol;
	}

}
