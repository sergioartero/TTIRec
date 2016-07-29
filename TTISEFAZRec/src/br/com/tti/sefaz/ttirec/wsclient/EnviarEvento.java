
package br.com.tti.sefaz.ttirec.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for enviarEvento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="enviarEvento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chaveNFe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="justificativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataHora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enviarEvento", namespace = "http://wsendpoint.sefaz.tti.com.br/", propOrder = {
    "chaveNFe",
    "tipo",
    "justificativa",
    "dataHora"
})
public class EnviarEvento {

    @XmlElement(namespace = "")
    protected String chaveNFe;
    @XmlElement(namespace = "")
    protected int tipo;
    @XmlElement(namespace = "")
    protected String justificativa;
    @XmlElement(namespace = "")
    protected String dataHora;

    /**
     * Gets the value of the chaveNFe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChaveNFe() {
        return chaveNFe;
    }

    /**
     * Sets the value of the chaveNFe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChaveNFe(String value) {
        this.chaveNFe = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     */
    public void setTipo(int value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the justificativa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJustificativa() {
        return justificativa;
    }

    /**
     * Sets the value of the justificativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJustificativa(String value) {
        this.justificativa = value;
    }

    /**
     * Gets the value of the dataHora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataHora() {
        return dataHora;
    }

    /**
     * Sets the value of the dataHora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataHora(String value) {
        this.dataHora = value;
    }

}
