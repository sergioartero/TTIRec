package br.com.tti.sefaz.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.tti.sefaz.systemconfig.SystemProperties;

@Entity
public class LogXML_MYSQL implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long idOp;
	private String keyXml;
	// FIXME: ORACLE
	@Temporal(TemporalType.TIMESTAMP)
	// @Column(name="DATALOGH")
	private Date date;
	private SystemProperties.XML_STATE state;
	private String cStat;
	private String xMotivo;
	private String xDescription;

	public Long getIdOp() {
		return idOp;
	}

	public void setIdOp(Long idOp) {
		this.idOp = idOp;
	}

	public String getKeyXml() {
		return keyXml;
	}

	public void setKeyXml(String keyXml) {
		this.keyXml = keyXml;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SystemProperties.XML_STATE getState() {
		return state;
	}

	public void setState(SystemProperties.XML_STATE state) {
		this.state = state;
	}

	public String getCStat() {
		return cStat;
	}

	public void setCStat(String stat) {
		cStat = stat;
	}

	public String getXMotivo() {
		return xMotivo;
	}

	public void setXMotivo(String motivo) {
		xMotivo = motivo;
	}

	public String getXDescription() {
		return xDescription;
	}

	public void setXDescription(String description) {
		xDescription = description;
	}

}
