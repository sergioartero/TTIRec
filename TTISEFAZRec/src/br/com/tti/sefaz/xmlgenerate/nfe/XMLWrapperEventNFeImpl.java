package br.com.tti.sefaz.xmlgenerate.nfe;

import java.util.Vector;
import java.util.logging.Level;

import br.com.tti.classes.consultaevento.TRetEvento;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperEvent;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperEventProtocol;

public class XMLWrapperEventNFeImpl implements XMLWrapperEvent {

	private String xml;

	private XMLGenerator gen;

	private TRetEvento retevento;

	public XMLWrapperEventNFeImpl(String xml) {
		this.xml = xml;
		try {
			this.gen = new XMLGenerator("br.com.tti.classes.consultaevento");
			this.retevento = (TRetEvento) this.gen.toObject(this.xml);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);

		}
	}

	@Override
	public String getCOrgao() {
		return this.retevento.getInfEvento().getCOrgao();
	}

	@Override
	public String getCStat() {
		return this.retevento.getInfEvento().getCStat();
	}

	@Override
	public String getCh() {
		return this.retevento.getInfEvento().getChNFe();
	}

	@Override
	public String getDhSefaz() {
		return this.retevento.getInfEvento().getDhRegEvento().toString();
	}

	@Override
	public String getEmail() {		
		return this.retevento.getInfEvento().getEmailDest();
	}

	@Override
	public String getNSeqEvento() {		
		return this.retevento.getInfEvento().getNSeqEvento();
	}

	@Override
	public String getProt() {
		return this.retevento.getInfEvento().getNProt();
	}

	@Override
	public String getTpAmb() {
		return this.retevento.getInfEvento().getTpAmb();
	}

	@Override
	public String getXMotivo() {		
		return this.retevento.getInfEvento().getXMotivo();
	}

	@Override
	public String getXmlProtocol() {
		// TODO Auto-generated method stub
		return this.xml;
	}

	@Override
	public Vector<XMLWrapperEventProtocol> getProtocols() {
		return null;
	}

}
