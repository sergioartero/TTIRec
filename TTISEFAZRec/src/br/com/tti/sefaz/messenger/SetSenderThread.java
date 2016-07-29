package br.com.tti.sefaz.messenger;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.cache.SetDataCache;
import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.RECIBE_STATE;
import br.com.tti.sefaz.systemconfig.SystemProperties.SET_STATE;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.util.IDSetManager;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperReturnSend;

public class SetSenderThread implements Runnable {

	private ManagerInterface manager;

	private String numberRecibo;
	private String numberSet;
	private Vector<String> xmls;
	private Vector<String> keyXmls;
	private String uf;
	private String cnpj;
	private String ambient;

	private XMLDataCache cacheXml;
	private SetDataCache cacheSet;

	private XMLMessageFactory xmlFactory;

	private long currentTime;
	private int currentTentative;

	private Vector<SefazState> sendedStates;
	private Vector<SefazState> noSendedStates;
	private Vector<SefazState> schemaErroStates;

	private boolean tryAgain;

	private String header;
	private String data;
	private Hashtable<String, String> prop;

	public SetSenderThread(ManagerInterface manager,
			XMLMessageFactory xmlFactory, XMLDataCache cacheXml,
			SetDataCache cacheSet, Vector<String> xmls, Vector<String> keyXmls,
			String cnpj, String uf, String ambient) {
		super();
		this.manager = manager;
		this.cacheXml = cacheXml;
		this.cacheSet = cacheSet;
		this.cnpj = cnpj;
		this.ambient = ambient;
		this.xmlFactory = xmlFactory;
		this.tryAgain = true;
		this.numberSet = IDSetManager.getNexIdSet().toString();
		this.uf = uf;

		this.sendedStates = States.getINSTANCE().getSendedStates();
		this.noSendedStates = States.getINSTANCE().getNoSendedStates();
		this.schemaErroStates = States.getINSTANCE().getSchemaErrorStates();

		this.xmls = new Vector<String>();
		this.keyXmls = new Vector<String>();

		for (String key : keyXmls) {
			this.keyXmls.add(key);
		}

		for (String xml : xmls) {
			this.xmls.add(xml);
		}

		this.createSet();
		this.createMessages();
	}

	private void createSet() {
		Date currentDate = Calendar.getInstance().getTime();

		SetData set = new SetData();
		// set.setNumberSet("2");
		set.setAmbient(this.ambient);
		set.setProcessSet(false);
		set.setCnpj(this.cnpj);
		set.setLastDateUpdate(currentDate);
		set.setNumberSet(this.numberSet);
		set.setState(SET_STATE.GERADO);
		set.setStateRecibe(RECIBE_STATE.PENDENTE);
		set.setLastTentative(currentDate);
		set.setNumeroTentativa(0);

		this.cacheSet.saveSet(set);
	}

	private void createMessages() {
		this.header = this.xmlFactory.createHeader(this.uf, this.ambient,
				SystemProperties.ID_SERVICO_RECEPCAO);

		this.data = this.xmlFactory.createSendMessage(this.numberSet,
				this.xmls, this.uf, this.ambient,
				SystemProperties.ID_SERVICO_RECEPCAO);

		this.prop = new Hashtable<String, String>();
		prop.put("AMBIENT", this.ambient);
		prop.put("UF", this.uf);
	}

	synchronized private void send() {
		for (String key : this.keyXmls) {
			this.cacheXml.updateState(key, XML_STATE.TENTANDO_ENVIO, Calendar
					.getInstance().getTime());
		}

		this.cacheSet.updateState(this.numberSet, SET_STATE.TENTANDO_ENVIO,
				Calendar.getInstance().getTime());

		String resultXml = null;
		try {
			resultXml = manager.sendXMLMessage(
					SystemProperties.ID_SERVICO_RECEPCAO, this.header,
					this.data, this.prop);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof MySenderException) {
				MySenderException ms = MySenderException.class.cast(e);
				for (String keyXml : this.keyXmls) {
					this.cacheXml.updateState(keyXml,
							XML_STATE.ERRO_COMUNICACAO_SEFAZ, Calendar
									.getInstance().getTime());
				}
				this.tryAgain = true;
			} else {
				for (String keyXml : this.keyXmls) {
					this.cacheXml.updateState(keyXml,
							XML_STATE.ERRO_COMUNICACAO_LOCAL, Calendar
									.getInstance().getTime());
				}
				this.tryAgain = false;
			}
		}

		XMLWrapperReturnSend recibe = null;
		try {
			recibe = XMLWrapperFactory.createReturnSendWrapper(resultXml);
		} catch (Exception e) {
			e.printStackTrace();
			this.tryAgain = false;
		}

		if (recibe == null) {
			return;
		}

		SefazState state = this.getSefazState(recibe);

		MyLogger.getLog().info(
				"Result sending set: " + this.numberSet + " code: "
						+ state.getCode() + " xMotive: " + state.getXMotive());

		if (this.sendedStates.contains(state)) {
			for (String keyXml : this.keyXmls) {
				this.cacheXml.updateSendState(keyXml, recibe.getCStat(), recibe
						.getXMotivo(), recibe.getDhRecbto());
			}
			this.numberRecibo = recibe.getNumberRecibo();
			this.cacheSet.updateSendState(this.numberSet, this.numberRecibo,
					resultXml, recibe.getCStat(), recibe.getXMotivo(), recibe
							.getDhRecbto());
			this.tryAgain = false;

			this.sendCheckSet();
		}

		if (this.noSendedStates.contains(state)) {
			for (String keyXml : this.keyXmls) {
				this.cacheXml.updateState(keyXml, XML_STATE.ERRO_ENVIO,
						Calendar.getInstance().getTime());
			}
			this.cacheSet.updateStateSefaz(this.numberSet,
					SET_STATE.ERRO_ENVIO, Calendar.getInstance().getTime(),
					recibe.getCStat(), recibe.getXMotivo(), recibe
							.getDhRecbto());
			this.tryAgain = true;
		}

		if (this.schemaErroStates.contains(state)) {
			for (String keyXml : this.keyXmls) {
				this.cacheXml.updateState(keyXml, XML_STATE.ERRO_SCHEMA_LOTE,
						Calendar.getInstance().getTime());
			}
			this.cacheSet.updateStateSefaz(this.numberSet,
					SET_STATE.ERRO_ESQUEMA, Calendar.getInstance().getTime(),
					recibe.getCStat(), recibe.getXMotivo(), recibe
							.getDhRecbto());
			this.tryAgain = false;
		}

		// states not configured in codigos.conf
		this.tryAgain = false;

	}

	private void sendCheckSet() {
		try {
			MyLogger.getLog().info(
					"Result sending request to check: "
							+ this.keyXmls.toString());
			Locator.getManagerReference().checkXmlSet(this.cnpj,
					this.numberSet, this.numberRecibo, this.keyXmls, this.uf,
					this.ambient);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private SefazState getSefazState(XMLWrapperReturnSend result) {
		return new SefazState(Integer.parseInt(result.getCStat().trim()),
				result.getXMotivo());
	}

	@Override
	public void run() {
		this.currentTime = 1;
		this.currentTentative = 0;
		while (tryAgain) {

			if (this.currentTentative > SystemParameters.MAX_NUMBER_TENTATIVES_SEND) {
				return;
			}

			MyLogger.getLog().info(
					"Sending set: " + this.numberSet + " with: "
							+ this.keyXmls.toString());

			this.send();
			try {
				Thread.sleep(this.currentTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.currentTentative++;
			this.currentTime = this.currentTentative
					* SystemParameters.INTERVAL_SEND;
			this.cacheSet.updateTentative(this.numberSet);
		}
		MyLogger.getLog().info("Finish sending set: " + this.numberSet);
		this.cacheSet.forceFlushCache();
		this.cacheXml.forceFlushCache();
	}
}
