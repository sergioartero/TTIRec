package br.com.tti.sefaz.buffer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.listeners.TTIEventNotifier;

public interface Buffer<T> extends Remote, TTIEventListener, TTIEventNotifier {
	public Vector<T> findObjects(Date d1, Date d2, Vector<String> cnpjs,
			String ambient) throws RemoteException;

	public Vector<T> findObjects(String d1, String d2, Vector<String> cnpjs,
			String ambient) throws RemoteException;

	public void addObject(T obj) throws RemoteException;
	
	public Vector<T> getBufferedElements() throws RemoteException;
}
