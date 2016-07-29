package br.com.tti.sefaz.persistence;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.TYPE_PRINT;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;

@Entity
public class XMLData {

	@Id
	private String keyXml;

	private String uf;

	private String cnpjEmit;

	private String cnpjDest;

	private String motivoErro;

	private boolean signated;

	private boolean printed;

	private String ambient;

	private TYPE_PRINT typePrint;

	private XML_STATE state;

	private SystemProperties.MODO_OP modo;

	private String dateEmiss;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAutorized;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSended;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCancel;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInut;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastDataStateUpdate;

	private String nAutorizedProtocol;

	private String nCancelProtocol;

	private String nInutProtocol;

	@Lob
	private String autorizedProtocol;

	@Lob
	private String cancelProtocol;

	@Lob
	private String inutProtocol;

	private String xMotivo;

	private String dhSefaz;

	@Lob
	private String xmlString;

	private String xMessageError;

	private String xMessageValidation;

	private String description;

	private int sefazCode;

	private String numberSet;

	private int serie;

	private int numero;

	public String getKeyXml() {
		return keyXml;
	}

	public void setKeyXml(String keyXml) {
		this.keyXml = keyXml;
	}

	public String getCnpjEmit() {
		return cnpjEmit;
	}

	public void setCnpjEmit(String cnpjEmit) {
		this.cnpjEmit = cnpjEmit;
	}

	public String getCnpjDest() {
		return cnpjDest;
	}

	public void setCnpjDest(String cnpjDest) {
		this.cnpjDest = cnpjDest;
	}

	public String getMotivoErro() {
		return motivoErro;
	}

	public void setMotivoErro(String motivoErro) {
		this.motivoErro = motivoErro;
	}

	public boolean isSignated() {
		return signated;
	}

	public void setSignated(boolean signated) {
		this.signated = signated;
	}

	public boolean isPrinted() {
		return printed;
	}

	public void setPrinted(boolean printed) {
		this.printed = printed;
	}

	public String getAmbient() {
		return ambient;
	}

	public void setAmbient(String ambient) {
		this.ambient = ambient;
	}

	public TYPE_PRINT getTypePrint() {
		return typePrint;
	}

	public void setTypePrint(TYPE_PRINT typePrint) {
		this.typePrint = typePrint;
	}

	public XML_STATE getState() {
		return state;
	}

	public void setState(XML_STATE state) {
		this.state = state;
	}

	public SystemProperties.MODO_OP getModo() {
		return modo;
	}

	public void setModo(SystemProperties.MODO_OP modo) {
		this.modo = modo;
	}

	public String getDateEmiss() {
		return dateEmiss;
	}

	public void setDateEmiss(String dateEmiss) {
		this.dateEmiss = dateEmiss;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateAutorized() {
		return dateAutorized;
	}

	public void setDateAutorized(Date dateAutorized) {
		this.dateAutorized = dateAutorized;
	}

	public Date getDateSended() {
		return dateSended;
	}

	public void setDateSended(Date dateSended) {
		this.dateSended = dateSended;
	}

	public Date getDateCancel() {
		return dateCancel;
	}

	public void setDateCancel(Date dateCancel) {
		this.dateCancel = dateCancel;
	}

	public Date getDateInut() {
		return dateInut;
	}

	public void setDateInut(Date dateInut) {
		this.dateInut = dateInut;
	}

	public Date getLastDataStateUpdate() {
		return lastDataStateUpdate;
	}

	public void setLastDataStateUpdate(Date lastDataStateUpdate) {
		this.lastDataStateUpdate = lastDataStateUpdate;
	}

	public String getAutorizedProtocol() {
		return autorizedProtocol;
	}

	public void setAutorizedProtocol(String autorizedProtocol) {
		this.autorizedProtocol = autorizedProtocol;
	}

	public String getCancelProtocol() {
		return cancelProtocol;
	}

	public void setCancelProtocol(String cancelProtocol) {
		this.cancelProtocol = cancelProtocol;
	}

	public String getInutProtocol() {
		return inutProtocol;
	}

	public void setInutProtocol(String inutProtocol) {
		this.inutProtocol = inutProtocol;
	}

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public String getXMessageError() {
		return xMessageError;
	}

	public void setXMessageError(String messageError) {
		xMessageError = messageError;
	}

	public String getXMessageValidation() {
		return xMessageValidation;
	}

	public void setXMessageValidation(String messageValidation) {
		xMessageValidation = messageValidation;
	}

	public int getSefazCode() {
		return sefazCode;
	}

	public void setSefazCode(int sefazCode) {
		this.sefazCode = sefazCode;
	}

	public String getNAutorizedProtocol() {
		return nAutorizedProtocol;
	}

	public void setNAutorizedProtocol(String autorizedProtocol) {
		nAutorizedProtocol = autorizedProtocol;
	}

	public String getNCancelProtocol() {
		return nCancelProtocol;
	}

	public void setNCancelProtocol(String cancelProtocol) {
		nCancelProtocol = cancelProtocol;
	}

	public String getNumberSet() {
		return numberSet;
	}

	public void setNumberSet(String numberSet) {
		this.numberSet = numberSet;
	}

	public String getXMotivo() {
		return xMotivo;
	}

	public void setXMotivo(String motivo) {
		xMotivo = motivo;
	}

	public String getDhSefaz() {
		return dhSefaz;
	}

	public void setDhSefaz(String dhSefaz) {
		this.dhSefaz = dhSefaz;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNInutProtocol() {
		return nInutProtocol;
	}

	public void setNInutProtocol(String inutProtocol) {
		nInutProtocol = inutProtocol;
	}

	public int getSerie() {
		return serie;
	}

	public void setSerie(int serie) {
		this.serie = serie;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (keyXml != null ? keyXml.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof XMLData)) {
			return false;
		}
		XMLData other = (XMLData) object;
		if ((this.keyXml == null && other.keyXml != null)
				|| (this.keyXml != null && !this.keyXml.equals(other.keyXml))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.keyXml;
	}

}
