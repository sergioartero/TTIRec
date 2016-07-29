package br.com.tti.sefaz.persistence;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.RECIBE_STATE;
import br.com.tti.sefaz.systemconfig.SystemProperties.SET_STATE;


@Entity
public class SetData {

	@Id
	private String numberSet;

	private String cnpj;

	private String ambient;

	private SET_STATE state;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastDateUpdate;

	private int sefazCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSend;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateQuery;

	private SystemProperties.MODO_OP modo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTentative;

	private int numeroTentativa;

	private String numeroRecibo;

	private RECIBE_STATE stateRecibe;

	@Lob
	private String xmlProtocol;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateProcess;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRecive;

	private boolean processSet;

	private String dhSefaz;

	private String xMotivo;

	public String getXmlProtocol() {
		return xmlProtocol;
	}

	public void setXmlProtocol(String xmlProtocol) {
		this.xmlProtocol = xmlProtocol;
	}

	public String getNumberSet() {
		return numberSet;
	}

	public void setNumberSet(String numberSet) {
		this.numberSet = numberSet;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAmbient() {
		return ambient;
	}

	public void setAmbient(String ambient) {
		this.ambient = ambient;
	}

	public SET_STATE getState() {
		return state;
	}

	public void setState(SET_STATE state) {
		this.state = state;
	}

	public Date getLastDateUpdate() {
		return lastDateUpdate;
	}

	public void setLastDateUpdate(Date lastDateUpdate) {
		this.lastDateUpdate = lastDateUpdate;
	}

	public int getSefazCode() {
		return sefazCode;
	}

	public void setSefazCode(int sefazCode) {
		this.sefazCode = sefazCode;
	}

	public Date getDateSend() {
		return dateSend;
	}

	public void setDateSend(Date dateSend) {
		this.dateSend = dateSend;
	}

	public Date getDateQuery() {
		return dateQuery;
	}

	public void setDateQuery(Date dateQuery) {
		this.dateQuery = dateQuery;
	}

	public SystemProperties.MODO_OP getModo() {
		return modo;
	}

	public void setModo(SystemProperties.MODO_OP modo) {
		this.modo = modo;
	}

	public Date getLastTentative() {
		return lastTentative;
	}

	public void setLastTentative(Date lastTentative) {
		this.lastTentative = lastTentative;
	}

	public int getNumeroTentativa() {
		return numeroTentativa;
	}

	public void setNumeroTentativa(int numeroTentativa) {
		this.numeroTentativa = numeroTentativa;
	}

	public String getNumeroRecibo() {
		return numeroRecibo;
	}

	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}

	public RECIBE_STATE getStateRecibe() {
		return stateRecibe;
	}

	public void setStateRecibe(RECIBE_STATE stateRecibe) {
		this.stateRecibe = stateRecibe;
	}

	public Date getDateProcess() {
		return dateProcess;
	}

	public void setDateProcess(Date dateProcess) {
		this.dateProcess = dateProcess;
	}

	public Date getDataRecive() {
		return dataRecive;
	}

	public void setDataRecive(Date dataRecive) {
		this.dataRecive = dataRecive;
	}

	public boolean isProcessSet() {
		return processSet;
	}

	public void setProcessSet(boolean processSet) {
		this.processSet = processSet;
	}

	public String getDhSefaz() {
		return dhSefaz;
	}

	public void setDhSefaz(String dhSefaz) {
		this.dhSefaz = dhSefaz;
	}

	public String getXMotivo() {
		return xMotivo;
	}

	public void setXMotivo(String motivo) {
		xMotivo = motivo;
	}

}
