package br.com.tti.sefaz.signer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;

import br.com.taragona.nfe.danfe.ImprimirDANFEImpl;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.signer.Main;

public class SignerPKCS11 extends Main {

	public static Hashtable<String, Provider> providers = new Hashtable<String, Provider>();

	public SignerPKCS11(String configname, String senha) {
		Provider p = providers.get(configname);
		if (p == null) {
			p = new sun.security.pkcs11.SunPKCS11(configname);
			Security.addProvider(p);
			providers.put(configname, p);
		}

		char[] pin = senha.toCharArray();

		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance("pkcs11");
		} catch (KeyStoreException ex) {
			MyLogger.getLog().log(Level.SEVERE,
					"Nao conseguiu criar keystore com pkcs11", ex);
			// System.exit(1);
		}
		try {
			ks.load(null, pin);
		} catch (IOException ex) {
			MyLogger.getLog().log(Level.SEVERE,
					"certificado ou senha invalidos", ex);

		} catch (NoSuchAlgorithmException ex) {
			MyLogger.getLog().log(Level.SEVERE,
					"algoritmo do certificado invalido", ex);
		} catch (CertificateException ex) {
			MyLogger.getLog().log(Level.SEVERE,
					"certificado ou senha invalidos", ex);
		}
		Enumeration<String> aliases = null;
		try {
			aliases = ks.aliases();
		} catch (KeyStoreException ex) {
			MyLogger.getLog().log(Level.SEVERE, "nenhuma entrada no keystore",
					ex);
		}

		KeyStore.PrivateKeyEntry entry = null;
		try {
			while (aliases.hasMoreElements()) {
				String alias1 = (String) aliases.nextElement();
				if (ks.isKeyEntry(alias1)) {
					entry = (KeyStore.PrivateKeyEntry) ks
							.getEntry(alias1, new KeyStore.PasswordProtection(
									senha.toCharArray()));
					privateKey = entry.getPrivateKey();
					certificate = entry.getCertificate();
					break;
				}
			}
		} catch (NoSuchAlgorithmException ex) {
			MyLogger.getLog().log(Level.SEVERE, null, ex);
		} catch (UnrecoverableEntryException ex) {
			MyLogger.getLog().log(Level.SEVERE, null, ex);
		} catch (KeyStoreException ex) {
			MyLogger.getLog().log(Level.SEVERE, null, ex);
		}

		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		SignerPKCS11 ass = new SignerPKCS11("E:\\hsm.cfg", "SNICOSP");
		String xml = ImprimirDANFEImpl
				.lerArquivo("C:\\35081017256512000116550220000000091987526811-nfe.xml");

		try {
			String assinado = ass.assina(xml, "infNFe");
			System.out.println(assinado);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
