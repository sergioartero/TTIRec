package br.com.tti.sefaz.test;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.logging.Level;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.CNPJSerialize;
import br.com.tti.sefaz.util.Locator;

public class TesteXMLGet {
	public static void main(String[] args) {

		ManagerInterface ger = Locator.getManagerReference();

		try {
			Hashtable<String, CNPJSerialize> cnpjs = ger.getCNPJsSerialize();
			for (String cnpj : cnpjs.keySet()) {
				CNPJSerialize ser = cnpjs.get(cnpj);
				System.out.println(ser.getDirectoryworks());
			}

		} catch (RemoteException e) {
			MyLogger.getLog().log(Level.INFO, null, e);
		}

	}
}
