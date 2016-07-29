package br.com.tti.sefaz.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.List;

import br.com.taragona.nfe.persistence.Propriedades;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;

public class WrapperPasswordManager implements PasswordManagerInterface {

	private static long key = 5;

	private DAO<Propriedades> daoProp = DaoFactory
			.createDAO(Propriedades.class);

	@Override
	public boolean checkPassword(String arquivo, String senha)
			throws RemoteException {
		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance("pkcs12");
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
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

	@Override
	public String crypt(String senha) {
		byte[] b = senha.getBytes();
		for (int i = 0; i < b.length; i++) {
			b[i] += key;
		}
		return new String(b);
	}

	@Override
	public String deCrypt(String senha) {
		byte[] b = senha.getBytes();
		for (int i = 0; i < b.length; i++) {
			b[i] -= key;
		}
		return new String(b);
	}

	@Override
	public String getPassword(String file) {
		List<Propriedades> senhas = this.daoProp.listAllElements();
		for (Propriedades p : senhas) {
			if (p.getArquivo().equals(file)) {
				return deCrypt(p.getSenha());
			}
		}
		return null;
	}

	@Override
	public void savePassword(String file, String pass) {
		pass = crypt(pass);

		Propriedades p = new Propriedades();
		p.setArquivo(file);
		p.setSenha(pass);

		this.daoProp.saveEntity(p);

	}

}
