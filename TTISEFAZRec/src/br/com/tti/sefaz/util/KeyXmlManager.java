package br.com.tti.sefaz.util;

import java.util.Random;

public class KeyXmlManager {

	private String UF;
	private String cnpj;
	private String numeroNota;
	private String chave;
	private String serie;
	private String ddmm;
	private String modelo;
	private String codigo;
	private String tipoemiss;

	public KeyXmlManager(String chave) {
		this.chave = chave.replace("NFe", "");
		this.chave = chave.replace("CTe", "");
		String[] partes = descomporChaveNFe(this.chave);
		this.cnpj = partes[2];
		this.UF = partes[0];
		this.numeroNota = partes[5];
		this.serie = partes[4];
		this.ddmm = partes[1];
		this.modelo = partes[3];
		this.codigo = partes[6];

		this.tipoemiss = this.codigo.substring(0, 1);
	}

	public String getDdmm() {
		return ddmm;
	}

	public void setDdmm(String ddmm) {
		this.ddmm = ddmm;
	}

	// UF, AAMM, CNPJ Emit, modelo, serie, numero nfe, codigo numerico, DV
	public String getUF() {
		return UF;
	}

	public void setUF(String uf) {
		UF = uf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public String getChave() {
		return chave;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getTpEmis() {
		return tipoemiss;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public static String[] descomporChaveNFe(String idNota) {

		idNota = idNota.replace("NFe", "");
		String[] componentes = new String[8];

		componentes[0] = idNota.substring(0, 2);
		componentes[1] = idNota.substring(2, 6);// .substring(0,2);
		componentes[2] = idNota.substring(6, 20);
		componentes[3] = idNota.substring(20, 22);
		componentes[4] = idNota.substring(22, 25);
		componentes[5] = idNota.substring(25, 34);
		componentes[6] = idNota.substring(34, 43);
		componentes[7] = idNota.substring(43, 44);

		return componentes;
	}

	private static Random r = new Random();

	public static String generateString(int n) {
		String str = new String();
		for (int i = 0; i < n; i++) {
			str += r.nextInt(10);
		}
		return str;
	}

	public static void main(String[] args) {
		String s = new String();

		String[] partes = descomporChaveNFe("31081017256512002593550010000151315326594310");

		KeyXmlManager c = new KeyXmlManager(
				"35141143802040000160550010000043681004640326");
		System.out.println("n: " + c.getNumeroNota());
		System.out.println("tpemiss: " + c.getTpEmis());
		for (String p : partes) {
			System.out.println("->" + p);
		}

	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

}