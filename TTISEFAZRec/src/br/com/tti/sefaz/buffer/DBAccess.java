package br.com.tti.sefaz.buffer;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.persistence.LogXML;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.remote.events.TTIEvent;

public interface DBAccess {
	public Vector<XMLData> findXMLData(Date d1, Date d2, Vector<String> cnpjs,
			String ambient, Hashtable prop) throws RemoteException;

	public Vector<XMLData> findXMLData(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException;

	public Vector<TTIEvent> findXMLEvent(Date d1, Date d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException;

	public Vector<TTIEvent> findXMLEvent(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException;

	public String findXML(String keyXml) throws RemoteException;

	public Vector<LogXML> findLog(String keyXml) throws RemoteException;

	public boolean checkPFXPassword(String pfx, String password)
			throws RemoteException;
}
