package br.com.tti.sefaz.querier.nfse;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface QuerierNFSe extends Remote {

	public String consultarNFSe(String cnpj, String mun,
			Hashtable<String, Object> params) throws RemoteException;

	public <T> T consultarNFSe(String cnpj, String mun,
			Hashtable<String, Object> params, Class<T> classreturn)
			throws Exception;
}
