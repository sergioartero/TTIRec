package br.com.tti.sefaz.test;

import java.util.Hashtable;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;

public class ContingenceTest {

	public static void main(String[] args) {
		ManagerInterface manager = Locator.getManagerReference();

		Hashtable<String, CNPJData> cnpjs;
		try {
			cnpjs = manager.getCNPJ();
			for (String cnpj : cnpjs.keySet()) {
				/*manager.changeToState(cnpj,
						SystemProperties.AMBIENT_HOMOLOGACAO,
						MODO_OP.CONTINGENCE);
*/
				/*System.out.println("Envio");

				MainParameters.processArguments(args);
				SendXml a = new SendXml();
				a.enviar();*/

				Thread.sleep(3000);

				manager.changeToState(cnpj,
						SystemProperties.AMBIENT_HOMOLOGACAO, MODO_OP.NORMAL);

				Thread.sleep(3000);
				
				manager.leaveContingence(cnpj,
						SystemProperties.AMBIENT_HOMOLOGACAO, true,
						MODO_OP.CONTINGENCE);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
