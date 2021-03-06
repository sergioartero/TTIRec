//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.28 at 09:44:11 AM BRST 
//


package br.org.abrasf.nfse.semfazonline.esquemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tcDadosServico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tcDadosServico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Valores" type="{http://www.abrasf.org.br/nfse.xsd}tcValoresDeclaracaoServico"/>
 *         &lt;element name="IssRetido" type="{http://www.abrasf.org.br/nfse.xsd}tsSimNao"/>
 *         &lt;element name="ResponsavelRetencao" type="{http://www.abrasf.org.br/nfse.xsd}tsResponsavelRetencao" minOccurs="0"/>
 *         &lt;element name="ItemListaServico" type="{http://www.abrasf.org.br/nfse.xsd}tsItemListaServico"/>
 *         &lt;element name="CodigoCnae" type="{http://www.abrasf.org.br/nfse.xsd}tsCodigoCnae" minOccurs="0"/>
 *         &lt;element name="CodigoTributacaoMunicipio" type="{http://www.abrasf.org.br/nfse.xsd}tsCodigoTributacao" minOccurs="0"/>
 *         &lt;element name="Discriminacao" type="{http://www.abrasf.org.br/nfse.xsd}tsDiscriminacao"/>
 *         &lt;element name="CodigoMunicipio" type="{http://www.abrasf.org.br/nfse.xsd}tsCodigoMunicipioIbge"/>
 *         &lt;element name="CodigoPais" type="{http://www.abrasf.org.br/nfse.xsd}tsCodigoPaisBacen" minOccurs="0"/>
 *         &lt;element name="ExigibilidadeISS" type="{http://www.abrasf.org.br/nfse.xsd}tsExigibilidadeISS"/>
 *         &lt;element name="MunicipioIncidencia" type="{http://www.abrasf.org.br/nfse.xsd}tsCodigoMunicipioIbge" minOccurs="0"/>
 *         &lt;element name="NumeroProcesso" type="{http://www.abrasf.org.br/nfse.xsd}tsNumeroProcesso" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcDadosServico", propOrder = {
    "valores",
    "issRetido",
    "responsavelRetencao",
    "itemListaServico",
    "codigoCnae",
    "codigoTributacaoMunicipio",
    "discriminacao",
    "codigoMunicipio",
    "codigoPais",
    "exigibilidadeISS",
    "municipioIncidencia",
    "numeroProcesso"
})
public class TcDadosServico {

    @XmlElement(name = "Valores", required = true)
    protected TcValoresDeclaracaoServico valores;
    @XmlElement(name = "IssRetido")
    protected byte issRetido;
    @XmlElement(name = "ResponsavelRetencao")
    protected Byte responsavelRetencao;
    @XmlElement(name = "ItemListaServico", required = true)
    protected String itemListaServico;
    @XmlElement(name = "CodigoCnae")
    protected Integer codigoCnae;
    @XmlElement(name = "CodigoTributacaoMunicipio")
    protected String codigoTributacaoMunicipio;
    @XmlElement(name = "Discriminacao", required = true)
    protected String discriminacao;
    @XmlElement(name = "CodigoMunicipio")
    protected int codigoMunicipio;
    @XmlElement(name = "CodigoPais")
    protected String codigoPais;
    @XmlElement(name = "ExigibilidadeISS")
    protected byte exigibilidadeISS;
    @XmlElement(name = "MunicipioIncidencia")
    protected Integer municipioIncidencia;
    @XmlElement(name = "NumeroProcesso")
    protected String numeroProcesso;

    /**
     * Gets the value of the valores property.
     * 
     * @return
     *     possible object is
     *     {@link TcValoresDeclaracaoServico }
     *     
     */
    public TcValoresDeclaracaoServico getValores() {
        return valores;
    }

    /**
     * Sets the value of the valores property.
     * 
     * @param value
     *     allowed object is
     *     {@link TcValoresDeclaracaoServico }
     *     
     */
    public void setValores(TcValoresDeclaracaoServico value) {
        this.valores = value;
    }

    /**
     * Gets the value of the issRetido property.
     * 
     */
    public byte getIssRetido() {
        return issRetido;
    }

    /**
     * Sets the value of the issRetido property.
     * 
     */
    public void setIssRetido(byte value) {
        this.issRetido = value;
    }

    /**
     * Gets the value of the responsavelRetencao property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getResponsavelRetencao() {
        return responsavelRetencao;
    }

    /**
     * Sets the value of the responsavelRetencao property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setResponsavelRetencao(Byte value) {
        this.responsavelRetencao = value;
    }

    /**
     * Gets the value of the itemListaServico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemListaServico() {
        return itemListaServico;
    }

    /**
     * Sets the value of the itemListaServico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemListaServico(String value) {
        this.itemListaServico = value;
    }

    /**
     * Gets the value of the codigoCnae property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodigoCnae() {
        return codigoCnae;
    }

    /**
     * Sets the value of the codigoCnae property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodigoCnae(Integer value) {
        this.codigoCnae = value;
    }

    /**
     * Gets the value of the codigoTributacaoMunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoTributacaoMunicipio() {
        return codigoTributacaoMunicipio;
    }

    /**
     * Sets the value of the codigoTributacaoMunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoTributacaoMunicipio(String value) {
        this.codigoTributacaoMunicipio = value;
    }

    /**
     * Gets the value of the discriminacao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscriminacao() {
        return discriminacao;
    }

    /**
     * Sets the value of the discriminacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscriminacao(String value) {
        this.discriminacao = value;
    }

    /**
     * Gets the value of the codigoMunicipio property.
     * 
     */
    public int getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * Sets the value of the codigoMunicipio property.
     * 
     */
    public void setCodigoMunicipio(int value) {
        this.codigoMunicipio = value;
    }

    /**
     * Gets the value of the codigoPais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPais() {
        return codigoPais;
    }

    /**
     * Sets the value of the codigoPais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPais(String value) {
        this.codigoPais = value;
    }

    /**
     * Gets the value of the exigibilidadeISS property.
     * 
     */
    public byte getExigibilidadeISS() {
        return exigibilidadeISS;
    }

    /**
     * Sets the value of the exigibilidadeISS property.
     * 
     */
    public void setExigibilidadeISS(byte value) {
        this.exigibilidadeISS = value;
    }

    /**
     * Gets the value of the municipioIncidencia property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMunicipioIncidencia() {
        return municipioIncidencia;
    }

    /**
     * Sets the value of the municipioIncidencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMunicipioIncidencia(Integer value) {
        this.municipioIncidencia = value;
    }

    /**
     * Gets the value of the numeroProcesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    /**
     * Sets the value of the numeroProcesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProcesso(String value) {
        this.numeroProcesso = value;
    }

}
