/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tti.sefaz.users;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// FIXME: ORACLE
// @Entity(name = "USERBR")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String nome;
	private String endereco;
	private String senha;
	@ManyToOne
	private Profile perfil;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Profile getPerfil() {
		return perfil;
	}

	public void setPerfil(Profile perfil) {
		this.perfil = perfil;
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
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.taragona.nfe.nfecockpitdesktop.data.Usuario[id=" + id
				+ "]";
	}
}
