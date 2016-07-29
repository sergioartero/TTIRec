package br.com.tti.sefaz.test;

import java.rmi.RemoteException;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.Locator;

public class QueryTest {

	public static void main(String[] args) {
		ManagerInterface ger = Locator.getManagerReference();
		try {
			String r = ger.makeQueryXML("35",
					SystemProperties.AMBIENT_HOMOLOGACAO, "NFe35345678901234567890123456789012345678901234", false, false);
		} catch (RemoteException e) {
			MyLogger.getLog().info(e.getLocalizedMessage());
		}
	}
}
