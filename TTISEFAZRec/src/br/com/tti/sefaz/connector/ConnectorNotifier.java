package br.com.tti.sefaz.connector;

import java.rmi.RemoteException;

import br.com.tti.sefaz.remote.events.TTIEvent;

public interface ConnectorNotifier extends java.rmi.Remote {
	public void addConnector(Connector connector) throws RemoteException;

	public void notifyDataConnector(TTIEvent data) throws RemoteException;

}
