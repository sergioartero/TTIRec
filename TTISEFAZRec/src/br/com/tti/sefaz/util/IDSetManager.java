package br.com.tti.sefaz.util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class IDSetManager {

	private static Long number = 0L;
	private static String file = "lastnumber2";

	public static Long getNexIdSet() {
		if (number == 0L) {
			try {
				FileInputStream f = new FileInputStream(file);
				DataInputStream dis = new DataInputStream(f);
				String line = dis.readLine();
				number = Long.parseLong(line);
				f.close();
				dis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		number++;
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(number.toString());
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return number;
	}

	public static void main(String[] args) {
		System.out.println(IDSetManager.getNexIdSet());
	}
}
