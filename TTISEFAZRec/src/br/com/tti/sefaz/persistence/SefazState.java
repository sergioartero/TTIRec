package br.com.tti.sefaz.persistence;

import java.io.Serializable;

public class SefazState implements Serializable {
	private Integer code;
	private String xMotive;

	public SefazState(Integer code, String motive) {
		super();
		this.code = code;
		xMotive = motive;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getXMotive() {
		return xMotive;
	}

	public void setXMotive(String motive) {
		xMotive = motive;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (code != null ? code.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof SefazState)) {
			return false;
		}

		SefazState other = (SefazState) object;
		if ((this.code == null && other.code != null)
				|| (this.code != null && !this.code.equals(other.code))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.code.toString();
	}

}
