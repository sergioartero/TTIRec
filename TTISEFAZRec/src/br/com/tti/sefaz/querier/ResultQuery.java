package br.com.tti.sefaz.querier;

import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public class ResultQuery implements XMLWrapperQuery {

	private String cstat;
	private String ch;
	private String dhsefaz;
	private String prot;
	private String tpamb;
	private String xmotivo;
	private String xml;
	private String protocolo;
	private String protCancel;

	public void setCstat(String cstat) {
		this.cstat = cstat;
	}

	public void setCh(String ch) {
		this.ch = ch;
	}

	public void setDhsefaz(String dhsefaz) {
		this.dhsefaz = dhsefaz;
	}

	public void setProt(String prot) {
		this.prot = prot;
	}

	public void setTpamb(String tpamb) {
		this.tpamb = tpamb;
	}

	public void setXmotivo(String xmotivo) {
		this.xmotivo = xmotivo;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	@Override
	public String getCStat() {
		// TODO Auto-generated method stub
		return this.cstat;
	}

	@Override
	public String getCh() {
		// TODO Auto-generated method stub
		return this.ch;
	}

	@Override
	public String getDhSefaz() {
		// TODO Auto-generated method stub
		return this.dhsefaz;
	}

	@Override
	public String getProt() {
		// TODO Auto-generated method stub
		return this.prot;
	}

	@Override
	public String getTpAmb() {
		// TODO Auto-generated method stub
		return this.tpamb;
	}

	@Override
	public String getXMotivo() {
		// TODO Auto-generated method stub
		return this.xmotivo;
	}

	@Override
	public String getXml() {
		// TODO Auto-generated method stub
		return this.xml;
	}

	@Override
	public String getXmlProtocol() {
		// TODO Auto-generated method stub
		return this.protocolo;
	}

	@Override
	public String getProtCancel() {

		return this.protCancel;
	}

	public void setProtCancel(String protCancel) {
		this.protCancel = protCancel;
	}

}
