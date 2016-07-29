//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.10.06 at 02:53:28 PM BRT 
//


package br.com.tti.sefaz.classes.inutilizacao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Tipo retorno do Pedido de Inutilização de Numeração do Conhecimento de Transporte eletrônico
 * 
 * <p>Java class for TRetInutCTe complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TRetInutCTe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infInut">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tpAmb" type="{http://www.portalfiscal.inf.br/cte}TAmb"/>
 *                   &lt;element name="verAplic" type="{http://www.portalfiscal.inf.br/cte}TVerAplic"/>
 *                   &lt;element name="cStat" type="{http://www.portalfiscal.inf.br/cte}TStat"/>
 *                   &lt;element name="xMotivo">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="1"/>
 *                         &lt;maxLength value="255"/>
 *                         &lt;whiteSpace value="collapse"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="cUF" type="{http://www.portalfiscal.inf.br/cte}TCodUfIBGE"/>
 *                   &lt;element name="ano" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *                         &lt;pattern value="[0-9]{1,2}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CNPJ" type="{http://www.portalfiscal.inf.br/cte}TCnpj" minOccurs="0"/>
 *                   &lt;element name="mod" type="{http://www.portalfiscal.inf.br/cte}TModCT" minOccurs="0"/>
 *                   &lt;element name="serie" type="{http://www.portalfiscal.inf.br/cte}TSerie" minOccurs="0"/>
 *                   &lt;element name="nCTIni" type="{http://www.portalfiscal.inf.br/cte}TNF" minOccurs="0"/>
 *                   &lt;element name="nCTFin" type="{http://www.portalfiscal.inf.br/cte}TNF" minOccurs="0"/>
 *                   &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="nProt" type="{http://www.portalfiscal.inf.br/cte}TProt" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="versao" use="required" type="{http://www.portalfiscal.inf.br/cte}TVerInutCTe" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRetInutCTe", namespace = "http://www.portalfiscal.inf.br/cte", propOrder = {
    "infInut",
    "signature"
})
public class TRetInutCTe {

    @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte", required = true)
    protected TRetInutCTe.InfInut infInut;
    @XmlElement(name = "Signature")
    protected SignatureType signature;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String versao;

    /**
     * Gets the value of the infInut property.
     * 
     * @return
     *     possible object is
     *     {@link TRetInutCTe.InfInut }
     *     
     */
    public TRetInutCTe.InfInut getInfInut() {
        return infInut;
    }

    /**
     * Sets the value of the infInut property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRetInutCTe.InfInut }
     *     
     */
    public void setInfInut(TRetInutCTe.InfInut value) {
        this.infInut = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

    /**
     * Gets the value of the versao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersao() {
        return versao;
    }

    /**
     * Sets the value of the versao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersao(String value) {
        this.versao = value;
    }


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
     *         &lt;element name="tpAmb" type="{http://www.portalfiscal.inf.br/cte}TAmb"/>
     *         &lt;element name="verAplic" type="{http://www.portalfiscal.inf.br/cte}TVerAplic"/>
     *         &lt;element name="cStat" type="{http://www.portalfiscal.inf.br/cte}TStat"/>
     *         &lt;element name="xMotivo">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="255"/>
     *               &lt;whiteSpace value="collapse"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="cUF" type="{http://www.portalfiscal.inf.br/cte}TCodUfIBGE"/>
     *         &lt;element name="ano" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
     *               &lt;pattern value="[0-9]{1,2}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CNPJ" type="{http://www.portalfiscal.inf.br/cte}TCnpj" minOccurs="0"/>
     *         &lt;element name="mod" type="{http://www.portalfiscal.inf.br/cte}TModCT" minOccurs="0"/>
     *         &lt;element name="serie" type="{http://www.portalfiscal.inf.br/cte}TSerie" minOccurs="0"/>
     *         &lt;element name="nCTIni" type="{http://www.portalfiscal.inf.br/cte}TNF" minOccurs="0"/>
     *         &lt;element name="nCTFin" type="{http://www.portalfiscal.inf.br/cte}TNF" minOccurs="0"/>
     *         &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="nProt" type="{http://www.portalfiscal.inf.br/cte}TProt" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tpAmb",
        "verAplic",
        "cStat",
        "xMotivo",
        "cuf",
        "ano",
        "cnpj",
        "mod",
        "serie",
        "nctIni",
        "nctFin",
        "dhRecbto",
        "nProt"
    })
    public static class InfInut {

        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String tpAmb;
        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String verAplic;
        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String cStat;
        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte", required = true)
        protected String xMotivo;
        @XmlElement(name = "cUF", namespace = "http://www.portalfiscal.inf.br/cte", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String cuf;
        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte")
        protected Short ano;
        @XmlElement(name = "CNPJ", namespace = "http://www.portalfiscal.inf.br/cte")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String cnpj;
        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String mod;
        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String serie;
        @XmlElement(name = "nCTIni", namespace = "http://www.portalfiscal.inf.br/cte")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String nctIni;
        @XmlElement(name = "nCTFin", namespace = "http://www.portalfiscal.inf.br/cte")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String nctFin;
        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte")
        protected XMLGregorianCalendar dhRecbto;
        @XmlElement(namespace = "http://www.portalfiscal.inf.br/cte")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String nProt;
        @XmlAttribute(name = "Id")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;

        /**
         * Gets the value of the tpAmb property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTpAmb() {
            return tpAmb;
        }

        /**
         * Sets the value of the tpAmb property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTpAmb(String value) {
            this.tpAmb = value;
        }

        /**
         * Gets the value of the verAplic property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVerAplic() {
            return verAplic;
        }

        /**
         * Sets the value of the verAplic property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVerAplic(String value) {
            this.verAplic = value;
        }

        /**
         * Gets the value of the cStat property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCStat() {
            return cStat;
        }

        /**
         * Sets the value of the cStat property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCStat(String value) {
            this.cStat = value;
        }

        /**
         * Gets the value of the xMotivo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXMotivo() {
            return xMotivo;
        }

        /**
         * Sets the value of the xMotivo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXMotivo(String value) {
            this.xMotivo = value;
        }

        /**
         * Gets the value of the cuf property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCUF() {
            return cuf;
        }

        /**
         * Sets the value of the cuf property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCUF(String value) {
            this.cuf = value;
        }

        /**
         * Gets the value of the ano property.
         * 
         * @return
         *     possible object is
         *     {@link Short }
         *     
         */
        public Short getAno() {
            return ano;
        }

        /**
         * Sets the value of the ano property.
         * 
         * @param value
         *     allowed object is
         *     {@link Short }
         *     
         */
        public void setAno(Short value) {
            this.ano = value;
        }

        /**
         * Gets the value of the cnpj property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCNPJ() {
            return cnpj;
        }

        /**
         * Sets the value of the cnpj property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCNPJ(String value) {
            this.cnpj = value;
        }

        /**
         * Gets the value of the mod property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMod() {
            return mod;
        }

        /**
         * Sets the value of the mod property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMod(String value) {
            this.mod = value;
        }

        /**
         * Gets the value of the serie property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSerie() {
            return serie;
        }

        /**
         * Sets the value of the serie property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSerie(String value) {
            this.serie = value;
        }

        /**
         * Gets the value of the nctIni property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNCTIni() {
            return nctIni;
        }

        /**
         * Sets the value of the nctIni property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNCTIni(String value) {
            this.nctIni = value;
        }

        /**
         * Gets the value of the nctFin property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNCTFin() {
            return nctFin;
        }

        /**
         * Sets the value of the nctFin property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNCTFin(String value) {
            this.nctFin = value;
        }

        /**
         * Gets the value of the dhRecbto property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDhRecbto() {
            return dhRecbto;
        }

        /**
         * Sets the value of the dhRecbto property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDhRecbto(XMLGregorianCalendar value) {
            this.dhRecbto = value;
        }

        /**
         * Gets the value of the nProt property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNProt() {
            return nProt;
        }

        /**
         * Sets the value of the nProt property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNProt(String value) {
            this.nProt = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }

    }

}