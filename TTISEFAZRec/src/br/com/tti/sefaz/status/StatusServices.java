package br.com.tti.sefaz.status;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StatusServices extends Remote {
	public boolean checkStatus(String uf, String ambient)
			throws RemoteException;

	public boolean checkService(String uf, String ambient, String idServico)
			throws RemoteException;
}
