package br.com.tti.sefaz.systemconfig;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.remote.CNPJSerialize;
import br.com.tti.sefaz.remote.CallBackConfig;
import br.com.tti.sefaz.remote.CertificatesConfig;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.remote.MessengerConfig;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.remote.ServicesConfig;

public interface SystemConfigInterface extends Remote {
	public Hashtable<String, MessengerConfig> getMessengerConfig()
			throws RemoteException;

	public Hashtable<String, CallBackConfig> getCallBackConfig()
			throws RemoteException;

	public Hashtable<String, CNPJData> getCNPJ() throws RemoteException;

	public DBConfig getDBConfig() throws RemoteException;

	public SenderConfig getSenderConfig() throws RemoteException;

	public Vector<CertificatesConfig> getCertificates() throws RemoteException;

	public ServicesConfig getServiceConfig() throws RemoteException;

	public Hashtable<Vector<String>, Vector<SchemaVersionConfig>> getSchemasVersion()
			throws RemoteException;

	public Hashtable<String, CNPJSerialize> getCNPJsSerialize()
			throws RemoteException;

}
