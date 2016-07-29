package br.com.tti.sefaz.listeners;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.tti.sefaz.remote.events.TTIEvent;

public interface TTIEventListener extends Remote {

	public void processEvent(TTIEvent event) throws RemoteException;
}
