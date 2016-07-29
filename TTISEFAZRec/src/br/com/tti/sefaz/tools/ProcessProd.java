package br.com.tti.sefaz.tools;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Hashtable;

public class ProcessProd {
	private String lineDes;
	private String lineValues;

	private int pos1;
	private int pos2;
	private int pos3;

	private Hashtable<String, String> prod;

	public ProcessProd() {
		this.prod = new Hashtable<String, String>();
	}

	public void setPositionColumn(String line) {
		this.pos1 = line.indexOf("[");
		this.pos2 = line.indexOf("[", pos1 + 1);

	}

	public String getNameColmun(String line) {
		String column = null;
		if (pos1 != -1 && pos2 != -1)
			column = line.substring(this.pos1 + 1, this.pos2);
		if (column != null)
			column = column.trim().replace(":", "").replace("]", "").trim();
		return column;
	}

	public String getValueColumn(String line) {
		String value = null;
		if (pos1 != -1 && pos2 != -1) {
			if (pos2 >= line.length())
				return line.trim();
			else {
				value = line.substring(this.pos1, this.pos2 - 1);
			}
		}
		if (value != null)
			value = value.trim();
		return value;
	}

	public void process() {
		String column = "";

		this.setPositionColumn(lineDes);

		column = this.getNameColmun(lineDes);
		String value = this.getValueColumn(lineValues);

		while (column != null) {
			this.prod.put(column, value);

			// System.out.println(this.pos2 +"");
			if (pos2 < lineDes.length())
				lineDes = lineDes.substring(this.pos2);
			if (pos2 < lineValues.length())
				lineValues = lineValues.substring(this.pos2);

			/*
			 * System.out.println(column); System.out.println(value);
			 * System.out.println("d->" + lineDes); System.out.println("v->" +
			 * lineValues);
			 */
			// System.out.println(column);
			this.setPositionColumn(lineDes);

			column = this.getNameColmun(lineDes);
			value = this.getValueColumn(lineValues);
		}

		String lastColumn = lineDes.trim().replace("[", "").replace("]", "")
				.replace(":", "").trim();
		String lastValue = lineValues.trim();
		// System.out.println(lastColumn);
		// System.out.println(lastValue);

		this.prod.put(lastColumn, lastValue);
	}

	private void lerArquivo(String path) throws Exception {
		FileInputStream f = new FileInputStream(path);

		DataInputStream dis = new DataInputStream(f);
		this.lineDes = dis.readLine();
		this.lineValues = dis.readLine();
	}

	public void setLineDes(String lineDes) {
		this.lineDes = lineDes;
	}

	public void setLineValues(String lineValues) {
		this.lineValues = lineValues;
	}

	public Hashtable<String, String> getProd() {
		return prod;
	}

	public static void main(String[] args) {
		ProcessProd p = new ProcessProd();
		try {
			p.lerArquivo("/home/cnoriega/produtos.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.process();
	}
}
