package br.com.tti.sefaz.cancelinut;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperCancel;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;

public class CancelThread implements Runnable {
	private ManagerInterface manager;

	private XMLDataCache cacheXml;

	private XMLMessageFactory factory;

	private String uf;
	private String ambient;
	private String cnpj;

	private String keyXml;
	private String protocol;
	private String just;

	private Vector<SefazState> canceledStates;
	private Vector<SefazState> alreadyCanceledStates;
	private Vector<SefazState> rejectedStates;
	private Vector<SefazState> schemaErrorStates;

	private String header;
	private String data;
	private Hashtable<String, String> prop;

	private boolean tryAgain;
	private MODO_OP modo;

	public CancelThread(ManagerInterface manager, XMLDataCache cacheXml,
			XMLMessageFactory factory, String cnpj, String uf, String ambient,
			String keyXml, String protocol, String just, MODO_OP modo) {
		super();
		this.manager = manager;
		this.cacheXml = cacheXml;
		this.factory = factory;
		this.protocol = protocol;
		this.uf = uf;
		this.ambient = ambient;
		this.keyXml = keyXml;
		this.just = just;
		this.modo = modo;

		this.cnpj = cnpj;
		this.tryAgain = true;

		this.canceledStates = States.getINSTANCE().getCancelStates();
		this.alreadyCanceledStates = States.getINSTANCE()
				.getAlreadyCancelStates();
		this.rejectedStates = States.getINSTANCE().getRejectedStates();
		this.schemaErrorStates = States.getINSTANCE().getSchemaErrorStates();

		this.createMessage();
	}

	private void createMessage() {
		this.header = this.factory.createHeader(this.uf, this.ambient,
				SystemProperties.ID_SERVICO_CANCELAMENTO);
		this.data = this.factory.createCancelMessage(this.keyXml,
				this.protocol, this.just, this.uf, this.ambient);
		this.prop = new Hashtable<String, String>();
		prop.put("AMBIENT", this.ambient);
		prop.put("UF", this.uf);
	}

	private String signCancel(String xml) {
		int pos1 = xml.indexOf("<cancCTe");
		int pos2 = xml.indexOf("</cteDadosMsg>");

		xml = xml.substring(pos1, pos2);

		try {
			xml = this.manager.signForCNPJ(this.cnpj, xml,
					SystemProperties.ID_SERVICO_CANCELAMENTO);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO,
					"Problems in sign for cancel:" + this.keyXml, e);
		}

		xml = xml.replace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
				"");
		String xmlSigned = "<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteCancelamento\">"
				+ xml + "</cteDadosMsg>";

		return xmlSigned;

	}

	private String makeCancel() throws Exception {
		MyLogger.getLog().info("Making cancel: " + this.keyXml);
		String resultXml = null;

		this.data = this.signCancel(this.data);

		try {
			resultXml = this.manager.sendXMLMessage(
					SystemProperties.ID_SERVICO_CANCELAMENTO, this.header,
					this.data, this.prop);
		} catch (Exception e) {
			if (e instanceof MySenderException) {
				this.tryAgain = true;
			} else {
				this.tryAgain = false;
			}
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, e.getLocalizedMessage());
		}

		XMLWrapperCancel cancel = null;
		try {
			cancel = XMLWrapperFactory.createReturnCancelWrapper(resultXml);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, "XML de retorno mal formada");
		}

		SefazState state = new SefazState(Integer.parseInt(cancel.getCStat()
				.trim()), cancel.getXMotivo());

		MyLogger.getLog()
				.info(
						"XML Cancel: " + this.keyXml + " with status "
								+ cancel.getCStat() + " motive: "
								+ cancel.getXMotivo());

		if (this.canceledStates.contains(state)) {
			this.cacheXml.updateCancelState(this.keyXml,
					cancel.getProtCancel(), cancel.getXmlProtocol(), cancel
							.getCStat(), cancel.getXMotivo(), cancel
							.getDhSefaz(), this.just);
		}

		if (this.alreadyCanceledStates.contains(state)) {
			this.cacheXml.updateState(this.keyXml, XML_STATE.CANCELADA,
					Calendar.getInstance().getTime());
		}

		if (this.rejectedStates.contains(state)) {
			this.cacheXml.updateStateSefaz(this.keyXml,
					XML_STATE.ERRO_CANCELADA, Calendar.getInstance().getTime(),
					cancel.getCStat(), cancel.getXMotivo(), null);
		}

		if (this.schemaErrorStates.contains(state)) {
			this.cacheXml.updateStateSefaz(this.keyXml,
					XML_STATE.ERRO_CANCELADA, Calendar.getInstance().getTime(),
					cancel.getCStat(), cancel.getXMotivo(), null);
		}
		this.tryAgain = false;
		return state.getXMotive();
	}

	public String initCancel() throws Exception {
		String result = null;
		if (this.modo.equals(SystemProperties.MODO_OP.NORMAL))
			result = this.makeCancel();
		else {
			this.cacheXml.updateState(this.keyXml,
					XML_STATE.CANCELADA_CONTINGENCIA, Calendar.getInstance()
							.getTime());
		}
		return result;
	}

	@Override
	public void run() {
		int numberTentatives = 0;
		while (this.tryAgain) {
			try {
				this.initCancel();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (numberTentatives >= SystemParameters.MAX_NUMBER_TENTATIVES_CANCEL_INUT) {
				return;
			}
			numberTentatives++;
		}
		this.cacheXml.forceFlushCache();
	}

}
