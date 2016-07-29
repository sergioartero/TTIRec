package br.com.tti.sefaz.callback;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import br.com.tti.sefaz.contingence.ModeOperation;
import br.com.tti.sefaz.remote.CallBackConfig;

public interface CallBackInteface extends ModeOperation, Remote {

	public void checkXmlSet(String cnpj, String idLote, String nRecibo,
			Vector<String> notas, String uf, String ambient)
			throws RemoteException;

	public void setCallBackParameters(String cnpj, String uf,
			CallBackConfig config) throws RemoteException;
}
