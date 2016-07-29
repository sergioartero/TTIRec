
package br.gov.sp.prefeitura.nfe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VersaoSchema" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MensagemXML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "versaoSchema",
    "mensagemXML"
})
@XmlRootElement(name = "ConsultaNFeRecebidasRequest")
public class ConsultaNFeRecebidasRequest {

    @XmlElement(name = "VersaoSchema")
    protected int versaoSchema;
    @XmlElement(name = "MensagemXML")
    protected String mensagemXML;

    /**
     * Gets the value of the versaoSchema property.
     * 
     */
    public int getVersaoSchema() {
        return versaoSchema;
    }

    /**
     * Sets the value of the versaoSchema property.
     * 
     */
    public void setVersaoSchema(int value) {
        this.versaoSchema = value;
    }

    /**
     * Gets the value of the mensagemXML property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensagemXML() {
        return mensagemXML;
    }

    /**
     * Sets the value of the mensagemXML property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensagemXML(String value) {
        this.mensagemXML = value;
    }

}
