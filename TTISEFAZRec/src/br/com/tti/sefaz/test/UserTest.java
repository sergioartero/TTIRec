package br.com.tti.sefaz.test;

import java.rmi.RemoteException;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.users.User;
import br.com.tti.sefaz.util.Locator;

public class UserTest {

	public static void main(String[] args) {
		try {
			ManagerInterface ger = Locator.getManagerReference();
			User u = ger.getUser("crhisn", null);
			System.out.println(u.getSenha());
			
			String[] cnpsj = u.getPerfil().getCnpjs();
			for (String cnpj : cnpsj) {
				System.out.println(cnpj);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
