package br.com.tti.sefaz.xmlgenerate.cte;

import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.classes.consulta.cte.TProcEvento;
import br.com.tti.sefaz.classes.consulta.cte.TRetConsSitCTe;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public class CopyOfXMLWrapperQueryCTeImpl implements XMLWrapperQuery {

	private static final long serialVersionUID = 1L;

	private TRetConsSitCTe query;
	private XMLGenerator xmlg;
	private String xmlString;

	private String cstat;
	private String xmotivo;
	private String xmlProtCancel;
	private String dhsefaz;
	private String protocolo;
	
	
	public CopyOfXMLWrapperQueryCTeImpl(String xmlResult) throws Exception {
		super();
		xmlResult = this.getStringXML(xmlResult);

		this.xmlg = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.consulta.cte");
		this.query = ((JAXBElement<TRetConsSitCTe>) this.xmlg
				.toObject(xmlResult)).getValue();
		this.xmlString = xmlResult;

		List<TProcEvento> events = this.query.getProcEventoCTe();
		for (TProcEvento event : events) {
			System.out.println("tipoevent:"
					+ event.getRetEventoCTe().getInfEvento().getTpEvento());
		}
	}

	private String getStringXML(String xml) {
		int pos1 = xml.indexOf("<retConsSitCTe");
		int pos2 = xml.indexOf("</retConsSitCTe>");

		if (pos1 != -1 && pos2 != -1)
			return xml.substring(pos1, pos2 + "</retConsSitCTe>".length());
		return xml;
	}

	@Override
	public String getCStat() {
		return this.query.getCStat();
	}

	@Override
	public String getCh() {
		if (this.query.getProtCTe() != null
				&& this.query.getProtCTe().getInfProt() != null) {
			return this.query.getProtCTe().getInfProt().getChCTe();
		}

		if (this.query.getRetCancCTe() != null
				&& this.query.getRetCancCTe().getInfCanc() != null) {
			return this.query.getRetCancCTe().getInfCanc().getChCTe();
		}

		return null;
	}

	@Override
	public String getDhSefaz() {
		if (this.query.getProtCTe() != null
				&& this.query.getProtCTe().getInfProt() != null) {
			return this.query.getProtCTe().getInfProt().getDhRecbto()
					.toString();
		}

		if (this.query.getRetCancCTe() != null
				&& this.query.getRetCancCTe().getInfCanc() != null) {
			return this.query.getRetCancCTe().getInfCanc().getDhRecbto()
					.toString();
		}

		return null;
	}

	@Override
	public String getProt() {
		if (this.query.getProtCTe() != null
				&& this.query.getProtCTe().getInfProt() != null) {
			return this.query.getProtCTe().getInfProt().getNProt();
		}

		if (this.query.getRetCancCTe() != null
				&& this.query.getRetCancCTe().getInfCanc() != null) {
			return this.query.getRetCancCTe().getInfCanc().getNProt();
		}

		return null;
	}

	@Override
	public String getTpAmb() {
		return this.query.getTpAmb();
	}

	@Override
	public String getXMotivo() {
		return this.query.getXMotivo();
	}

	@Override
	public String getXmlProtocol() {
		if (this.query.getProtCTe() != null
				&& this.query.getProtCTe().getInfProt() != null) {
			return this.query.getProtCTe().getInfProt().getNProt();
		}

		if (this.query.getRetCancCTe() != null
				&& this.query.getRetCancCTe().getInfCanc() != null) {
			return this.query.getRetCancCTe().getInfCanc().getNProt();
		}

		return null;
	}

	@Override
	public String getXml() {
		return this.xmlString;
	}

	@Override
	public String getProtCancel() {
		return this.xmlString;
	}
}
