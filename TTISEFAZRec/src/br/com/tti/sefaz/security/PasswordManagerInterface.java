package br.com.tti.sefaz.security;

import java.rmi.RemoteException;

public interface PasswordManagerInterface {
	public void savePassword(String file, String pass);

	public String getPassword(String file);

	public String crypt(String senha);

	public String deCrypt(String senha);

	public boolean checkPassword(String arquivo, String senha)
			throws RemoteException;
}
