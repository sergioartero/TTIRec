package br.com.tti.sefaz.remote.events;

import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;

public class ChangeModeOpEvent extends TTIEvent {

	private static final long serialVersionUID = 1L;

	private MODO_OP modo;

	private String cnpj;

	private String amb;

	public MODO_OP getModo() {
		return modo;
	}

	public void setModo(MODO_OP modo) {
		this.modo = modo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAmb() {
		return amb;
	}

	public void setAmb(String amb) {
		this.amb = amb;
	}
}
