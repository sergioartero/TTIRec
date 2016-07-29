package br.com.tti.sefaz.consultadest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "chNFe", "cnpj", "cpf", "xNome", "ie",
		"dEmi", "tpNF", "vnf", "digVal", "dhRecbto", "cSitNFe", "cSitConf" })
public class ResCanc implements Serializable {

	@XmlElement(required = true)
	protected String chNFe;
	@XmlElement(name = "CNPJ")
	protected String cnpj;
	@XmlElement(name = "CPF")
	protected String cpf;
	@XmlElement(required = true)
	protected String xNome;
	@XmlElement(name = "IE", required = true)
	protected String ie;
	@XmlElement(required = true)
	protected String dEmi;
	@XmlElement(required = true)
	protected String tpNF;
	@XmlElement(name = "vNF", required = true)
	protected String vnf;
	@XmlElement(required = true)
	protected String digVal;
	@XmlElement(required = true)
	protected XMLGregorianCalendar dhRecbto;
	@XmlElement(required = true)
	protected String cSitNFe;
	@XmlElement(required = true)
	protected String cSitConf;
	@XmlAttribute(name = "NSU", required = true)
	protected String nsu;

	/**
	 * Gets the value of the chNFe property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getChNFe() {
		return chNFe;
	}

	/**
	 * Sets the value of the chNFe property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setChNFe(String value) {
		this.chNFe = value;
	}

	/**
	 * Gets the value of the cnpj property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCNPJ() {
		return cnpj;
	}

	/**
	 * Sets the value of the cnpj property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCNPJ(String value) {
		this.cnpj = value;
	}

	/**
	 * Gets the value of the cpf property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCPF() {
		return cpf;
	}

	/**
	 * Sets the value of the cpf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCPF(String value) {
		this.cpf = value;
	}

	/**
	 * Gets the value of the xNome property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getXNome() {
		return xNome;
	}

	/**
	 * Sets the value of the xNome property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setXNome(String value) {
		this.xNome = value;
	}

	/**
	 * Gets the value of the ie property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getIE() {
		return ie;
	}

	/**
	 * Sets the value of the ie property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setIE(String value) {
		this.ie = value;
	}

	/**
	 * Gets the value of the dEmi property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDEmi() {
		return dEmi;
	}

	/**
	 * Sets the value of the dEmi property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDEmi(String value) {
		this.dEmi = value;
	}

	/**
	 * Gets the value of the tpNF property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTpNF() {
		return tpNF;
	}

	/**
	 * Sets the value of the tpNF property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTpNF(String value) {
		this.tpNF = value;
	}

	/**
	 * Gets the value of the vnf property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVNF() {
		return vnf;
	}

	/**
	 * Sets the value of the vnf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVNF(String value) {
		this.vnf = value;
	}

	/**
	 * Gets the value of the digVal property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDigVal() {
		return digVal;
	}

	/**
	 * Sets the value of the digVal property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDigVal(String value) {
		this.digVal = value;
	}

	/**
	 * Gets the value of the dhRecbto property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getDhRecbto() {
		return dhRecbto;
	}

	/**
	 * Sets the value of the dhRecbto property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setDhRecbto(XMLGregorianCalendar value) {
		this.dhRecbto = value;
	}

	/**
	 * Gets the value of the cSitNFe property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCSitNFe() {
		return cSitNFe;
	}

	/**
	 * Sets the value of the cSitNFe property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCSitNFe(String value) {
		this.cSitNFe = value;
	}

	/**
	 * Gets the value of the cSitConf property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCSitConf() {
		return cSitConf;
	}

	/**
	 * Sets the value of the cSitConf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCSitConf(String value) {
		this.cSitConf = value;
	}

	/**
	 * Gets the value of the nsu property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNSU() {
		return nsu;
	}

	/**
	 * Sets the value of the nsu property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNSU(String value) {
		this.nsu = value;
	}

}
