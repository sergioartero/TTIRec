package br.com.tti.sefaz.querier;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public interface QuerierRemote extends Remote {
	public XMLWrapperQuery makeQueryComplete(String uf, String key)
			throws RemoteException;

	public boolean isAlive() throws RemoteException;
}
