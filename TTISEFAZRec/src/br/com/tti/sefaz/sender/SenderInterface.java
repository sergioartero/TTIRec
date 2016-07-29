package br.com.tti.sefaz.sender;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface SenderInterface extends Remote {

	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException;

	public boolean checkXMLMessage(String idServico, Hashtable prop)
			throws RemoteException;
}
