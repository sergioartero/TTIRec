package br.com.tti.sefaz.test;

import java.rmi.RemoteException;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.Locator;

public class CancelTest {

	public static void main(String[] args) {
		ManagerInterface ger = Locator.getManagerReference();

		try {
			ger.cancelXml("60689692000159", "35",
					SystemProperties.AMBIENT_HOMOLOGACAO,
					"35345678901234567890123456789012345678901234", "123456789012345",
					"teste de cancelamento: 35345678901234567890123456789012345678901234", false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
