//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.28 at 11:09:48 AM BRST 
//

package br.com.tti.sefaz.nfes.schemas.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cabecalho">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CodCidade" type="{http://localhost:8080/WsNFe2/tp}tpCodCidade"/>
 *                   &lt;element name="CPFCNPJRemetente" type="{http://localhost:8080/WsNFe2/tp}tpCPFCNPJ"/>
 *                   &lt;element name="InscricaoMunicipalPrestador" type="{http://localhost:8080/WsNFe2/tp}tpInscricaoMunicipal"/>
 *                   &lt;element name="dtInicio" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="dtFim" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="NotaInicial" type="{http://localhost:8080/WsNFe2/tp}tpNumero" minOccurs="0"/>
 *                   &lt;element name="Versao" type="{http://localhost:8080/WsNFe2/tp}tpVersao"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "cabecalho" })
@XmlRootElement(name = "ReqConsultaNotas")
public class ReqConsultaNotas {

	@XmlElement(name = "Cabecalho", required = true)
	protected ReqConsultaNotas.Cabecalho cabecalho;

	/**
	 * Gets the value of the cabecalho property.
	 * 
	 * @return possible object is {@link ReqConsultaNotas.Cabecalho }
	 * 
	 */
	public ReqConsultaNotas.Cabecalho getCabecalho() {
		return cabecalho;
	}

	/**
	 * Sets the value of the cabecalho property.
	 * 
	 * @param value
	 *            allowed object is {@link ReqConsultaNotas.Cabecalho }
	 * 
	 */
	public void setCabecalho(ReqConsultaNotas.Cabecalho value) {
		this.cabecalho = value;
	}

	/**
	 * Gets the value of the signature property.
	 * 
	 * @return possible object is {@link SignatureType }
	 * 
	 */

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="CodCidade" type="{http://localhost:8080/WsNFe2/tp}tpCodCidade"/>
	 *         &lt;element name="CPFCNPJRemetente" type="{http://localhost:8080/WsNFe2/tp}tpCPFCNPJ"/>
	 *         &lt;element name="InscricaoMunicipalPrestador" type="{http://localhost:8080/WsNFe2/tp}tpInscricaoMunicipal"/>
	 *         &lt;element name="dtInicio" type="{http://www.w3.org/2001/XMLSchema}date"/>
	 *         &lt;element name="dtFim" type="{http://www.w3.org/2001/XMLSchema}date"/>
	 *         &lt;element name="NotaInicial" type="{http://localhost:8080/WsNFe2/tp}tpNumero" minOccurs="0"/>
	 *         &lt;element name="Versao" type="{http://localhost:8080/WsNFe2/tp}tpVersao"/>
	 *       &lt;/sequence>
	 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "codCidade", "cpfcnpjRemetente",
			"inscricaoMunicipalPrestador", "dtInicio", "dtFim", "notaInicial",
			"versao" })
	public static class Cabecalho {

		@XmlElement(name = "CodCidade")
		protected long codCidade;
		@XmlElement(name = "CPFCNPJRemetente", required = true)
		protected String cpfcnpjRemetente;
		@XmlElement(name = "InscricaoMunicipalPrestador")
		protected long inscricaoMunicipalPrestador;
		@XmlElement(required = true)
		@XmlSchemaType(name = "date")
		protected String dtInicio;
		@XmlElement(required = true)
		@XmlSchemaType(name = "date")
		protected String dtFim;
		@XmlElement(name = "NotaInicial")
		protected Integer notaInicial;
		@XmlElement(name = "Versao")
		protected long versao;
		@XmlAttribute(name = "Id")
		protected String id;

		/**
		 * Gets the value of the codCidade property.
		 * 
		 */
		public long getCodCidade() {
			return codCidade;
		}

		/**
		 * Sets the value of the codCidade property.
		 * 
		 */
		public void setCodCidade(long value) {
			this.codCidade = value;
		}

		/**
		 * Gets the value of the cpfcnpjRemetente property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getCPFCNPJRemetente() {
			return cpfcnpjRemetente;
		}

		/**
		 * Sets the value of the cpfcnpjRemetente property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setCPFCNPJRemetente(String value) {
			this.cpfcnpjRemetente = value;
		}

		/**
		 * Gets the value of the inscricaoMunicipalPrestador property.
		 * 
		 */
		public long getInscricaoMunicipalPrestador() {
			return inscricaoMunicipalPrestador;
		}

		/**
		 * Sets the value of the inscricaoMunicipalPrestador property.
		 * 
		 */
		public void setInscricaoMunicipalPrestador(long value) {
			this.inscricaoMunicipalPrestador = value;
		}

		/**
		 * Gets the value of the dtInicio property.
		 * 
		 * @return possible object is {@link XMLGregorianCalendar }
		 * 
		 */

		/**
		 * Gets the value of the notaInicial property.
		 * 
		 * @return possible object is {@link Integer }
		 * 
		 */
		public Integer getNotaInicial() {
			return notaInicial;
		}

		public String getDtInicio() {
			return dtInicio;
		}

		public void setDtInicio(String dtInicio) {
			this.dtInicio = dtInicio;
		}

		public String getDtFim() {
			return dtFim;
		}

		public void setDtFim(String dtFim) {
			this.dtFim = dtFim;
		}

		/**
		 * Sets the value of the notaInicial property.
		 * 
		 * @param value
		 *            allowed object is {@link Integer }
		 * 
		 */
		public void setNotaInicial(Integer value) {
			this.notaInicial = value;
		}

		/**
		 * Gets the value of the versao property.
		 * 
		 */
		public long getVersao() {
			return versao;
		}

		/**
		 * Sets the value of the versao property.
		 * 
		 */
		public void setVersao(long value) {
			this.versao = value;
		}

		/**
		 * Gets the value of the id property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getId() {
			return id;
		}

		/**
		 * Sets the value of the id property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setId(String value) {
			this.id = value;
		}

	}

}