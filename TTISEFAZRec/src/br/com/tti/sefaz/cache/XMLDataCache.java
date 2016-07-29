package br.com.tti.sefaz.cache;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import br.com.tti.sefaz.connector.ConnectorProxy;
import br.com.tti.sefaz.listeners.DataListener;
import br.com.tti.sefaz.listeners.DataNotifier;
import br.com.tti.sefaz.listeners.impl.EventManagerListenerImpl;
import br.com.tti.sefaz.listeners.impl.LogDatabaseImpl;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.util.MainParameters;

public class XMLDataCache implements DataNotifier {

	private static XMLDataCache cache = null;

	public static XMLDataCache getInstance() {
		if (cache == null) {
			cache = new XMLDataCache();
			cache.addListener(new EventManagerListenerImpl());
			cache.addListener(new LogDatabaseImpl());
			if (MainParameters.isActiveconnector()) {
				cache.addListener(new ConnectorProxy());
			}
		}
		return cache;
	}

	private Vector<DataListener> listeners;
	private DAO<XMLData> daoXml;

	public XMLDataCache() {
		this.listeners = new Vector<DataListener>();
		this.daoXml = DaoFactory.createDAO(XMLData.class);
	}

	synchronized public XMLData findData(String keyXml) throws Exception {
		return this.daoXml.findEntity(keyXml);
	}

	synchronized public void simpleSaveState(XMLData date) throws Exception {
		XMLData dataExits = this.daoXml.findEntity(date.getKeyXml());
		if (dataExits == null) {
			this.daoXml.saveEntity(date);
			// this.notifyData(date);
		} else {
			// this.daoXml.updateEntity(date);
		}

	}

	synchronized public void saveState(XMLData date) throws Exception {
		XMLData dataExits = this.daoXml.findEntity(date.getKeyXml());
		if (dataExits != null) {
			if (!dataExits.getModo().equals(MODO_OP.NORMAL)) {
				date.setModo(dataExits.getModo());
			}

			if (dataExits.getState() == null) {
				this.daoXml.updateEntity(dataExits);
				return;
			}
			if (dataExits.getState().equals(XML_STATE.AUTORIZADA)
					&& dataExits.getAmbient().equals(
							SystemProperties.AMBIENT_HOMOLOGACAO)
					&& date.getAmbient().equals(
							SystemProperties.AMBIENT_PRODUCAO)) {
				this.daoXml.updateEntity(date);
			}
			if (!dataExits.getState().equals(XML_STATE.AUTORIZADA)) {
				this.daoXml.updateEntity(date);
			}
			if (dataExits.getState().equals(XML_STATE.AUTORIZADA)
					&& dataExits.getAmbient().equals(
							SystemProperties.AMBIENT_HOMOLOGACAO)
					&& date.getAmbient().equals(
							SystemProperties.AMBIENT_HOMOLOGACAO)) {
				throw new Exception("XML ja autorizado no ambiente: "
						+ dataExits.getAmbient());
			}
			if (dataExits.getState().equals(XML_STATE.AUTORIZADA)
					&& dataExits.getAmbient().equals(
							SystemProperties.AMBIENT_PRODUCAO)) {
				throw new Exception("XML ja autorizado no ambiente: "
						+ dataExits.getAmbient());
			}

		} else {
			this.daoXml.saveEntity(date);
		}

		this.notifyData(date);
	}

	synchronized public void updateState(String keyXml, XML_STATE stateV,
			Date date) {
		XMLData state = this.daoXml.findEntity(keyXml);
		state.setState(stateV);
		state.setLastDataStateUpdate(date);

		this.daoXml.updateEntity(state);
		this.notifyData(state);
	}

	synchronized public void updateStateSefaz(String keyXml, XML_STATE stateV,
			Date date, String cStat, String xMotivo, String dhSefaz) {
		XMLData state = this.daoXml.findEntity(keyXml);

		if (state == null) {
			MyLogger.getLog().info("XML not found key:" + keyXml);
			return;
		}

		state.setState(stateV);
		state.setLastDataStateUpdate(date);
		state.setSefazCode(Integer.parseInt(cStat));
		state.setXMotivo(xMotivo);
		state.setDhSefaz(dhSefaz);

		this.daoXml.updateEntity(state);
		this.notifyData(state);
	}

	synchronized public void updateSendState(String keyXml, String cStat,
			String xMotivo, String dhSefaz) {
		XMLData state = this.daoXml.findEntity(keyXml);
		state.setLastDataStateUpdate(Calendar.getInstance().getTime());
		state.setDateSended((Calendar.getInstance().getTime()));
		state.setState(XML_STATE.ENVIADA);
		state.setSefazCode(Integer.parseInt(cStat));
		state.setXMotivo(xMotivo);
		state.setDhSefaz(dhSefaz);

		this.daoXml.updateEntity(state);
		this.notifyData(state);
	}

	synchronized public void updateAutorizeState(String keyXml,
			String nProtocol, String xmlProtocol, String cStat, String xMotivo,
			String dhSefaz) {
		XMLData state = this.daoXml.findEntity(keyXml);
		Date current = Calendar.getInstance().getTime();
		state.setState(XML_STATE.AUTORIZADA);
		state.setDateAutorized(current);
		state.setLastDataStateUpdate(current);
		state.setSefazCode(Integer.parseInt(cStat));
		state.setXMotivo(xMotivo);
		state.setNAutorizedProtocol(nProtocol);
		state.setAutorizedProtocol(xmlProtocol);
		state.setDhSefaz(dhSefaz);

		this.daoXml.updateEntity(state);
		this.notifyData(state);
	}

	synchronized public void updateCancelState(String keyXml, String nProtocol,
			String xmlProtocol, String cStat, String xMotivo, String dhSefaz,
			String just) {
		XMLData state = this.daoXml.findEntity(keyXml);
		Date current = Calendar.getInstance().getTime();

		state.setState(XML_STATE.CANCELADA);

		state.setLastDataStateUpdate(current);
		state.setSefazCode(Integer.parseInt(cStat));
		state.setXMotivo(xMotivo);
		state.setCancelProtocol(xmlProtocol);
		state.setNCancelProtocol(nProtocol);
		state.setDhSefaz(dhSefaz);
		state.setDescription(just);

		this.daoXml.updateEntity(state);
		this.notifyData(state);
	}

	synchronized public void updateInutState(String keyXml, String nProtocol,
			String xmlProtocol, String cStat, String xMotivo, String dhSefaz,
			String just) {
		XMLData state = this.daoXml.findEntity(keyXml);
		Date current = Calendar.getInstance().getTime();

		state.setState(XML_STATE.INUTLIZADA);

		state.setLastDataStateUpdate(current);
		state.setSefazCode(Integer.parseInt(cStat));
		state.setXMotivo(xMotivo);
		state.setInutProtocol(xmlProtocol);
		state.setNInutProtocol(nProtocol);
		state.setDhSefaz(dhSefaz);
		state.setDescription(just);

		this.daoXml.updateEntity(state);
		this.notifyData(state);
	}

	synchronized public void forceFlushCache() {
		this.daoXml.flush();
	}

	@Override
	public void addListener(DataListener l) {
		this.listeners.add(l);
	}

	@Override
	public void notifyData(XMLData data) {
		for (DataListener l : this.listeners) {
			l.process(data);
		}
	}

	@Override
	public void notifyData(SetData data) throws RemoteException {
		for (DataListener l : this.listeners) {
			l.process(data);
		}
	}

}
