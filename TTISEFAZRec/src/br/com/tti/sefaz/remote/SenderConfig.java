package br.com.tti.sefaz.remote;

import java.io.Serializable;
import java.util.Vector;

public class SenderConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trustStoreFile;
	private String pfxTransmission;
	private String pfxFile;
	private Vector<String> cnpjs;
	private String servicesFile;

	public String getTrustStoreFile() {
		return trustStoreFile;
	}

	public void setTrustStoreFile(String trustStoreFile) {
		this.trustStoreFile = trustStoreFile;
	}

	public String getPfxTransmission() {
		return pfxTransmission;
	}

	public void setPfxTransmission(String pfxTransmission) {
		this.pfxTransmission = pfxTransmission;
	}

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

	public String getServicesFile() {
		return servicesFile;
	}

	public void setServicesFile(String servicesFile) {
		this.servicesFile = servicesFile;
	}

}