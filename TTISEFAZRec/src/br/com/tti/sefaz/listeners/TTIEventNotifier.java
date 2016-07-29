package br.com.tti.sefaz.listeners;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.tti.sefaz.remote.events.TTIEvent;

public interface TTIEventNotifier extends Remote {

	public void addListener(TTIEventListener listener) throws RemoteException;

	public void notifyEvent(TTIEvent event) throws RemoteException;
}
