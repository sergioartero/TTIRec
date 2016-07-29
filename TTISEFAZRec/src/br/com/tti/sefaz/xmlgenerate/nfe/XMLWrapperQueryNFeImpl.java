package br.com.tti.sefaz.xmlgenerate.nfe;

import java.io.Serializable;

import br.com.tti.nfev200.TRetConsSitNFe;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public class XMLWrapperQueryNFeImpl implements XMLWrapperQuery, Serializable {

	private TRetConsSitNFe query;
	private XMLGenerator xmlg;
	private String xmlString;
	private String xmlProtCancel;

	public XMLWrapperQueryNFeImpl() {
	}

	public XMLWrapperQueryNFeImpl(String xmlResult) throws Exception {
		super();
		xmlResult = this.getStringXML(xmlResult);
		this.xmlg = XMLGenerator.createXMLGenerator("br.com.tti.nfev200");
		this.query = (TRetConsSitNFe) this.xmlg.toObject(xmlResult);
		this.xmlString = xmlResult;
	}

	private String getStringXML(String xml) {
		int pos1 = xml.indexOf("<retConsSitNFe");
		int pos2 = xml.indexOf("</nfeConsultaNF2Result>");

		if (pos2 == -1) {
			pos2 = xml.indexOf("</ns2:nfeConsultaNF2Result>");
		}

		if (pos2 == -1) {
			pos2 = xml.indexOf("</nfeConsultaNFResult>");
		}

		if (pos1 != -1 && pos2 != -1)
			return xml.substring(pos1, pos2);
		return xml;
	}

	@Override
	public String getCStat() {
		return this.query.getCStat();
	}

	@Override
	public String getCh() {
		if (this.query.getProtNFe() != null)
			return this.query.getProtNFe().getInfProt().getChNFe();
		if (this.query.getRetCancNFe() != null)
			return this.query.getRetCancNFe().getInfCanc().getChNFe();
		return null;
	}

	@Override
	public String getDhSefaz() {
		if (this.query.getProtNFe() != null
				&& this.query.getProtNFe().getInfProt().getDhRecbto() != null) {
			return this.query.getProtNFe().getInfProt().getDhRecbto()
					.toString();
		}

		if (this.query.getRetCancNFe() != null
				&& this.query.getRetCancNFe().getInfCanc().getDhRecbto() != null) {
			return this.query.getRetCancNFe().getInfCanc().getDhRecbto()
					.toString();
		}

		return null;
	}

	@Override
	public String getProt() {
		if (this.query.getProtNFe() != null) {
			return this.query.getProtNFe().getInfProt().getNProt();
		}

		if (this.query.getRetCancNFe() != null) {
			return this.query.getRetCancNFe().getInfCanc().getNProt();
		}
		return null;
	}

	@Override
	public String getTpAmb() {
		return this.query.getTpAmb();
	}

	@Override
	public String getXMotivo() {
		if (this.query.getProtNFe() != null)
			return this.query.getProtNFe().getInfProt().getXMotivo();

		if (this.query.getRetCancNFe() != null)
			return this.query.getRetCancNFe().getInfCanc().getXMotivo();

		if (this.query.getCStat().equals("217")) {
			return "Rejeicao: " + this.query.getXMotivo();
		}

		return this.query.getXMotivo();
	}

	@Override
	public String getXmlProtocol() {
		return this.xmlString;
	}

	@Override
	public String getXml() {
		return this.xmlString;
	}

	@Override
	public String getProtCancel() {
		if (this.query.getRetCancNFe() != null
				&& this.query.getRetCancNFe().getInfCanc().getDhRecbto() != null) {
			this.xmlProtCancel = this.query.getRetCancNFe().getInfCanc()
					.getNProt()
					+ " "
					+ this.query.getRetCancNFe().getInfCanc().getDhRecbto()
							.toString();
		}
		return this.xmlProtCancel;
	}
}
