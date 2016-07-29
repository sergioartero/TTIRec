package br.com.tti.sefaz.listeners;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.XMLData;

public interface DataNotifier extends Remote {

	public void addListener(DataListener listener) throws RemoteException;

	public void notifyData(XMLData data) throws RemoteException;

	public void notifyData(SetData data) throws RemoteException;

}
