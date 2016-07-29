package br.com.tti.sefaz.cancelinut;

import java.rmi.RemoteException;

import br.com.tti.sefaz.contingence.ModeOperation;

public interface CancelInutInterface extends ModeOperation, java.rmi.Remote {

	public String cancelXml(String cnpj, String uf, String ambient,
			String keyXml, String protocol, String just, boolean async)
			throws RemoteException;

	public String inutXml(String cnpj, String uf, String ambient,
			String keyXml, String justl, boolean sync) throws RemoteException;

	public String inutXml(String nOp, String uf, String ambient, String ano,
			String cnpj, String mod, String serie, String ini, String fim,
			String just, boolean async) throws RemoteException;

}
