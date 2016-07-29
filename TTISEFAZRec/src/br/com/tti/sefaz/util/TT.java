package br.com.tti.sefaz.util;

import java.io.FileWriter;

public class TT {

	public static void main(String[] args) {
		try {
			String content = ReadFile
					.readFile("D:\\Documents and Settings\\Administrador\\Meus documentos\\Downloads\\saida.log");

			content = content.replace("Reading line", "\nLinha");
			content = content.replace("100", "Autorizada");
			content = content.replace("101", "Cancelada");
			content = content.replace("registros:0",
					"registros: Nao encontrada no banco de dados");

			FileWriter fw = new FileWriter("C:\\saida_processada.txt");
			fw.write(content);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
