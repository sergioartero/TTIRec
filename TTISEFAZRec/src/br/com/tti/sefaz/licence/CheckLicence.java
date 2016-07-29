package br.com.tti.sefaz.licence;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.util.ReadFile;

public class CheckLicence {

	private String fileConfig;
	private String digest;

	private ResourceBundle b;

	public CheckLicence(String fileConfig) {
		super();
		this.fileConfig = fileConfig;
		try {
			this.b = ResourceBundle.getBundle("license");
			this.b.getString("license");
		} catch (Exception e) {
			MyLogger.getLog().info("Licen\u00e7a nao encontrada");
			System.exit(1000);
		}

	}

	private String generateDigest(String xmlFile) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(xmlFile.getBytes());
			byte[] hash = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0"
							+ Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}
		return null;
	}

	public void check() {
		try {
			String xml = ReadFile.readFile(this.fileConfig);
			digest = this.generateDigest(xml + this.getMACAddress());
			// System.out.println(digest);
			if (!digest.equals(this.b.getObject("license"))) {
				MyLogger.getLog().info("Licen\u00e7a invalida");
				System.exit(1000);
			}

		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}
	}

	public void generateLicense(String mac) {
		try {
			String xml = ReadFile.readFile(this.fileConfig);
			digest = this.generateDigest(xml + mac);
			System.out.println(digest);

		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}
	}

	public static String getMACAddress() {
		try {

			Enumeration<NetworkInterface> intfs = NetworkInterface
					.getNetworkInterfaces();
			String all = new String();
			for (; intfs.hasMoreElements();) {
				NetworkInterface it = intfs.nextElement();

				byte[] mac = it.getHardwareAddress();

				if (mac != null) {
					for (int i = 0; i < mac.length; i++) {
						all += String.format("%02X%s", mac[i],
								(i < mac.length - 1) ? "" : "");
					}
				}
			}
			return all;
		} catch (SocketException e) {
			MyLogger.getLog().info(e.getLocalizedMessage());
		}
		return "crhisnoriega";
	}

	private String code(String xml) {
		Charset charset = Charset.forName("UTF-8");
		CharsetEncoder encoder = charset.newEncoder();
		CharBuffer in = CharBuffer.allocate(xml.length());
		in.wrap(xml);
		ByteBuffer encode = null;
		try {
			encode = encoder.encode(in);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}

		CharBuffer bb = encode.asCharBuffer();
		String ss = new String();
		bb.put(ss);

		return bb.toString();

	}

	public static void main(String[] args) {
		// CheckLicence l = new CheckLicence(args[0]);
		// l.generateLicense(args[1]);

		System.out.println(getMACAddress());
		// l.check();
	}

}
