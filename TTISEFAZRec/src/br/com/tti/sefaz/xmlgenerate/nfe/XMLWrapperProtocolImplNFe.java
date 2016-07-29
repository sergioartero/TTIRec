package br.com.tti.sefaz.xmlgenerate.nfe;

import br.com.taragona.nfe.classes.retrecepcao.TProtNFe;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperProtocol;

public class XMLWrapperProtocolImplNFe implements XMLWrapperProtocol {

	private String xmlString;
	private TProtNFe prot;

	public XMLWrapperProtocolImplNFe(TProtNFe prot) {
		this.prot = prot;
	}

	@Override
	public String getCStat() {
		return this.prot.getInfProt().getCStat();
	}

	@Override
	public String getCh() {
		return this.prot.getInfProt().getChNFe();
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
		String mot = this.prot.getInfProt().getXMotivo();
		mot = mot.replace("NF", "CT");
		return mot;
	}

	@Override
	public String getXmlProtocol() {
		return this.xmlString;
	}

}
