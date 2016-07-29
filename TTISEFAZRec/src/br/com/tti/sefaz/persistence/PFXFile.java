package br.com.tti.sefaz.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PFXFile {

	@Id
	private String pfx;
	private String password;

	public String getPfx() {
		return pfx;
	}

	public void setPfx(String pfx) {
		this.pfx = pfx;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
