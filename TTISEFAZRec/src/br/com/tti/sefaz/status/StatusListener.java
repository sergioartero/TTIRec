package br.com.tti.sefaz.status;

import java.rmi.RemoteException;

import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.remote.events.StatusServiceEvent;
import br.com.tti.sefaz.remote.events.TTIEvent;

public class StatusListener implements TTIEventListener {

	@Override
	public void processEvent(TTIEvent event) throws RemoteException {
		if (event instanceof StatusServiceEvent) {
			StatusServiceEvent status = (StatusServiceEvent) event;
			
			
		}
		
	}

}
