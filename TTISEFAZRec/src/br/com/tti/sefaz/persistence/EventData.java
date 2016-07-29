package br.com.tti.sefaz.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.tti.sefaz.event.xml.classes.TEvento;
import br.com.tti.sefaz.event.xml.classes.ret.TretEvento;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;

@Entity
public class EventData implements Serializable {

	static public enum EVENTO_STATUS {
		GERADO, ENVIADO, REJEITADO, AUTORIZADO, ERRO
	}

	// 210200 – Confirmação da Operação
	// 210210 – Ciência da Operação
	// 210220 – Desconhecimento da Operação
	// 210240 – Operação não Realizada
	static public enum TIPO_EVENTO {
		TODOS("000000", "Todos"), CONFIRM_OP("210200",
				"Confirmacao da Operacao"), CIENCIA_OP("210210",
				"Ciencia da Operacao"), DESCON_OP("210220",
				"Desconhecimento da Operacao"), OP_REALIZADA("210240",
				"Operacao nao Realizada");

		private final String codigo;
		private final String mensagem;

		private TIPO_EVENTO(String codigo, String mensagem) {
			this.codigo = codigo;
			this.mensagem = mensagem;
		}

		public String getCodigo() {
			return codigo;
		}

		public String getMensagem() {
			return mensagem;
		}

	}

	/*
	 * @GeneratedValue(strategy = GenerationType.TABLE)
	 * 
	 * @Id private int id;
	 */

	@EmbeddedId
	private EventId id;

	private TEvento event;

	private TretEvento retevent;

	private XML_STATE estado;

	private String mensagem;

	private String estadonfe;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataGerada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnviada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAutorizada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizada;

	@Lob
	private String xmlString;

	@Lob
	private String protocoloXML;

	private String xjust;

	private String descricao;

	private TIPO_EVENTO tipoEvento;

	private EVENTO_STATUS status;

	public EventId getId() {
		return id;
	}

	public void setId(EventId id) {
		this.id = id;
	}

	public TEvento getEvent() {
		return event;
	}

	public void setEvent(TEvento event) {
		this.event = event;
	}

	public XML_STATE getEstado() {
		return estado;
	}

	public void setEstado(XML_STATE estado) {
		this.estado = estado;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getEstadonfe() {
		return estadonfe;
	}

	public void setEstadonfe(String estadonfe) {
		this.estadonfe = estadonfe;
	}

	public Date getDataGerada() {
		return dataGerada;
	}

	public void setDataGerada(Date dataGerada) {
		this.dataGerada = dataGerada;
	}

	public Date getDataEnviada() {
		return dataEnviada;
	}

	public void setDataEnviada(Date dataEnviada) {
		this.dataEnviada = dataEnviada;
	}

	public Date getDataAutorizada() {
		return dataAutorizada;
	}

	public void setDataAutorizada(Date dataAutorizada) {
		this.dataAutorizada = dataAutorizada;
	}

	public String getProtocoloXML() {
		return protocoloXML;
	}

	public void setProtocoloXML(String protocoloXML) {
		this.protocoloXML = protocoloXML;
	}

	public Date getDataAtualizada() {
		return dataAtualizada;
	}

	public void setDataAtualizada(Date dataAtualizada) {
		this.dataAtualizada = dataAtualizada;
	}

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public String getXjust() {
		return xjust;
	}

	public void setXjust(String xjust) {
		this.xjust = xjust;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TIPO_EVENTO getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TIPO_EVENTO tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public EVENTO_STATUS getStatus() {
		return status;
	}

	public void setStatus(EVENTO_STATUS status) {
		this.status = status;
	}

	public TretEvento getRetevent() {
		return retevent;
	}

	public void setRetevent(TretEvento retevent) {
		this.retevent = retevent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventData other = (EventData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
