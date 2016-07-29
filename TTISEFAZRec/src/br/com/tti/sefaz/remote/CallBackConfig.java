package br.com.tti.sefaz.remote;

import java.io.Serializable;

public class CallBackConfig implements Serializable {
	private String cnpj;
	private String uf;

	private long timeProc;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public long getTimeProc() {
		return timeProc;
	}

	public void setTimeProc(long timeProc) {
		this.timeProc = timeProc;
	}

}
