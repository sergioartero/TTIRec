package br.com.tti.sefaz.remote;

import java.io.Serializable;
import java.util.Vector;

public class CertificatesConfig implements Serializable {

	private String pfxFile;
	private Vector<String> cnpjs;

	public String getPfxFile() {
		return pfxFile;
	}

	public void setPfxFile(String pfxFile) {
		this.pfxFile = pfxFile;
	}

	public Vector<String> getCnpjs() {
		return cnpjs;
	}

	public void setCnpjs(Vector<String> cnpjs) {
		this.cnpjs = cnpjs;
	}

	@Override
	public String toString() {
		return this.pfxFile.toString();
	}

}
