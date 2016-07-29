package br.com.tti.sefaz.tools;

import java.util.Hashtable;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.users.Profile;
import br.com.tti.sefaz.users.User;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.util.MainParameters;

public class CreateUser {

	public void createUser(String login, String name, String pass) {

		ManagerInterface ger = Locator.getManagerReference();
		try {
			User u = new User();
			Profile p = new Profile();

			u.setId(login);
			u.setNome(name);
			u.setSenha(pass);

			Hashtable<String, CNPJData> cnpjs = ger.getCNPJ();
			String[] csss = new String[cnpjs.keySet().size()];
			cnpjs.keySet().toArray(csss);
			p.setCnpjs(csss);

			u.setPerfil(p);
			ger.register(u, p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);

		CreateUser c = new CreateUser();
		c.createUser("tti", "tti", "tti");
	}

}
