package br.com.tti.sefaz.tools;

import java.rmi.RemoteException;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.Locator;

public class QueryXml {

	public static void main(String[] args) {
		ManagerInterface manager = Locator.getManagerReference();
		try {
			String r = manager
					.makeQueryXML("35", SystemProperties.AMBIENT_HOMOLOGACAO,
							"35090161531620001709550010000051024724207434",
							true, true);
			System.out.println(r);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
