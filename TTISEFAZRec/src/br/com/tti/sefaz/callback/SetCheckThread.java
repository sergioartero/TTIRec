package br.com.tti.sefaz.callback;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;

import br.com.tti.sefaz.cache.SetDataCache;
import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.SET_STATE;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperProtocol;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperReturnCallBack;

public class SetCheckThread implements Runnable {

	private ManagerInterface manager;

	private XMLMessageFactory xmlFactory;

	private XMLDataCache cacheXml;
	private SetDataCache cacheSet;

	private String numberRecibo;
	private String numberSet;
	private Vector<String> keyXmls;
	private String uf;
	private String cnpj;
	private String ambient;

	private Vector<SefazState> processedStates;
	private Vector<SefazState> nonProcessedStates;
	private Vector<SefazState> autorizedStates;
	private Vector<SefazState> rejectedStates;
	private Vector<SefazState> schemaErroStates;

	private long lastTime;
	private long processTime;

	private boolean tryAgain;

	private int currentTentatives;
	private long currentTime;

	private String header;
	private String data;
	private Hashtable<String, String> prop;

	public SetCheckThread(ManagerInterface manager,
			XMLMessageFactory xmlFactory, XMLDataCache cacheXml,
			SetDataCache cacheSet, String numberRecibo, String numberSet,
			Vector<String> keyXmls, String uf, String ambient, long processTime) {
		super();
		this.manager = manager;
		this.xmlFactory = xmlFactory;
		this.cacheXml = cacheXml;
		this.cacheSet = cacheSet;
		this.numberRecibo = numberRecibo;
		this.numberSet = numberSet;
		this.processTime = processTime;

		this.keyXmls = keyXmls;
		this.uf = uf;
		this.ambient = ambient;

		this.tryAgain = true;

		this.processedStates = States.getINSTANCE().getProcessedStates();
		this.nonProcessedStates = States.getINSTANCE().getNonProcessedStates();
		this.autorizedStates = States.getINSTANCE().getAutorizedStates();
		this.rejectedStates = States.getINSTANCE().getRejectedStates();
		this.schemaErroStates = States.getINSTANCE().getSchemaErrorStates();

		this.createMessages();
	}

	private void createMessages() {
		this.header = this.xmlFactory.createHeader(this.uf, this.ambient,
				SystemProperties.ID_SERVICO_RETRECEPCAO);
		this.data = this.xmlFactory.createCallbackMessage(this.numberRecibo,
				this.uf, this.ambient);

		this.prop = new Hashtable<String, String>();
		this.prop.put("AMBIENT", this.ambient);
		this.prop.put("UF", this.uf);
	}

	private void check() {

		String resultXml = null;

		try {
			resultXml = this.manager.sendXMLMessage(
					SystemProperties.ID_SERVICO_RETRECEPCAO, this.header,
					this.data, this.prop);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		XMLWrapperReturnCallBack result = null;
		try {
			result = XMLWrapperFactory.createReturnCallbackWrapper(resultXml);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SefazState state = this.getSefazState(result);

		MyLogger.getLog().info(
				"Result set: " + this.numberSet + " recibo: "
						+ this.numberRecibo + " code: " + state.getCode()
						+ " xMotive: " + state.getXMotive());

		if (this.processedStates.contains(state)) {

			Vector<XMLWrapperProtocol> protocols = result.getProt();

			for (XMLWrapperProtocol prot : protocols) {
				MyLogger.getLog().info(
						"Processing XML: " + prot.getCh() + " with code: "
								+ prot.getCStat() + " xMotive: "
								+ prot.getXMotivo());

				SefazState stateProt = this.getSefazState(prot);
				if (this.autorizedStates.contains(stateProt)) {
					this.cacheXml.updateAutorizeState(prot.getCh(), prot
							.getProt(), prot.getXmlProtocol(), prot.getCStat(),
							prot.getXMotivo(), prot.getDhSefaz());

					try {
						XMLData xmlData = this.cacheXml.findData(prot.getCh());
						if (xmlData != null)
							this.manager.printXml(xmlData.getKeyXml(), xmlData
									.getXmlString(), xmlData.getModo());
					} catch (Exception e) {
						MyLogger.getLog().log(Level.INFO,
								"Problemas na impressao: " + prot.getCh(), e);
					}
				}

				if (this.rejectedStates.contains(stateProt)) {
					this.cacheXml.updateStateSefaz(prot.getCh(),
							XML_STATE.REJEITADA, Calendar.getInstance()
									.getTime(), prot.getCStat(), prot
									.getXMotivo(), prot.getDhSefaz());
				}

				if (this.schemaErroStates.contains(stateProt)) {
					this.cacheXml.updateStateSefaz(prot.getCh(),
							XML_STATE.ERRO_SCHEMA_XML, Calendar.getInstance()
									.getTime(), prot.getCStat(), prot
									.getXMotivo(), prot.getDhSefaz());
				}
				this.tryAgain = false;
			}

			this.cacheSet.updateCheckState(this.numberSet, result.getCStat(),
					result.getXMotivo(), null);
		}

		if (this.nonProcessedStates.contains(state)) {
			this.tryAgain = true;
		}

		if (this.schemaErroStates.contains(state)) {
			this.cacheSet.updateCheckState(this.numberSet, result.getCStat(),
					result.getXMotivo(), null);
			this.tryAgain = false;
		}

	}

	private SefazState getSefazState(XMLWrapperReturnCallBack result) {
		return new SefazState(Integer.parseInt(result.getCStat().trim()),
				result.getXMotivo());
	}

	private SefazState getSefazState(XMLWrapperProtocol prot) {
		return new SefazState(Integer.parseInt(prot.getCStat().trim()), prot
				.getXMotivo());
	}

	@Override
	public void run() {
		this.currentTentatives = 0;
		this.currentTime = 0;
		this.lastTime = Calendar.getInstance().getTimeInMillis();

		while (tryAgain) {
			MyLogger.getLog().info(
					"Checking set: " + this.numberSet + " recibo: "
							+ this.numberRecibo + " with:"
							+ this.keyXmls.toString());
			this.check();

			this.currentTime = Calendar.getInstance().getTimeInMillis();
			long lapsedTime = this.currentTime - this.lastTime;
			this.lastTime = this.currentTime;
			if (lapsedTime > this.processTime
					|| this.currentTentatives > SystemParameters.MAX_NUMBER_TENTATIVES_CHECK) {
				this.cacheSet.updateState(this.numberSet,
						SET_STATE.TEMPO_PROCESSAMENTO_LIMITE, Calendar
								.getInstance().getTime());
				return;
			} else {
				try {
					Thread.sleep(SystemParameters.INTERVAL_CHECK);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.currentTentatives++;
		}

		MyLogger.getLog().info("Finish checking set: " + this.numberSet);
		this.cacheSet.forceFlushCache();
		this.cacheXml.forceFlushCache();

	}
}
