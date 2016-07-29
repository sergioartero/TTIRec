package br.com.tti.sefaz.sender.dispatch;

import java.util.Hashtable;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.remote.ServicesConfig;
import br.com.tti.sefaz.systemconfig.SystemProperties;

public class DispatchFactory {

	// url, data
	private Hashtable<String, Service> services;
	private Hashtable<String, Dispatch<SOAPMessage>> dispatchers;
	private ServicesConfig config;

	public DispatchFactory(ServicesConfig config) {
		this.config = config;
		this.services = new Hashtable<String, Service>();
		this.dispatchers = new Hashtable<String, Dispatch<SOAPMessage>>();
	}

	public Dispatch<SOAPMessage> createDispath(String uf, String idServico,
			String ambient) throws MySenderException {

		String url = null;

		if (!ambient.contains("v200")) {
			ambient = ambient + "v200";
		}

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

		Service service = this.services.get(url);

		QName portQName = new QName(config.getNamespaceNames().get(url), config
				.getPortNames().get(url));

		if (service == null) {
			QName servQName = new QName(config.getNamespaceNames().get(url),
					config.getNomeServico().get(url));
			try {
				service = Service.create(servQName);
			} catch (Exception e) {
				e.printStackTrace();
				this.services.remove(url);
				throw new MySenderException(e, MySenderException.ERROR_SERVICE
						+ url);
			}

			// String version = config.getVersionSOAP().get(url);

			// if (version.equals(SystemProperties.SOAP_12))
			service.addPort(portQName,
					javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING, url);
			/*
			 * if (version.equals(SystemProperties.SOAP_11))
			 * service.addPort(portQName,
			 * javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, url);
			 */
			this.services.put(url, service);
		}

		Dispatch<SOAPMessage> dispSOAP = this.dispatchers.get(url);

		if (dispSOAP == null) {
			try {
				dispSOAP = service.createDispatch(portQName, SOAPMessage.class,
						Service.Mode.MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				this.services.remove(url);
				throw new MySenderException(e, MySenderException.ERROR_DISPATCH
						+ url);
			}
			this.dispatchers.put(url, dispSOAP);
		}
		return dispSOAP;
	}
}
