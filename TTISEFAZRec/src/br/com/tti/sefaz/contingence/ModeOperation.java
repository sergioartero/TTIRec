package br.com.tti.sefaz.contingence;

import java.rmi.RemoteException;

import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;

public interface ModeOperation extends java.rmi.Remote {

	public void changeToState(String cnpj, String ambient, MODO_OP modo)
			throws RemoteException;

	public MODO_OP getModo(String cnpj, String ambient) throws RemoteException;
}
