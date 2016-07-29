package br.com.tti.sefaz.remote;

import java.io.Serializable;

import br.com.tti.sefaz.systemconfig.schema.Cnpjinfo.TIPOEMP;

public class CNPJData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cnpj;
	private String uf;
	private String xName;
	private TIPOEMP tipo;
	private String municipio;
	private String login;
	private String senha;
	private String im;

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
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

	public String getXName() {
		return xName;
	}

	public void setXName(String name) {
		xName = name;
	}

	public TIPOEMP getTipo() {
		return tipo;
	}

	public void setTipo(TIPOEMP tipo) {
		this.tipo = tipo;
	}

	public String getxName() {
		return xName;
	}

	public void setxName(String xName) {
		this.xName = xName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CNPJData other = (CNPJData) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[cnpj: " + this.cnpj + " uf: " + this.uf + " tipo: "
				+ this.tipo + " mun: " + this.municipio + "]";
	}
}
