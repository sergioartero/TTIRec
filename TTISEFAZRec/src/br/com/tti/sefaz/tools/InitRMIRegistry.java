package br.com.tti.sefaz.tools;

import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.util.MainParameters;

public class InitRMIRegistry {
	public static void init() {
		if (MainParameters.isRmiregistry()) {
			try {
				LocateRegistry.createRegistry(1099);				
				MyLogger.getLog().info("RMIRegistry Iniciado");
				Object lockObject = new Object();
				//synchronized (lockObject) {
				//	lockObject.wait();
				//}
			} catch (Exception e) {
				e.printStackTrace();
				MyLogger.getLog().info("RMIRegistry dont initialized!!");
				MyLogger.getLog().log(Level.FINEST, e.getLocalizedMessage(), e);
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);
		InitRMIRegistry.init();
	}
}
