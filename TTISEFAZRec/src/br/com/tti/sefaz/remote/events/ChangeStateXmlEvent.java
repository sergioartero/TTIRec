package br.com.tti.sefaz.remote.events;

import java.util.Date;

import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;

public class ChangeStateXmlEvent extends TTIEvent {

	private static final long serialVersionUID = 1L;

	private String keyXml;

	private String uf;

	private String cnpjEmit;

	private String cnpjDest;

	private String motivoErro;

	private boolean printed;

	private String ambient;

	private XML_STATE state;

	private SystemProperties.MODO_OP modo;

	private String dateEmiss;

	private Date dateCreate;

	private Date dateAutorized;

	private Date dateSended;

	private Date dateCancel;

	private Date dateInut;

	private String nAutorizedProtocol;

	private String nCancelProtocol;

	private String nInutProtocol;

	private String xMotivo;

	private String dhSefaz;

	private String xMessageError;

	private String xMessageValidation;

	private String description;

	private int sefazCode;

	private String numberSet;

	public String getKeyXml() {
		return keyXml;
	}

	public void setKeyXml(String keyXml) {
		this.keyXml = keyXml;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
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

	public String getNInutProtocol() {
		return nInutProtocol;
	}

	public void setNInutProtocol(String inutProtocol) {
		nInutProtocol = inutProtocol;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSefazCode() {
		return sefazCode;
	}

	public void setSefazCode(int sefazCode) {
		this.sefazCode = sefazCode;
	}

	public String getNumberSet() {
		return numberSet;
	}

	public void setNumberSet(String numberSet) {
		this.numberSet = numberSet;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (keyXml != null ? keyXml.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ChangeStateXmlEvent)) {
			return false;
		}
		ChangeStateXmlEvent other = (ChangeStateXmlEvent) object;
		if ((this.keyXml == null && other.keyXml != null)
				|| (this.keyXml != null && !this.keyXml.equals(other.keyXml))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Event  " + this.keyXml + " with " + this.state.toString();
	}

	public static ChangeStateXmlEvent createEvent(XMLData data) {
		ChangeStateXmlEvent event = new ChangeStateXmlEvent();
		event.setAmbient(data.getAmbient());
		event.setCnpjDest(data.getCnpjDest());
		event.setCnpjEmit(data.getCnpjEmit());
		event.setDateAutorized(data.getDateAutorized());
		event.setDateCancel(data.getDateCancel());
		event.setDateCreate(data.getDateCreate());
		event.setDateEmiss(data.getDateEmiss());
		event.setDateInut(data.getDateInut());
		event.setDateSended(data.getDateSended());
		event.setDescription(data.getDescription());
		event.setDhSefaz(data.getDhSefaz());
		event.setKeyXml(data.getKeyXml());
		event.setModo(data.getModo());
		event.setMotivoErro(data.getMotivoErro());
		event.setNAutorizedProtocol(data.getNAutorizedProtocol());
		event.setNCancelProtocol(data.getNCancelProtocol());
		event.setNInutProtocol(data.getNInutProtocol());
		event.setPrinted(data.isPrinted());
		event.setNumberSet(data.getNumberSet());
		event.setSefazCode(data.getSefazCode());
		event.setState(data.getState());
		event.setUf(data.getUf());
		event.setXMessageError(data.getXMessageError());
		event.setXMessageValidation(data.getXMessageValidation());
		event.setXMotivo(data.getXMotivo());

		return event;
	}

}
