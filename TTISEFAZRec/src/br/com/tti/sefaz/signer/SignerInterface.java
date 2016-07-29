package br.com.tti.sefaz.signer;

import java.rmi.RemoteException;
import java.util.Hashtable;

public interface SignerInterface {

	public String signForCNPJ(String cnpj, String xml, String tag)
			throws RemoteException;

	public Hashtable<String, Hashtable<String, Object>> readAllPFXPRoperties();
}
