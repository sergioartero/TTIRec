package br.com.tti.sefaz.contingence;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;

public interface ContingeceInterface extends Remote {

	public void enterContingence(String cnpj, String ambient, boolean async,
			MODO_OP modo) throws RemoteException;

	public void leaveContingence(String cnpj, String ambient, boolean async,
			MODO_OP type) throws RemoteException;

}
