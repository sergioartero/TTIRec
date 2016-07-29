package br.com.tti.sefaz.xmlgenerate.cte;

import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.classes.consulta.cte.TProcEvento;
import br.com.tti.sefaz.classes.consulta.cte.TRetConsSitCTe;
import br.com.tti.sefaz.classes.consulta.cte.TRetEvento;
import br.com.tti.sefaz.event.xml.classes.ret.TretEvento;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public class XMLWrapperQueryCTeImpl implements XMLWrapperQuery {

	private static final long serialVersionUID = 1L;

	private TRetConsSitCTe consulta;
	private XMLGenerator xmlg;
	private String xmlresult;

	private String chave;
	private String cstat;
	private String xmotivo;
	private String xmlProtCancel;
	private String dhsefaz;
	private String protocolo;

	public XMLWrapperQueryCTeImpl(String xmlResult) throws Exception {
		super();
		xmlResult = this.getStringXML(xmlResult);

		this.xmlg = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.consulta.cte");
		this.consulta = ((JAXBElement<TRetConsSitCTe>) this.xmlg
				.toObject(xmlResult)).getValue();
		this.xmlresult = xmlResult;

		this.cstat = this.consulta.getCStat();
		this.xmotivo = this.consulta.getXMotivo();

		if (this.consulta.getProtCTe() != null
				&& this.consulta.getProtCTe().getInfProt() != null) {

			this.dhsefaz = this.consulta.getProtCTe().getInfProt()
					.getDhRecbto().toString();
			this.protocolo = this.consulta.getProtCTe().getInfProt().getNProt();

			this.chave = this.consulta.getProtCTe().getInfProt().getChCTe();
		}

		List<TProcEvento> events = this.consulta.getProcEventoCTe();

		if (!events.isEmpty()) {
			for (TProcEvento event : events) {
				TRetEvento eventret = event.getRetEventoCTe();
				if (eventret.getInfEvento().getTpEvento().equals("110111")
						&& eventret.getInfEvento().getCStat().equals("135")) {
					this.cstat = "101";
					this.xmotivo = "Cancelamento NF-e homologado";
					this.xmlProtCancel = eventret.getInfEvento().getNProt()
							+ " " + eventret.getInfEvento().getDhRegEvento();
					// this.protocolo = eventret.getInfEvento().getNProt();
					/*
					 * this.dhsefaz = eventret.getInfEvento().getDhRegEvento()
					 * .toString();
					 */

					this.chave = eventret.getInfEvento().getChCTe();
				}
			}
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
		return this.cstat;
	}

	@Override
	public String getXMotivo() {
		if (!this.cstat.equals("100") && !this.cstat.equals("101")) {
			return "Rejeicão: " + this.xmotivo;
		}

		return this.xmotivo;
	}

	@Override
	public String getCh() {
		return this.chave;
	}

	@Override
	public String getDhSefaz() {
		return this.dhsefaz;
	}

	@Override
	public String getProt() {
		return this.protocolo;
	}

	@Override
	public String getTpAmb() {
		// TODO Auto-generated method stub
		return this.consulta.getTpAmb();
	}

	@Override
	public String getXml() {
		return this.xmlresult;
	}

	@Override
	public String getXmlProtocol() {
		// TODO Auto-generated method stub
		return this.xmlresult;
	}

	@Override
	public String getProtCancel() {
		return this.xmlProtCancel;
	}
}
