package br.com.tti.sefaz.tools;

import java.io.DataInputStream;
import java.io.FileInputStream;

import br.com.tti.sefaz.signer.AssinadorInt;
import br.com.tti.sefaz.signer.Main;
import br.com.tti.sefaz.util.KeywordPassword;
import br.com.tti.sefaz.util.MainParameters;

public class PKCS11Test {
	private static String lerArquivo(String path) throws Exception {
		FileInputStream f = new FileInputStream(path);

		DataInputStream dis = new DataInputStream(f);
		String line = dis.readLine();
		String all = new String();

		while (line != null) {
			all += line;
			line = dis.readLine();
		}
		return all;
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);
		String senha = KeywordPassword.typePassword("Digite o PIN:");
		AssinadorInt signer = new Main(senha);
		try {
			String xml = lerArquivo(MainParameters.getXml());
			String xmlSigned = signer.assina(xml, "infCTe");
			System.out.println(xmlSigned);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
