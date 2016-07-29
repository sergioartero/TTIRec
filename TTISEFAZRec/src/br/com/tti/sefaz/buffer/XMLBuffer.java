package br.com.tti.sefaz.buffer;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

import br.com.tti.sefaz.externaldbaccess.ExternalDBAccess;
import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.events.ChangeStateXmlEvent;
import br.com.tti.sefaz.remote.events.TTIEvent;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.Locator;

public class XMLBuffer implements Buffer<ChangeStateXmlEvent> {

	private ManagerInterface manager;
	private ExternalDBAccess access;
	private Vector<ChangeStateXmlEvent> buffer;
	private Vector<TTIEventListener> listener;

	public XMLBuffer(ManagerInterface manager) {
		this.listener = new Vector<TTIEventListener>();
		this.buffer = new Vector<ChangeStateXmlEvent>();
		this.manager = manager;
	}

	public XMLBuffer(ExternalDBAccess access) {
		this.listener = new Vector<TTIEventListener>();
		this.buffer = new Vector<ChangeStateXmlEvent>();
		this.access = access;
	}

	@Override
	public void addObject(ChangeStateXmlEvent obj) throws RemoteException {
		if (buffer.contains(obj)) {
			buffer.remove(obj);
		}
		this.buffer.add(obj);
	}

	@Override
	public Vector<ChangeStateXmlEvent> findObjects(String d1, String d2,
			Vector<String> cnpjs, String ambient) throws RemoteException {

		this.buffer = new Vector<ChangeStateXmlEvent>();

		Vector<TTIEvent> res = null;

		if (this.access != null) {
			res = this.access.findXMLEvent(d1, d2, cnpjs, ambient, null);
		} else {
			res = this.manager.findXMLEvent(d1, d2, cnpjs, ambient, null);
		}

		for (TTIEvent event : res) {
			this.buffer.add(ChangeStateXmlEvent.class.cast(event));
		}
		return this.buffer;
	}

	@Override
	public Vector<ChangeStateXmlEvent> findObjects(Date d1, Date d2,
			Vector<String> cnpjs, String ambient) throws RemoteException {
		Vector<TTIEvent> newList = new Vector<TTIEvent>();
		this.buffer = new Vector<ChangeStateXmlEvent>();

		Vector<TTIEvent> res = null;

		if (this.access != null) {
			res = this.access.findXMLEvent(d1, d2, cnpjs, ambient, null);
		} else {
			res = this.manager.findXMLEvent(d1, d2, cnpjs, ambient, null);
		}

		for (TTIEvent event : res) {
			newList.add(ChangeStateXmlEvent.class.cast(event));
		}
		return this.buffer;
	}

	@Override
	public void processEvent(TTIEvent event) throws RemoteException {
		MyLogger.getLog().info("Processing: " + event.toString());
		ChangeStateXmlEvent data = ChangeStateXmlEvent.class.cast(event);
		if (buffer.contains(data)) {
			buffer.remove(data);
		}
		this.buffer.insertElementAt(data, 0);
		this.notifyEvent(event);
	}

	@Override
	public void addListener(TTIEventListener listener) throws RemoteException {
		this.listener.add(listener);
	}

	@Override
	public void notifyEvent(TTIEvent event) throws RemoteException {
		for (TTIEventListener l : this.listener) {
			l.processEvent(event);
		}
	}

	@Override
	public Vector<ChangeStateXmlEvent> getBufferedElements()
			throws RemoteException {
		return this.buffer;
	}

	public static void main(String[] args) {
		XMLBuffer b = new XMLBuffer(Locator.getExternalAccess());
		Vector<String> cnpj = new Vector<String>();
		cnpj.add("61531620001709");
		cnpj.add("61531620001709");
		try {
			b.findObjects("01/01/2009", "01/02/2009", cnpj,
					SystemProperties.AMBIENT_HOMOLOGACAO);
			Vector<ChangeStateXmlEvent> el = b.getBufferedElements();
			for (ChangeStateXmlEvent l : el) {
				System.out.println(l.toString());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
