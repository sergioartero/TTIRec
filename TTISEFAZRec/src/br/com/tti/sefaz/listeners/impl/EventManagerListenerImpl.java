package br.com.tti.sefaz.listeners.impl;

import java.rmi.RemoteException;

import br.com.tti.sefaz.listeners.DataListener;
import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.remote.events.ChangeStateXmlEvent;
import br.com.tti.sefaz.remote.events.TTIEvent;
import br.com.tti.sefaz.util.Locator;

public class EventManagerListenerImpl implements DataListener {

	private TTIEventListener manager;

	public EventManagerListenerImpl() {
		this.manager = Locator.getManagerReference();
	}

	@Override
	public void process(SetData data) {

	}

	@Override
	public void process(TTIEvent event) {

	}

	@Override
	public void process(XMLData data) {
		ChangeStateXmlEvent event = ChangeStateXmlEvent.createEvent(data);
		try {
			this.manager.processEvent(event);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
