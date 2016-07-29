package br.com.tti.sefaz.sender;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Hashtable;

import sun.security.x509.X509CertImpl;

import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.security.PasswordManager;
import br.com.tti.sefaz.security.PasswordManagerInterface;
import br.com.tti.sefaz.security.WrapperPasswordManager;
import br.com.tti.sefaz.signer.SignerPKCS11;
import br.com.tti.sefaz.systemconfig.SystemConfigInterface;
import br.com.tti.sefaz.util.KeywordPassword;

public abstract class SenderGenericController {

	private SystemConfigInterface config;

	private PasswordManagerInterface pm;

	private SenderConfig senderConfig;

	public SenderGenericController(SystemConfigInterface config) {
		try {
			this.senderConfig = config.getSenderConfig();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pm = new PasswordManager();
		this.initSender();
	}

	public SenderGenericController(SenderConfig senderConfig) {
		// this.config = config;
		this.senderConfig = senderConfig;
		// this.pm = new PasswordManager();
		this.pm = new WrapperPasswordManager();
		this.initSender();
	}

	public void initSender() {
		try {
			// this.senderConfig = config.getSenderConfig();
			String certConfFile = senderConfig.getPfxTransmission();

			if (certConfFile.endsWith(".pfx")) {

				String keyStore = senderConfig.getTrustStoreFile();

				String pass = this.pm.getPassword(certConfFile);
				if (pass == null) {
					pass = KeywordPassword
							.typePassword("Digite a senha do arquivo PFX para transmissao "
									+ certConfFile + ":");
					if (this.pm.checkPassword(certConfFile, pass)) {
						this.pm.savePassword(certConfFile, pass);
					}
				}

				SenderGenericController.setSSLProperties(keyStore, certConfFile,
						"crhisn", pass);
			} else {
				String keyStore = senderConfig.getTrustStoreFile();

				String pass = this.pm.getPassword(certConfFile);
				if (pass == null) {
					pass = KeywordPassword
							.typePassword("Digite o PIN do token configurado no arquivo: "
									+ certConfFile + ":");
					if (this.pm.checkPassword(certConfFile, pass)) {
						this.pm.savePassword(certConfFile, pass);
					}
				}

				SenderGenericController.ajustarPropriedadesFwdPKCS11(certConfFile,
						keyStore, "crhisn", pass);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setSSLProperties(String fileTrust, String fileKey,
			String passwordTrust, String passwordKey) {
		// System.out.println("Setando:" + fileKey);
		// System.out.println("Setando:" + passwordKey);
		System.setProperty("javax.net.ssl.keyStore", fileKey);
		System.setProperty("javax.net.ssl.trustStore", fileTrust);
		System.setProperty("javax.net.ssl.keyStorePassword", passwordKey);
		System.setProperty("javax.net.ssl.trustStorePassword", passwordTrust);
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
	}

	public static void ajustarPropriedadesFwdPKCS11(String configfile,
			String fileTrust, String passwordTrust, String passwordKey) {

		System.out.println("Criando provedor SSL");
		Provider p = null;
		p = SignerPKCS11.providers.get(configfile);

		if (p == null) {
			p = new sun.security.pkcs11.SunPKCS11(configfile);
			Security.addProvider(p);
			SignerPKCS11.providers.put(configfile, p);
			System.out.println("Provedor criado:" + p.getName());
		}

		System.setProperty("javax.net.ssl.trustStore", fileTrust);
		System.setProperty("javax.net.ssl.trustStorePassword", passwordTrust);

		System.setProperty("javax.net.ssl.keyStoreType", "PKCS11");
		System.setProperty("javax.net.ssl.keyStorePassword", passwordKey);
		System.setProperty("javax.net.ssl.keyStore", "NONE");
		System.setProperty("javax.net.ssl.keyStoreProvider", p.getName());

	}
	
	

	public abstract String sendXMLMessage(String idServico, String header,
			String data, Hashtable prop) throws RemoteException;
	
	
}
