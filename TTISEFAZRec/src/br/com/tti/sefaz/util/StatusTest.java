package br.com.tti.sefaz.util;

import java.rmi.RemoteException;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.events.StatusServiceEvent;

public class StatusTest {
	public static void main(String[] args) {
		ManagerInterface manager = Locator.getManagerReference("localhost");
		StatusServiceEvent ev = new StatusServiceEvent();
		ev.setStatus("todo fora");

		try {
			manager.notifyEvent(ev);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
