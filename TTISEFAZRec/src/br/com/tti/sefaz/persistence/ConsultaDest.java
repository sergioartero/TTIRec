package br.com.tti.sefaz.persistence;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.tti.sefaz.consultadest.TConsNFeDest;
import br.com.tti.sefaz.consultadest.TRetConsNFeDest;

@Entity
public class ConsultaDest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Embedded
	private TConsNFeDest consulta;

	@Embedded
	private TRetConsNFeDest resposta;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataproc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TConsNFeDest getConsulta() {
		return consulta;
	}

	public void setConsulta(TConsNFeDest consulta) {
		this.consulta = consulta;
	}

	public TRetConsNFeDest getResposta() {
		return resposta;
	}

	public void setResposta(TRetConsNFeDest resposta) {
		this.resposta = resposta;
	}

	public Date getDataproc() {
		return dataproc;
	}

	public void setDataproc(Date dataproc) {
		this.dataproc = dataproc;
	}

}
