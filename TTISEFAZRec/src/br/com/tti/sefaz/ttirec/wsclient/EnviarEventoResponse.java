
package br.com.tti.sefaz.ttirec.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for enviarEventoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="enviarEventoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.portalfiscal.inf.br/nfe}TRetEnvEvento" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enviarEventoResponse", namespace = "http://wsendpoint.sefaz.tti.com.br/", propOrder = {
    "_return"
})
public class EnviarEventoResponse {

    @XmlElement(name = "return", namespace = "")
    protected TRetEnvEvento _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link TRetEnvEvento }
     *     
     */
    public TRetEnvEvento getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRetEnvEvento }
     *     
     */
    public void setReturn(TRetEnvEvento value) {
        this._return = value;
    }

}
