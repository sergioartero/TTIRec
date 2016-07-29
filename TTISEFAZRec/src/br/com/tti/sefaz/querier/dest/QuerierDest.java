package br.com.tti.sefaz.querier.dest;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.consultadest.TRetConsNFeDest;

public interface QuerierDest extends Remote {

	public TRetConsNFeDest consultarNFeDest(String cnpj,
			Hashtable<String, Object> params) throws RemoteException;

}
