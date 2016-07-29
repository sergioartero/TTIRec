package br.com.tti.sefaz.connector;

import java.rmi.RemoteException;

import br.com.tti.sefaz.remote.events.TTIEvent;

public abstract class AbstractConnector implements Connector, Runnable {

	private boolean run = true;

	@Override
	public abstract void processInput() throws RemoteException;

	@Override
	public abstract String getConnectorName() throws RemoteException;

	@Override
	public abstract Connector getStub() throws RemoteException;

	@Override
	public abstract void register(ConnectorNotifier notifier)
			throws RemoteException;

	@Override
	public abstract void processEvent(TTIEvent event) throws RemoteException;

	@Override
	public void run() {
		while (this.run) {
			try {
				this.processInput();
				Thread.sleep(2000);				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public void startConnector() throws RemoteException {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void stopConnector() throws RemoteException {
		this.run = false;
	}

}
