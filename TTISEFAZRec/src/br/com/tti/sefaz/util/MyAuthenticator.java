package br.com.tti.sefaz.util;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
	private static MyAuthenticator instance = null;

	public static String loginStatic;

	public static String passStatic;

	public static String httpsHost;

	public static String httpsPort;

	private static String file = ".pass";

	private String login;
	private String pass;

	public MyAuthenticator(String login, String pass) {
		super();
		this.login = login;
		this.pass = pass;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		if (getRequestorType() == RequestorType.PROXY) {
			return new PasswordAuthentication(this.login, this.pass
					.toCharArray());
		} else {
			return super.getPasswordAuthentication();
		}
	}

	public void register() {
		// System.out.println("setando:" + this.login);
		// System.out.println("setando:" + this.pass);
		Authenticator.setDefault(this);
	}

	/*
	 * public void checkPassword() { FileInputStream f = null; try { f = new
	 * FileInputStream(file); DataInputStream dis = new DataInputStream(f);
	 * this.login = dis.readLine(); this.pass = dis.readLine(); this.register();
	 * f.close(); } catch (FileNotFoundException e) { this.getAuthentication();
	 * this.register(); try { FileWriter fw = new FileWriter(file);
	 * fw.write(this.login); fw.write("\n"); fw.write(this.pass); fw.close(); }
	 * catch (IOException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); } } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } }
	 */

}
