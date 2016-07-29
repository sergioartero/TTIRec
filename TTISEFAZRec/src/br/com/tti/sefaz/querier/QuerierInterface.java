package br.com.tti.sefaz.querier;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.tti.sefaz.persistence.SefazState;

public interface QuerierInterface extends Remote {
	public String makeQueryXML(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws RemoteException;

	public SefazState makeQueryState(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws RemoteException;
}
