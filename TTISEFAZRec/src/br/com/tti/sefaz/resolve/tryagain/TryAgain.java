package br.com.tti.sefaz.resolve.tryagain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TryAgain extends Remote {
	public void synchronizeXmlWithSefaz(String keyXml) throws RemoteException;

	public void synchronizeXmlWithSefaz(String cnpj, String ambient, String uf)
			throws RemoteException;

}
