//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.07.03 at 02:45:07 PM BRT 
//

package br.com.tti.sefaz.systemconfig.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for cnpjs complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;cnpjs&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;cnpj_info&quot; type=&quot;{}cnpjinfo&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name=&quot;UF&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cnpjs", propOrder = { "cnpjInfo", "pfx", "nNotas", "timeout",
		"tamanhoLote", "tempoProc", "pastaSaida", "emails", "casos" })
public class Cnpjs {

	@XmlElement(name = "cnpj_info")
	protected List<Cnpjinfo> cnpjInfo;
	@XmlAttribute(name = "UF")
	protected String uf;
	@XmlAttribute(name = "TIPO")
	protected String tipo;

	@XmlAttribute(name = "MUNICIPIO")
	protected String municipio;

	protected String pfx;
	protected String nNotas;
	protected String timeout;
	protected String tamanhoLote;
	protected String tempoProc;
	protected String pastaSaida;
	protected String emails;
	protected String casos;

	/**
	 * Gets the value of the cnpjInfo property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the cnpjInfo property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getCnpjInfo().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Cnpjinfo }
	 * 
	 * 
	 */

	public List<Cnpjinfo> getCnpjInfo() {
		if (cnpjInfo == null) {
			cnpjInfo = new ArrayList<Cnpjinfo>();
		}
		return this.cnpjInfo;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * Gets the value of the uf property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUF() {
		return uf;
	}

	/**
	 * Sets the value of the uf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUF(String value) {
		this.uf = value;
	}

	public String getPfx() {
		return pfx;
	}

	public void setPfx(String pfx) {
		this.pfx = pfx;
	}

	public String getNNotas() {
		return nNotas;
	}

	public void setNNotas(String notas) {
		nNotas = notas;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getTamanhoLote() {
		return tamanhoLote;
	}

	public void setTamanhoLote(String tamanhoLote) {
		this.tamanhoLote = tamanhoLote;
	}

	public String getTempoProc() {
		return tempoProc;
	}

	public void setTempoProc(String tempoProc) {
		this.tempoProc = tempoProc;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getCasos() {
		return casos;
	}

	public void setCasos(String casos) {
		this.casos = casos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPastaSaida() {
		return pastaSaida;
	}

	public void setPastaSaida(String pastaSaida) {
		this.pastaSaida = pastaSaida;
	}
}
