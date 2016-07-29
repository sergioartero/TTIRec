package br.com.tti.sefaz.sender.axis;

import java.util.Hashtable;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.soap.SOAPConstants;

import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.remote.ServicesConfig;
import br.com.tti.sefaz.systemconfig.SystemProperties;

public class CallFactory {

	private ServicesConfig config;
	private Hashtable<String, Call> callers;
	private Service service;

	public CallFactory(ServicesConfig config) {
		this.config = config;
		this.callers = new Hashtable<String, Call>();
	}

	public String findURL(String uf, String idServico, String ambient) {
		String url = null;

		if (ambient.equals(SystemProperties.AMBIENT_HOMOLOGACAO)) {
			url = this.config.getUrlServicosHomolgacao().get(uf).get(idServico);
		}

		if (ambient.equals(SystemProperties.AMBIENT_PRODUCAO)) {
			url = this.config.getUrlServicosNormal().get(uf).get(idServico);
		}

		if (ambient.equals(SystemProperties.AMBIENT_SCAN)) {
			url = this.config.getUrlServicosScan().get(uf).get(idServico);
		}

		MyLogger.getLog().info("URL:" + url);

		return url;
	}

	public Call createCaller(String uf, String idServico, String ambient)
			throws MySenderException {

		String url = null;

		if (ambient.equals(SystemProperties.AMBIENT_HOMOLOGACAO)) {
			url = this.config.getUrlServicosHomolgacao().get(uf).get(idServico);
		}

		if (ambient.equals(SystemProperties.AMBIENT_PRODUCAO)) {
			url = this.config.getUrlServicosNormal().get(uf).get(idServico);
		}

		if (ambient.equals(SystemProperties.AMBIENT_SCAN)) {
			url = this.config.getUrlServicosScan().get(uf).get(idServico);
		}

		url = url.replace("?wsdl", "");
		MyLogger.getLog().info("URL:" + url);

		if (service == null) {
			this.service = new Service();
		}

		Call call = this.callers.get(url);

		if (call == null) {
			try {
				call = (Call) service.createCall();
				call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
				call.setTargetEndpointAddress(new java.net.URL(url));

				call.setOperationName(new QName(
						"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2",
						"nfeRecepcaoLote2"));
				/*
				 * call.setOperationName(new
				 * QName(this.config.getNamespaceNames() .get(url),
				 * this.config.getNomeOperacao().get(url)));
				 */

			} catch (Exception e) {
				e.printStackTrace();

				throw new MySenderException(e, MySenderException.ERROR_DISPATCH
						+ url);
			}
			this.callers.put(url, call);
		}

		return call;
	}
}
