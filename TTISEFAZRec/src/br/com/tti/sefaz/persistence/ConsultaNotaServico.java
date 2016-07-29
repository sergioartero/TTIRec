package br.com.tti.sefaz.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ConsultaNotaServico implements Serializable {

	@Id
	private String id;

	// entendase como tomador... que eh cliente da TTI... Auto sueco
	private String cnpjPrestador;

	private String municipio;

	@Lob
	private byte[] requestObj;

	private String requestObjClass;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataIni;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;

	private String outro;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCnpjPrestador() {
		return cnpjPrestador;
	}

	public void setCnpjPrestador(String cnpjPrestador) {
		this.cnpjPrestador = cnpjPrestador;
	}

	public byte[] getRequestObj() {
		return requestObj;
	}

	public void setRequestObj(byte[] requestObj) {
		this.requestObj = requestObj;
	}

	public String getRequestObjClass() {
		return requestObjClass;
	}

	public void setRequestObjClass(String requestObjClass) {
		this.requestObjClass = requestObjClass;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getOutro() {
		return outro;
	}

	public void setOutro(String outro) {
		this.outro = outro;
	}

}
