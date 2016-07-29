package br.com.tti.sefaz.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;

@Entity
public class ModeOperationData {

	@Id
	private String cnpj;
	private MODO_OP modo;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public MODO_OP getModo() {
		return modo;
	}

	public void setModo(MODO_OP modo) {
		this.modo = modo;
	}

}
