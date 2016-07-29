
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
 *         &lt;element name="RetornoXML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "retornoXML"
})
@XmlRootElement(name = "ConsultaLoteResponse")
public class ConsultaLoteResponse {

    @XmlElement(name = "RetornoXML")
    protected String retornoXML;

    /**
     * Gets the value of the retornoXML property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetornoXML() {
        return retornoXML;
    }

    /**
     * Sets the value of the retornoXML property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetornoXML(String value) {
        this.retornoXML = value;
    }

}
