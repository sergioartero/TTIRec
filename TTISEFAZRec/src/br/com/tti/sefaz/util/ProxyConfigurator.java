package br.com.tti.sefaz.util;

import java.net.Authenticator;
import java.net.ProxySelector;
import java.util.ResourceBundle;


import br.com.tti.sefaz.log.MyLogger;

public class ProxyConfigurator {

	private static ResourceBundle rb;

	public static void configureProxy() {
		rb = ResourceBundle.getBundle("proxy");

		MyAuthenticator.loginStatic = rb.getString("usuario");
		MyAuthenticator.passStatic = rb.getString("senha");
		MyAuthenticator.httpsHost = rb.getString("servidor");
		MyAuthenticator.httpsPort = rb.getString("porta");

		MyLogger.getLog().finest(
				"configureProxy usuario:\"" + MyAuthenticator.loginStatic
						+ "\"");
		MyLogger.getLog().finest(
				"configureProxy senha:\"" + MyAuthenticator.passStatic + "\"");

		Authenticator.setDefault(new MyAuthenticator(
				MyAuthenticator.loginStatic, MyAuthenticator.passStatic));
		
		

		/*
		 * ProxySelector myProxySelector = proxySearch.getProxySelector();
		 * 
		 * ProxySelector.setDefault(myProxySelector);
		 */

		/*System.getProperties().put("proxySet", "true");
		System.getProperties()
				.put("https.proxyHost", MyAuthenticator.httpsHost);
		System.getProperties()
				.put("https.proxyPort", MyAuthenticator.httpsPort);
		System.getProperties().put("http.proxyHost", MyAuthenticator.httpsHost);
		System.getProperties().put("http.proxyPort", MyAuthenticator.httpsPort);*/

	}
}
