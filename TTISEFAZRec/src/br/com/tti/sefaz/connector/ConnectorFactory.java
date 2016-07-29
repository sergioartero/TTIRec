package br.com.tti.sefaz.connector;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.logging.Level;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.util.Locator;

public class ConnectorFactory {
	
	private static Hashtable<String, Connector> connectors = new Hashtable<String, Connector>();

	public static Connector createConnector(String className) throws Exception {
		try {
			Class classs = Class.forName(className);
			Class superclasss = classs.getSuperclass();

			boolean implementsss = false;
			if (superclasss.getCanonicalName().contains(
					"br.com.tti.sefaz.connector.AbstractConnector")) {
				implementsss = true;				
			}

			if (!implementsss)
				throw new Exception("Class name " + className
						+ " not implements Connector interface.");

			ManagerInterface mng = Locator.getManagerReference();

			// active remote object
			Connector inst = (Connector) classs.newInstance();
			inst.register(mng);
			inst.startConnector();

			mng.addConnector(inst.getStub());
			
			connectors.put(className, inst);

			return inst;
		} catch (ClassNotFoundException e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		} catch (InstantiationException e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		} catch (IllegalAccessException e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		return null;
	}
	
	public static void killConnectors(){
		for (Connector connector : connectors.values()) {
			try {
				connector.stopConnector();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
}
