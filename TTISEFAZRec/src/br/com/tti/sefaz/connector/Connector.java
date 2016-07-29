package br.com.tti.sefaz.connector;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.tti.sefaz.listeners.TTIEventListener;

public interface Connector extends Remote, TTIEventListener {
	public void register(ConnectorNotifier notifier) throws RemoteException;

	public Connector getStub() throws RemoteException;

	public String getConnectorName() throws RemoteException;
	
	public void processInput() throws RemoteException;
	
	public void startConnector() throws RemoteException;
	
	public void stopConnector() throws RemoteException;	
}
