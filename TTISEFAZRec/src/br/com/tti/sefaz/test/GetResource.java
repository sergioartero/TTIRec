package br.com.tti.sefaz.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetResource {

	public void get(String name) {
		InputStream r = this.getClass().getClassLoader().getResourceAsStream(
				name);
		InputStreamReader isr = new InputStreamReader(r);
		BufferedReader br = new BufferedReader(isr);

		try {
			while (br.readLine() != null) {
				System.out.println(br.readLine());
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}

	}

	public static void main(String[] args) {
		GetResource rr = new GetResource();
		rr.get("codigos.conf");
	}
}
