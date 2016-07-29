package br.com.tti.sefaz.util;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeywordPassword {

	public static String typePassword(String msg) {
		Console consola = System.console();
		char[] newPassword1 = null;

		if (consola == null) {
			// System.out.println("Fudeu!!");
			System.out.print(msg);
			BufferedReader dataIn = new BufferedReader(new InputStreamReader(
					System.in));
			try {

				String password = dataIn.readLine();
				return password;
			} catch (IOException e) {
				System.out.println("Error!");
			}
			return null;
		}

		while (newPassword1 == null || newPassword1.length == 0) {
			newPassword1 = consola.readPassword(msg);
		}
		return new String(newPassword1);
	}

}
