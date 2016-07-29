package br.com.tti.sefaz.signer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;

import sun.security.x509.X509CertImpl;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.remote.CertificatesConfig;
import br.com.tti.sefaz.security.PasswordManager;
import br.com.tti.sefaz.security.PasswordManagerInterface;
import br.com.tti.sefaz.security.WrapperPasswordManager;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.SIGNER_TYPE;
import br.com.tti.sefaz.util.KeywordPassword;
import br.com.tti.sefaz.util.Locator;

public class Signer implements SignerInterface {

	private Hashtable<String, AssinadorInt> signers;

	private PasswordManagerInterface pass;
	private ManagerInterface manager;

	private Vector<CertificatesConfig> certificates;
	private Hashtable<String, CNPJData> cnpjs;

	public Signer() {
		try {
			this.manager = Locator.getManagerReference();
			this.certificates = this.manager.getCertificates();
			this.cnpjs = this.manager.getCNPJ();
		} catch (RemoteException e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		this.pass = new WrapperPasswordManager();
		this.signers = new Hashtable<String, AssinadorInt>();
		assert this.manager != null;
		if (SystemParameters.signer.equals(SIGNER_TYPE.PKCS12_SIGNER))
			this.initSignersPKCS12();
		if (SystemParameters.signer.equals(SIGNER_TYPE.PKCS11_SIGNER))
			this.initSignersPKCS11();
	}

	public Signer(Vector<CertificatesConfig> certificates) {
		this.certificates = certificates;
		this.pass = new WrapperPasswordManager();
		this.signers = new Hashtable<String, AssinadorInt>();
		assert this.manager != null;
		if (SystemParameters.signer.equals(SIGNER_TYPE.PKCS12_SIGNER))
			this.initSignersPKCS12();

		if (SystemParameters.signer.equals(SIGNER_TYPE.PKCS11_SIGNER))
			this.initSignersPKCS11();
	}

	@Deprecated
	public void initSignersPKCS11() {

		for (String cnpj : this.cnpjs.keySet()) {
			String password = this.pass.getPassword("pkcs11_password");

			AssinadorInt signer = new Main(password);
			this.signers.put(cnpj, signer);
		}
	}

	public void initSignersPKCS12() {
		try {

			for (CertificatesConfig certificate : certificates) {
				String certConfFile = certificate.getPfxFile();

				if (certConfFile.endsWith(".pfx")) {
					String pass = this.pass.getPassword(certConfFile);
					AssinadorInt a = null;

					if (pass == null) {
						pass = KeywordPassword
								.typePassword("Digite a senha do arquivo PFX para assinatura "
										+ certConfFile + ": ");
						a = new Main(certConfFile, pass);
						this.pass.savePassword(certConfFile, pass);
					} else {
						a = new Main(certConfFile, pass);
					}

					Vector<String> cnpjs = certificate.getCnpjs();
					for (String cnpj : cnpjs) {
						this.signers.put(cnpj, a);
					}
				} else {
					String pass = this.pass.getPassword(certConfFile);
					AssinadorInt a = null;

					if (pass == null) {
						pass = KeywordPassword
								.typePassword("Digite o PIN do token configurado no arquivo: "
										+ certConfFile + ": ");
						a = new SignerPKCS11(certConfFile, pass);
						this.pass.savePassword(certConfFile, pass);
					} else {
						a = new SignerPKCS11(certConfFile, pass);
					}

					Vector<String> cnpjs = certificate.getCnpjs();
					for (String cnpj : cnpjs) {
						this.signers.put(cnpj, a);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String signForCNPJ(String cnpj, String xml, String idService)
			throws RemoteException {
		try {
			String tag = "";
			if (idService.equals(SystemProperties.ID_SERVICO_RECEPCAO)) {
				if (SystemParameters.system
						.equals(SystemProperties.SYSTEM.TTI_NFE)) {
					tag = "infNFe";
				} else {
					tag = "infCTe";
				}
			}
			// infInut
			if (idService.equals(SystemProperties.ID_SERVICO_CANCELAMENTO)) {
				if (SystemParameters.system
						.equals(SystemProperties.SYSTEM.TTI_NFE)) {
					tag = "infCanc";
				} else {
					tag = "infCanc";
				}
			}

			if (idService.equals(SystemProperties.ID_SERVICO_INUTILIZACAO)) {
				if (SystemParameters.system
						.equals(SystemProperties.SYSTEM.TTI_NFE)) {
					tag = "infInut";
				} else {
					tag = "infInut";
				}
			}

			if (idService.equals(SystemProperties.ID_SERVICO_ESTADO_SERVICOS)
					|| idService
							.equals(SystemProperties.ID_SERVICO_DOWNLOAD_NF)) {
				if (SystemParameters.system
						.equals(SystemProperties.SYSTEM.TTI_NFE)) {
					tag = "infEvento";
				} else {
					tag = "infEvento";
				}
			}

			if (idService.equals(SystemProperties.ID_SERVICO_CONSULTA)) {
				Main signer = (Main) this.signers.get(cnpj.trim());
				return signer.assina(xml, "Cabecalho");
			}

			String xmlSigned = this.signers.get(cnpj.trim()).assina(xml, tag);
			MyLogger.getLog().finest(xmlSigned);
			return xmlSigned;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Hashtable<String, Hashtable<String, Object>> readAllPFXPRoperties() {
		Hashtable<String, Hashtable<String, Object>> prop = new Hashtable<String, Hashtable<String, Object>>();

		for (CertificatesConfig cert : this.certificates) {
			Vector<String> cnpjss = cert.getCnpjs();
			prop.put(cert.getPfxFile(),
					this.readPFXProperties(cert.getPfxFile()));
		}

		return prop;
	}

	public Hashtable<String, Object> readPFXProperties(String certificado) {

		String senha = this.pass.getPassword(certificado);

		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance("pkcs12");
		} catch (KeyStoreException ex) {
			System.out.println("Nao conseguiu criar keystore com pkcs12");
			// System.exit(1);
		}

		try {
			new FileInputStream(certificado);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nao encontrado: " + certificado);
			// System.exit(222);
		}

		try {
			ks.load(new FileInputStream(certificado), senha.toCharArray());
		} catch (IOException ex) {
			System.out.println("Senha digitada invalida");
			// System.exit(2);
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Algoritmo do certificado invalido");
			// System.exit(3);
		} catch (CertificateException ex) {
			System.out.println("Certificado ou senha invalidos");
			// System.exit(4);
		}
		Enumeration<String> aliases = null;
		try {
			aliases = ks.aliases();
		} catch (KeyStoreException ex) {
			System.out.println("Nenhuma entrada no keystore");
			// System.exit(5);
		}

		KeyStore.PrivateKeyEntry entry = null;

		try {
			while (aliases.hasMoreElements()) {
				String alias1 = (String) aliases.nextElement();
				if (ks.isKeyEntry(alias1)) {

					entry = (KeyStore.PrivateKeyEntry) ks
							.getEntry(alias1, new KeyStore.PasswordProtection(
									senha.toCharArray()));
					Certificate certificate = entry.getCertificate();

					X509CertImpl cc = (X509CertImpl) certificate;

					Hashtable<String, Object> props = new Hashtable<String, Object>();
					props.put("NA", cc.getNotAfter());
					props.put("NB", cc.getNotBefore());
					props.put("CN", cc.getSubjectX500Principal().toString());

					return props;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
