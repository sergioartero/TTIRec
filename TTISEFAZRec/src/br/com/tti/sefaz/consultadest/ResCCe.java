package br.com.tti.sefaz.consultadest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "chNFe", "dhEvento", "tpEvento",
		"nSeqEvento", "descEvento", "xCorrecao", "tpNF", "dhRecbto" })
public class ResCCe implements Serializable {

	@XmlElement(required = true)
	protected String chNFe;
	@XmlElement(required = true)
	protected String dhEvento;
	@XmlElement(required = true)
	protected String tpEvento;
	@XmlElement(required = true)
	protected String nSeqEvento;
	@XmlElement(required = true)
	protected String descEvento;
	protected String xCorrecao;
	@XmlElement(required = true)
	protected String tpNF;
	@XmlElement(required = true)
	protected XMLGregorianCalendar dhRecbto;
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
	 * Gets the value of the dhEvento property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDhEvento() {
		return dhEvento;
	}

	/**
	 * Sets the value of the dhEvento property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDhEvento(String value) {
		this.dhEvento = value;
	}

	/**
	 * Gets the value of the tpEvento property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTpEvento() {
		return tpEvento;
	}

	/**
	 * Sets the value of the tpEvento property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTpEvento(String value) {
		this.tpEvento = value;
	}

	/**
	 * Gets the value of the nSeqEvento property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNSeqEvento() {
		return nSeqEvento;
	}

	/**
	 * Sets the value of the nSeqEvento property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNSeqEvento(String value) {
		this.nSeqEvento = value;
	}

	/**
	 * Gets the value of the descEvento property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescEvento() {
		return descEvento;
	}

	/**
	 * Sets the value of the descEvento property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescEvento(String value) {
		this.descEvento = value;
	}

	/**
	 * Gets the value of the xCorrecao property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getXCorrecao() {
		return xCorrecao;
	}

	/**
	 * Sets the value of the xCorrecao property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setXCorrecao(String value) {
		this.xCorrecao = value;
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
