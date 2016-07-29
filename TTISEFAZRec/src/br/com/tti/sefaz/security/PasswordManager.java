package br.com.tti.sefaz.security;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.List;

import br.com.tti.sefaz.persistence.PFXFile;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;

public class PasswordManager implements PasswordManagerInterface {

	private static long key = 5;
	private DAO<PFXFile> daoPfx;

	public PasswordManager() {
		this.daoPfx = DaoFactory.createDAO(PFXFile.class);
	}

	public void savePassword(String file, String pass) {
		pass = crypt(pass);

		PFXFile p = new PFXFile();
		p.setPfx(file);
		p.setPassword(pass);

		this.daoPfx.saveEntity(p);
	}

	public String getPassword(String file) {
		List<PFXFile> senhas = this.daoPfx.listAllElements();
		for (PFXFile p : senhas) {
			if (p.getPfx().equals(file)) {
				return deCrypt(p.getPassword());
			}
		}
		return null;
	}

	public String crypt(String senha) {
		byte[] b = senha.getBytes();
		for (int i = 0; i < b.length; i++) {
			b[i] += key;
		}
		return new String(b);
	}

	public String deCrypt(String senha) {
		byte[] b = senha.getBytes();
		for (int i = 0; i < b.length; i++) {
			b[i] -= key;
		}
		return new String(b);
	}

	public boolean checkPassword(String arquivo, String senha)
			throws RemoteException {
		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance("pkcs12");
		} catch (KeyStoreException e1) {
			throw new RemoteException(e1.getMessage());
		}
		try {
			new FileInputStream(arquivo);
		} catch (FileNotFoundException e) {
			throw new RemoteException("Arquivo nao encontrado");
		}
		try {
			ks.load(new FileInputStream(arquivo), senha.toCharArray());
		} catch (IOException ex) {
			throw new RemoteException("Senha digitada invalida");
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
		return true;

	}

	public static void main(String[] args) {
		PasswordManager g = new PasswordManager();
		System.out.println(g.deCrypt("6789:;<="));
		System.out.println(g.deCrypt(g.crypt("6789:;<=")));
	}
}
