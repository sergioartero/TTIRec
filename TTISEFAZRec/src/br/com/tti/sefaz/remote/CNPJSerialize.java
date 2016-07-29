package br.com.tti.sefaz.remote;

import java.io.Serializable;

public class CNPJSerialize implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cnpj;
	private String emails;
	private String directoryworks;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getDirectoryworks() {
		return directoryworks;
	}

	public void setDirectoryworks(String directoryworks) {
		this.directoryworks = directoryworks;
	}

}
