
package br.com.tti.sefaz.ttirec.wsclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for retConsNFeDest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="retConsNFeDest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tpAmb" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="verAplic" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cStat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xMotivo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dhResp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indCont" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ultNSU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ret" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="resNFe" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CNPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="CPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="xNome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="IE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="dEmi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="vNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="digVal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
 *                             &lt;element name="cSitNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="cSitConf" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="resCanc" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CNPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="CPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="xNome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="IE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="dEmi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="vNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="digVal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
 *                             &lt;element name="cSitNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="cSitConf" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="resCCe" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="dhEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="tpEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="nSeqEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="descEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="xCorrecao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="versao" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retConsNFeDest", propOrder = {
    "tpAmb",
    "verAplic",
    "cStat",
    "xMotivo",
    "dhResp",
    "indCont",
    "ultNSU",
    "ret"
})
public class RetConsNFeDest {

    @XmlElement(required = true)
    protected String tpAmb;
    @XmlElement(required = true)
    protected String verAplic;
    @XmlElement(required = true)
    protected String cStat;
    @XmlElement(required = true)
    protected String xMotivo;
    @XmlElement(required = true)
    protected String dhResp;
    protected String indCont;
    protected String ultNSU;
    @XmlElement(nillable = true)
    protected List<RetConsNFeDest.Ret> ret;
    @XmlAttribute(required = true)
    protected String versao;

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
     * Gets the value of the dhResp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDhResp() {
        return dhResp;
    }

    /**
     * Sets the value of the dhResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDhResp(String value) {
        this.dhResp = value;
    }

    /**
     * Gets the value of the indCont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndCont() {
        return indCont;
    }

    /**
     * Sets the value of the indCont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndCont(String value) {
        this.indCont = value;
    }

    /**
     * Gets the value of the ultNSU property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUltNSU() {
        return ultNSU;
    }

    /**
     * Sets the value of the ultNSU property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUltNSU(String value) {
        this.ultNSU = value;
    }

    /**
     * Gets the value of the ret property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ret property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RetConsNFeDest.Ret }
     * 
     * 
     */
    public List<RetConsNFeDest.Ret> getRet() {
        if (ret == null) {
            ret = new ArrayList<RetConsNFeDest.Ret>();
        }
        return this.ret;
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
     *         &lt;element name="resNFe" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CNPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="xNome" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="IE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="dEmi" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="vNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="digVal" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
     *                   &lt;element name="cSitNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="cSitConf" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="resCanc" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CNPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="xNome" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="IE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="dEmi" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="vNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="digVal" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
     *                   &lt;element name="cSitNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="cSitConf" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="resCCe" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="dhEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="tpEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="nSeqEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="descEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="xCorrecao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "resNFe",
        "resCanc",
        "resCCe"
    })
    public static class Ret {

        protected RetConsNFeDest.Ret.ResNFe resNFe;
        protected RetConsNFeDest.Ret.ResCanc resCanc;
        protected RetConsNFeDest.Ret.ResCCe resCCe;

        /**
         * Gets the value of the resNFe property.
         * 
         * @return
         *     possible object is
         *     {@link RetConsNFeDest.Ret.ResNFe }
         *     
         */
        public RetConsNFeDest.Ret.ResNFe getResNFe() {
            return resNFe;
        }

        /**
         * Sets the value of the resNFe property.
         * 
         * @param value
         *     allowed object is
         *     {@link RetConsNFeDest.Ret.ResNFe }
         *     
         */
        public void setResNFe(RetConsNFeDest.Ret.ResNFe value) {
            this.resNFe = value;
        }

        /**
         * Gets the value of the resCanc property.
         * 
         * @return
         *     possible object is
         *     {@link RetConsNFeDest.Ret.ResCanc }
         *     
         */
        public RetConsNFeDest.Ret.ResCanc getResCanc() {
            return resCanc;
        }

        /**
         * Sets the value of the resCanc property.
         * 
         * @param value
         *     allowed object is
         *     {@link RetConsNFeDest.Ret.ResCanc }
         *     
         */
        public void setResCanc(RetConsNFeDest.Ret.ResCanc value) {
            this.resCanc = value;
        }

        /**
         * Gets the value of the resCCe property.
         * 
         * @return
         *     possible object is
         *     {@link RetConsNFeDest.Ret.ResCCe }
         *     
         */
        public RetConsNFeDest.Ret.ResCCe getResCCe() {
            return resCCe;
        }

        /**
         * Sets the value of the resCCe property.
         * 
         * @param value
         *     allowed object is
         *     {@link RetConsNFeDest.Ret.ResCCe }
         *     
         */
        public void setResCCe(RetConsNFeDest.Ret.ResCCe value) {
            this.resCCe = value;
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
         *         &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="CNPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="CPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="xNome" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="IE" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="dEmi" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="vNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="digVal" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
         *         &lt;element name="cSitNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="cSitConf" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "chNFe",
            "cnpj",
            "cpf",
            "xNome",
            "ie",
            "dEmi",
            "tpNF",
            "vnf",
            "digVal",
            "dhRecbto",
            "cSitNFe",
            "cSitConf"
        })
        public static class ResCanc {

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
            @XmlSchemaType(name = "anySimpleType")
            protected Object dhRecbto;
            @XmlElement(required = true)
            protected String cSitNFe;
            @XmlElement(required = true)
            protected String cSitConf;
            @XmlAttribute(name = "NSU", required = true)
            protected String nsu;

            /**
             * Gets the value of the chNFe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChNFe() {
                return chNFe;
            }

            /**
             * Sets the value of the chNFe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChNFe(String value) {
                this.chNFe = value;
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
             * Gets the value of the cpf property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCPF() {
                return cpf;
            }

            /**
             * Sets the value of the cpf property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCPF(String value) {
                this.cpf = value;
            }

            /**
             * Gets the value of the xNome property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getXNome() {
                return xNome;
            }

            /**
             * Sets the value of the xNome property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setXNome(String value) {
                this.xNome = value;
            }

            /**
             * Gets the value of the ie property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIE() {
                return ie;
            }

            /**
             * Sets the value of the ie property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIE(String value) {
                this.ie = value;
            }

            /**
             * Gets the value of the dEmi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDEmi() {
                return dEmi;
            }

            /**
             * Sets the value of the dEmi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDEmi(String value) {
                this.dEmi = value;
            }

            /**
             * Gets the value of the tpNF property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTpNF() {
                return tpNF;
            }

            /**
             * Sets the value of the tpNF property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTpNF(String value) {
                this.tpNF = value;
            }

            /**
             * Gets the value of the vnf property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVNF() {
                return vnf;
            }

            /**
             * Sets the value of the vnf property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVNF(String value) {
                this.vnf = value;
            }

            /**
             * Gets the value of the digVal property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDigVal() {
                return digVal;
            }

            /**
             * Sets the value of the digVal property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDigVal(String value) {
                this.digVal = value;
            }

            /**
             * Gets the value of the dhRecbto property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getDhRecbto() {
                return dhRecbto;
            }

            /**
             * Sets the value of the dhRecbto property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setDhRecbto(Object value) {
                this.dhRecbto = value;
            }

            /**
             * Gets the value of the cSitNFe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCSitNFe() {
                return cSitNFe;
            }

            /**
             * Sets the value of the cSitNFe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCSitNFe(String value) {
                this.cSitNFe = value;
            }

            /**
             * Gets the value of the cSitConf property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCSitConf() {
                return cSitConf;
            }

            /**
             * Sets the value of the cSitConf property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCSitConf(String value) {
                this.cSitConf = value;
            }

            /**
             * Gets the value of the nsu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNSU() {
                return nsu;
            }

            /**
             * Sets the value of the nsu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNSU(String value) {
                this.nsu = value;
            }

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
         *         &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="dhEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="tpEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="nSeqEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="descEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="xCorrecao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
         *       &lt;/sequence>
         *       &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "chNFe",
            "dhEvento",
            "tpEvento",
            "nSeqEvento",
            "descEvento",
            "xCorrecao",
            "tpNF",
            "dhRecbto"
        })
        public static class ResCCe {

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
            @XmlSchemaType(name = "anySimpleType")
            protected Object dhRecbto;
            @XmlAttribute(name = "NSU", required = true)
            protected String nsu;

            /**
             * Gets the value of the chNFe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChNFe() {
                return chNFe;
            }

            /**
             * Sets the value of the chNFe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChNFe(String value) {
                this.chNFe = value;
            }

            /**
             * Gets the value of the dhEvento property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDhEvento() {
                return dhEvento;
            }

            /**
             * Sets the value of the dhEvento property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDhEvento(String value) {
                this.dhEvento = value;
            }

            /**
             * Gets the value of the tpEvento property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTpEvento() {
                return tpEvento;
            }

            /**
             * Sets the value of the tpEvento property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTpEvento(String value) {
                this.tpEvento = value;
            }

            /**
             * Gets the value of the nSeqEvento property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNSeqEvento() {
                return nSeqEvento;
            }

            /**
             * Sets the value of the nSeqEvento property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNSeqEvento(String value) {
                this.nSeqEvento = value;
            }

            /**
             * Gets the value of the descEvento property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescEvento() {
                return descEvento;
            }

            /**
             * Sets the value of the descEvento property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescEvento(String value) {
                this.descEvento = value;
            }

            /**
             * Gets the value of the xCorrecao property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getXCorrecao() {
                return xCorrecao;
            }

            /**
             * Sets the value of the xCorrecao property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setXCorrecao(String value) {
                this.xCorrecao = value;
            }

            /**
             * Gets the value of the tpNF property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTpNF() {
                return tpNF;
            }

            /**
             * Sets the value of the tpNF property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTpNF(String value) {
                this.tpNF = value;
            }

            /**
             * Gets the value of the dhRecbto property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getDhRecbto() {
                return dhRecbto;
            }

            /**
             * Sets the value of the dhRecbto property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setDhRecbto(Object value) {
                this.dhRecbto = value;
            }

            /**
             * Gets the value of the nsu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNSU() {
                return nsu;
            }

            /**
             * Sets the value of the nsu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNSU(String value) {
                this.nsu = value;
            }

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
         *         &lt;element name="chNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="CNPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="CPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="xNome" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="IE" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="dEmi" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="tpNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="vNF" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="digVal" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="dhRecbto" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
         *         &lt;element name="cSitNFe" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="cSitConf" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="NSU" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "chNFe",
            "cnpj",
            "cpf",
            "xNome",
            "ie",
            "dEmi",
            "tpNF",
            "vnf",
            "digVal",
            "dhRecbto",
            "cSitNFe",
            "cSitConf"
        })
        public static class ResNFe {

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
            @XmlSchemaType(name = "anySimpleType")
            protected Object dhRecbto;
            @XmlElement(required = true)
            protected String cSitNFe;
            @XmlElement(required = true)
            protected String cSitConf;
            @XmlAttribute(name = "NSU", required = true)
            protected String nsu;

            /**
             * Gets the value of the chNFe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChNFe() {
                return chNFe;
            }

            /**
             * Sets the value of the chNFe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChNFe(String value) {
                this.chNFe = value;
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
             * Gets the value of the cpf property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCPF() {
                return cpf;
            }

            /**
             * Sets the value of the cpf property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCPF(String value) {
                this.cpf = value;
            }

            /**
             * Gets the value of the xNome property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getXNome() {
                return xNome;
            }

            /**
             * Sets the value of the xNome property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setXNome(String value) {
                this.xNome = value;
            }

            /**
             * Gets the value of the ie property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIE() {
                return ie;
            }

            /**
             * Sets the value of the ie property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIE(String value) {
                this.ie = value;
            }

            /**
             * Gets the value of the dEmi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDEmi() {
                return dEmi;
            }

            /**
             * Sets the value of the dEmi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDEmi(String value) {
                this.dEmi = value;
            }

            /**
             * Gets the value of the tpNF property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTpNF() {
                return tpNF;
            }

            /**
             * Sets the value of the tpNF property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTpNF(String value) {
                this.tpNF = value;
            }

            /**
             * Gets the value of the vnf property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVNF() {
                return vnf;
            }

            /**
             * Sets the value of the vnf property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVNF(String value) {
                this.vnf = value;
            }

            /**
             * Gets the value of the digVal property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDigVal() {
                return digVal;
            }

            /**
             * Sets the value of the digVal property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDigVal(String value) {
                this.digVal = value;
            }

            /**
             * Gets the value of the dhRecbto property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getDhRecbto() {
                return dhRecbto;
            }

            /**
             * Sets the value of the dhRecbto property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setDhRecbto(Object value) {
                this.dhRecbto = value;
            }

            /**
             * Gets the value of the cSitNFe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCSitNFe() {
                return cSitNFe;
            }

            /**
             * Sets the value of the cSitNFe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCSitNFe(String value) {
                this.cSitNFe = value;
            }

            /**
             * Gets the value of the cSitConf property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCSitConf() {
                return cSitConf;
            }

            /**
             * Sets the value of the cSitConf property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCSitConf(String value) {
                this.cSitConf = value;
            }

            /**
             * Gets the value of the nsu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNSU() {
                return nsu;
            }

            /**
             * Sets the value of the nsu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNSU(String value) {
                this.nsu = value;
            }

        }

    }

}
