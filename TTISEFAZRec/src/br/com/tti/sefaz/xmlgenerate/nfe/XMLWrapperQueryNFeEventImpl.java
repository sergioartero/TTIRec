package br.com.tti.sefaz.xmlgenerate.nfe;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBElement;

import br.com.tti.classes.consultaevento.TProcEvento;
import br.com.tti.classes.consultaevento.TRetConsSitNFe;
import br.com.tti.classes.consultaevento.TRetEvento;
import br.com.tti.sefaz.event.xml.classes.ret.TretEvento;
import br.com.tti.sefaz.persistence.EventData;
import br.com.tti.sefaz.util.ReadFile;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public class XMLWrapperQueryNFeEventImpl implements XMLWrapperQuery,
		Serializable {

	private TRetConsSitNFe consulta;
	private String xmlresult;
	// private XMLGenerator xmlg;

	private String cstat;
	private String xmotivo;
	private String xmlProtCancel;
	private String dhsefaz;
	private String protocolo;

	public XMLWrapperQueryNFeEventImpl(String xmlresult) throws Exception {
		xmlresult = this.getStringXML(xmlresult);
		System.out.println(xmlresult);
		this.xmlresult = xmlresult;
		XMLGenerator xmlg = XMLGenerator
				.createXMLGenerator("br.com.tti.classes.consultaevento");
		this.consulta = ((JAXBElement<TRetConsSitNFe>) xmlg
				.toObject(this.xmlresult)).getValue();

		this.cstat = this.consulta.getCStat();
		this.xmotivo = this.consulta.getXMotivo();

		if (this.consulta.getProtNFe() != null
				&& this.consulta.getProtNFe().getInfProt() != null) {

			this.dhsefaz = this.consulta.getProtNFe().getInfProt()
					.getDhRecbto().toString();
			this.protocolo = this.consulta.getProtNFe().getInfProt().getNProt();
		}

		List<TProcEvento> events = this.consulta.getProcEventoNFe();

		if (!events.isEmpty()) {
			for (TProcEvento event : events) {
				TretEvento eventret = event.getRetEvento();
				if (eventret.getInfEvento().getTpEvento().equals("110111")
						&& eventret.getInfEvento().getCStat().equals("135")) {
					this.cstat = "101";
					this.xmotivo = "Cancelamento NF-e homologado";
					this.xmlProtCancel = eventret.getInfEvento().getNProt()
							+ " " + eventret.getInfEvento().getDhRegEvento();

					/*
					 * this.dhsefaz = eventret.getInfEvento().getDhRegEvento()
					 * .toString();
					 */
					// this.protocolo = eventret.getInfEvento().getNProt();
				}
			}
		}

	}

	private String getStringXML(String xml) {
		int pos1 = xml.indexOf("<retConsSitNFe");
		int pos2 = xml.indexOf("</nfeConsultaNF2Result>");

		if (pos2 == -1) {
			pos2 = xml.indexOf("</ns2:nfeConsultaNF2Result>");
		}

		if (pos2 == -1) {
			pos2 = xml.indexOf("</ns2:nfeConsultaNFResult>");
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
		return this.consulta.getChNFe();
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

	public static void main(String[] args) throws Exception {
		String xml = ReadFile
				.readFile("D:\\Documents and Settings\\Administrador\\Desktop\\hs_err_pid1088.xml");
		XMLWrapperQueryNFeEventImpl x = new XMLWrapperQueryNFeEventImpl(xml);
		System.out.println(x.getXMotivo());

	}

	@Override
	public String getProtCancel() {
		return xmlProtCancel;
	}

}
