package br.com.tti.sefaz.status.local;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.remote.events.StatusServiceEvent;
import br.com.tti.sefaz.sender.SenderInterface;
import br.com.tti.sefaz.status.CheckerStatusController;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperStatus;

public class CheckerStatusThread implements Runnable {
	private String uf;
	private String ambient;

	private SenderInterface sender;
	private XMLMessageFactory xmlFactor;
	private CheckerStatusController notifier;

	public CheckerStatusThread(String uf, String ambient,
			SenderInterface sender, XMLMessageFactory xmlFactor,
			CheckerStatusController notifier) {
		super();
		this.uf = uf;
		this.ambient = ambient;
		this.sender = sender;
		this.xmlFactor = xmlFactor;
		this.notifier = notifier;
	}

	private void checkStatus() {

		String header = this.xmlFactor.createHeader(uf, ambient,
				SystemProperties.ID_SERVICO_ESTADO_SERVICOS);
		String data = this.xmlFactor.createStatusService(uf, ambient);
		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", this.ambient);
		prop.put("UF", this.uf);

		String result = null;
		try {
			result = this.sender.sendXMLMessage(
					SystemProperties.ID_SERVICO_ESTADO_SERVICOS, header, data,
					prop);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			XMLWrapperStatus status = XMLWrapperFactory
					.createStatusWrapper(result);
			StatusServiceEvent event = new StatusServiceEvent();
			event.setStatus(status.getXMotivo());
			event.setUf(this.uf);
			event.setAmbient(this.ambient);

			this.notifier.notifyEvent(event);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			this.checkStatus();
		}
	}
}
