package br.com.tti.sefaz.messenger;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.tti.sefaz.contingence.ModeOperation;
import br.com.tti.sefaz.remote.MessengerConfig;

public interface MessengerInterface extends ModeOperation, Remote {

	public void sendXml(String keyXml, String xml, String cnpjSender,
			String cnpjReceiver, String dateEmiss, String message, String uf,
			String ambient, boolean sign, boolean error) throws RemoteException;

	public void setMessengerParameters(String cnpj, String ambient,
			MessengerConfig msnConfig) throws RemoteException;

	public void sendXml(String xml) throws RemoteException;

	public boolean isActive() throws RemoteException;

}
