package br.com.tti.sefaz.util;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

import br.com.tti.sefaz.externaldbaccess.ExternalDBAccess;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;

public class Locator {
	public static String MANGER_REMOTE_NAME = "ttimanager";
	public static String ACCESS_REMOTE_NAME = "ttiaccess";

	private static ManagerInterface manager = null;
	private static ExternalDBAccess external = null;

	private static int TENTATIVES = 3;

	public static ExternalDBAccess getExternalAccess() {
		if (external == null) {
			try {
				Registry registry = LocateRegistry.getRegistry();
				external = (ExternalDBAccess) registry
						.lookup(ACCESS_REMOTE_NAME);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return external;
	}

	public static ExternalDBAccess getExternalAccess(String ip) {
		if (external == null) {
			try {
				Registry registry = LocateRegistry.getRegistry(ip);
				external = (ExternalDBAccess) registry
						.lookup(ACCESS_REMOTE_NAME);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return external;
	}

	private static void reCreate(String ip) {
		for (int i = 0; i < TENTATIVES; i++) {
			try {
				Registry registry = LocateRegistry.getRegistry(ip);
				manager = (ManagerInterface) registry
						.lookup(MANGER_REMOTE_NAME);
				if (manager != null)
					return;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static ManagerInterface getManagerReference() {
		if (manager == null) {
			try {
				Registry registry = LocateRegistry.getRegistry();
				manager = (ManagerInterface) registry
						.lookup(MANGER_REMOTE_NAME);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			manager.isActive();
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			reCreate("localhost");
		}

		return manager;
	}

	public static ManagerInterface getManagerReference(String ip) {
		if (manager == null) {
			try {
				Registry registry = LocateRegistry.getRegistry(ip);
				manager = (ManagerInterface) registry
						.lookup(MANGER_REMOTE_NAME);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			manager.isActive();
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			reCreate(ip);
		}

		return manager;
	}

	public static void setManager(ManagerInterface manager) {
		Locator.manager = manager;
	}

	public static void setExternal(ExternalDBAccess external) {
		Locator.external = external;
	}

}
