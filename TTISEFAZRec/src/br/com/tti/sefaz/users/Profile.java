/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tti.sefaz.users;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Luciano
 */
@Entity
public class Profile implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String[] cnpjs;
	private boolean notas;
	private boolean lotes;
	private boolean configurarMsn;
	private boolean ajustarEstado;
	private boolean configurarServ;
	private boolean cadastrar;
	private boolean senhas;
	private boolean consultaNotas;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public String[] getCnpjs() {
		return cnpjs;
	}

	public void setCnpjs(String[] cnpjs) {
		this.cnpjs = cnpjs;
	}

	public boolean isAjustarEstado() {
		return ajustarEstado;
	}

	public void setAjustarEstado(boolean ajustarEstado) {
		this.ajustarEstado = ajustarEstado;
	}

	public boolean isConfigurarMsn() {
		return configurarMsn;
	}

	public void setConfigurarMsn(boolean configurarMsn) {
		this.configurarMsn = configurarMsn;
	}

	public boolean isCadastrar() {
		return cadastrar;
	}

	public void setCadastrar(boolean cadastrar) {
		this.cadastrar = cadastrar;
	}

	public boolean isConfigurarServ() {
		return configurarServ;
	}

	public void setConfigurarServ(boolean configurarServ) {
		this.configurarServ = configurarServ;
	}

	

	public boolean isSenhas() {
		return senhas;
	}

	public void setSenhas(boolean senhas) {
		this.senhas = senhas;
	}

	public boolean isConsultaNotas() {
		return consultaNotas;
	}

	public void setConsultaNotas(boolean consultaNotas) {
		this.consultaNotas = consultaNotas;
	}

	
	public boolean isNotas() {
		return notas;
	}

	public void setNotas(boolean notas) {
		this.notas = notas;
	}

	public boolean isLotes() {
		return lotes;
	}

	public void setLotes(boolean lotes) {
		this.lotes = lotes;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Profile)) {
			return false;
		}
		Profile other = (Profile) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.taragona.nfe.nfecockpitdesktop.data.Perfil[id=" + id
				+ "]";
	}

}
