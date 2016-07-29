package br.com.tti.sefaz.sender.ssl;

public class ConfigureSSLProperties {

	public static void setSSLCertificates(String fileTrust, String fileKey,
			String passwordTrust, String passwordKey) {
		System.setProperty("javax.net.ssl.keyStore", fileKey);
		System.setProperty("javax.net.ssl.trustStore", fileTrust);
		System.setProperty("javax.net.ssl.keyStorePassword", passwordKey);
		System.setProperty("javax.net.ssl.trustStorePassword", passwordTrust);
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
	}

}
