package br.com.tti.sefaz.remote;

import java.io.Serializable;

public class MessengerConfig implements Serializable {
	private String cnpj;
	private String uf;
	private int sizeSet;
	private int sizeFile;
	private long timeout;

	public int getSizeSet() {
		return sizeSet;
	}

	public void setSizeSet(int sizeSet) {
		this.sizeSet = sizeSet;
	}

	public int getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(int sizeFile) {
		this.sizeFile = sizeFile;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

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

}
