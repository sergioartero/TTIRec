package br.com.tti.sefaz.externaldbaccess;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.util.Locator;

public class LaunchExternalAccess {

	private ExternalDBAccess external;

	public LaunchExternalAccess() {
		this.external = new ExternalAccessImpl();
	}

	private void register() throws RemoteException {
		Registry reg = LocateRegistry.getRegistry();
		ExternalDBAccess stub = (ExternalDBAccess) UnicastRemoteObject
				.exportObject(this.external, 0);
		reg.rebind(Locator.ACCESS_REMOTE_NAME, stub);
	}

	public void initExternalAccess() {
		try {
			this.register();
			MyLogger.getLog().info("Acesso remoto inicializado");
		} catch (RemoteException e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}
	}

	public static void main(String[] args) {
		LaunchExternalAccess l = new LaunchExternalAccess();
		l.initExternalAccess();
	}
}
