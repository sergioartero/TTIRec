package br.com.tti.sefaz.connector;

import br.com.tti.sefaz.listeners.DataListener;
import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.remote.events.ChangeStateXmlEvent;
import br.com.tti.sefaz.remote.events.TTIEvent;
import br.com.tti.sefaz.util.Locator;

public class ConnectorProxy implements DataListener {

	private ConnectorNotifier connector;

	public ConnectorProxy(ConnectorNotifier connector) {
		super();
		this.connector = connector;
	}

	public ConnectorProxy() {
		this.connector = Locator.getManagerReference();
	}

	@Override
	public void process(XMLData data) {
		try {
			ChangeStateXmlEvent event = ChangeStateXmlEvent.createEvent(data);
			this.connector.notifyDataConnector(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(SetData data) {
	}

	@Override
	public void process(TTIEvent event) {
	}

}
