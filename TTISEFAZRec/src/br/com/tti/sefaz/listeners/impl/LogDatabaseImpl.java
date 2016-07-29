package br.com.tti.sefaz.listeners.impl;

import java.util.Calendar;

import br.com.tti.sefaz.listeners.DataListener;
import br.com.tti.sefaz.persistence.LogXML;
import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.remote.events.TTIEvent;

public class LogDatabaseImpl implements DataListener {

	private DAO<LogXML> daoLog;

	public LogDatabaseImpl() {
		this.daoLog = DaoFactory.createDAO(LogXML.class);
	}

	@Override
	public void process(XMLData data) {
		LogXML log = new LogXML();
		log.setCStat(data.getSefazCode() + "");
		log.setDate(Calendar.getInstance().getTime());
		log.setKeyXml(data.getKeyXml());
		log.setState(data.getState());
		log.setXDescription("");
		if (data.getXMessageError() != null)
			log.setXDescription(data.getXMessageError());
		if (data.getXMessageValidation() != null)
			log
					.setXDescription(log.getXDescription()
							+ data.getXMessageError());

		log.setXMotivo(data.getXMotivo());

		this.daoLog.saveEntity(log);
	}

	@Override
	public void process(SetData data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(TTIEvent event) {
		// TODO Auto-generated method stub

	}

}
