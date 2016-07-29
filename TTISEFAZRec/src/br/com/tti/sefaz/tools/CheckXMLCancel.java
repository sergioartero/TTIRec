package br.com.tti.sefaz.tools;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import br.com.tti.sefaz.querier.QuerierInstance;

public class CheckXMLCancel {
	private QuerierInstance q;
	private FileWriter fws;

	private String inputfile;
	private String outputfile;

	public CheckXMLCancel(String inputfile, String outfile, String xmlfile) {
		try {
			System.out.println(inputfile);
			this.inputfile = inputfile;
			this.outputfile = outfile;
			fws = new FileWriter(this.inputfile);
			q = new QuerierInstance(xmlfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeOutput(String line, String result) {
		try {
			fws.write(line + ";" + result + "\n");
			fws.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readFile() throws Exception {
		FileInputStream f = new FileInputStream(this.outputfile);

		DataInputStream dis = new DataInputStream(f);
		String line = dis.readLine();
		String all = new String();

		while (line != null) {

			line = dis.readLine();

			String consult = this.extractKey(line).replace("NFe", "");
			if (consult.length() == 44) {
				String result = this.queryKey(consult);
				this.writeOutput(line, result);
			}
		}

		f.close();

		this.fws.close();
	}

	public String extractKey(String line) {
		String[] parts = line.split(";");

		String number = null;
		String key = null;
		if (parts.length > 0)
			number = parts[0];
		if (parts.length > 1)
			key = parts[1];

		if (key != null)
			return key;
		else
			return number;
	}

	public String queryKey(String key) {
		String resultado = null;
		try {
			resultado = q.makeQuery(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static String calcularDV(String chave) {
		int counter = 2;
		int accumulated = 0;
		for (int i = chave.length() - 1; i >= 0; i--) {
			int digito = Integer.parseInt(chave.substring(i, i + 1));
			accumulated += (counter * digito);
			if (counter > 8) {
				counter = 2;
			} else {
				counter++;
			}
		}

		int resto = accumulated - (((int) (accumulated / 11)) * 11);
		if (resto == 0 || resto == 1) {
			return "0";
		} else {
			return (11 - resto) + "";
		}

	}

	public static void main(String[] args) {
		CheckXMLCancel x = new CheckXMLCancel(args[0], args[1], args[2]);

		try {
			/*
			 * String chave = "3510090114210700013755001000014596000000001";
			 * String dv = calcularDV(chave); String xml = x
			 * 
			 * .queryKey(chave + dv); System.out.println(xml);
			 */
			x.readFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
