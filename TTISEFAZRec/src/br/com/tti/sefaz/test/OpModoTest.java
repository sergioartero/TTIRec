package br.com.tti.sefaz.test;

import java.rmi.RemoteException;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;

public class OpModoTest {

	public static void main(String[] args) {
		ManagerInterface manager = Locator.getManagerReference("localhost");
		String ambient = SystemProperties.AMBIENT_HOMOLOGACAO;
		MODO_OP modo = MODO_OP.NORMAL;
		try {
			manager.changeToState("60689692000159", ambient, modo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
