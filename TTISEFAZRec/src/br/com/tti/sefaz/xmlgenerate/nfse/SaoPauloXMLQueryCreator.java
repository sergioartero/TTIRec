package br.com.tti.sefaz.xmlgenerate.nfse;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.logging.Level;

import br.com.taragona.nfe.xmlgenerator.XMLGenerator;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.nfes.schemas.saopaulo.PedidoConsultaNFePeriodo;
import br.com.tti.sefaz.nfes.schemas.saopaulo.TpCPFCNPJ;
import br.com.tti.sefaz.nfes.schemas.saopaulo.PedidoConsultaNFePeriodo.Cabecalho;
import br.com.tti.sefaz.nfes.schemas.saopaulo.retornoconsulta.RetornoConsulta;
import br.com.tti.sefaz.signer.Signer;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.xmlgenerate.XMLQueryCreator;

public class SaoPauloXMLQueryCreator implements XMLQueryCreator {
	private Signer signer;
	private XMLGenerator geninput;
	private XMLGenerator genouput;
	private SimpleDateFormat sdf;

	public SaoPauloXMLQueryCreator(Signer signer) {
		this.signer = signer;
		this.geninput = new XMLGenerator(
				"br.com.tti.sefaz.nfes.schemas.saopaulo");
		this.genouput = new XMLGenerator(
				"br.com.tti.sefaz.nfes.schemas.saopaulo.retornoconsulta");
		this.sdf = new SimpleDateFormat("yyyy-MM-dd");
	}

	@Override
	public <T> T convertResult(String xml, Class<T> classreturn) {
		try {
			Object obj = this.genouput.toObject(xml);
			return classreturn.cast(obj);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}
		return null;
	}

	@Override
	public String createXMLQuery(String cnpjs, String municipio,
			Hashtable<String, Object> params) {

		PedidoConsultaNFePeriodo periodo = new PedidoConsultaNFePeriodo();

		Cabecalho cc = new Cabecalho();
		cc.setVersao(1);
		cc.setNumeroPagina(1);
		TpCPFCNPJ cnpj = new TpCPFCNPJ();
		cnpj.setCNPJ(cnpjs);
		cc.setCPFCNPJ(cnpj);
		TpCPFCNPJ cnpjrem = new TpCPFCNPJ();
		cnpjrem.setCNPJ(cnpjs);
		cc.setCPFCNPJRemetente(cnpjrem);

		cc.setDtInicio(this.sdf.format(params.get("dataini")));
		cc.setDtFim(this.sdf.format(params.get("datafim")));

		// cc.setDtInicio(value);

		periodo.setCabecalho(cc);

		String xml = null;
		try {
			xml = geninput.toXMLString(periodo);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		System.out.println(xml);

		xml = xml
				.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
						"");
		/*
		 * value = value.replace(":ns3", ""); value = value.replace("ns3:", "");
		 * value = value.replace(
		 * "xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" ", "");
		 */

		System.out.println("pre sign: " + xml);

		String signedxml = null;
		try {
			signedxml = this.signer.signForCNPJ(cnpjs, xml,
					SystemProperties.ID_SERVICO_CONSULTA);
		} catch (RemoteException e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		return signedxml;

	}

}
