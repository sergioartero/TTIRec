package br.com.tti.sefaz.sender.port;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.xml.namespace.QName;

import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.systemconfig.SystemProperties;

public class PortFactory {
	private Hashtable<String, MyServer> services;
	private Hashtable<String, Hashtable<String, Object>> ports;

	protected Hashtable<String, String> portNames;
	protected Hashtable<String, String> namespaceNames;
	protected Hashtable<String, String> nomeServico;
	protected Hashtable<String, Class> classServices;

	// UF, id servico, data
	protected Hashtable<String, Hashtable<String, String>> urlServicosNormal;
	protected Hashtable<String, Hashtable<String, String>> urlServicosScan;
	protected Hashtable<String, Hashtable<String, String>> urlServicosHomolgacao;
	protected Hashtable<String, Hashtable<String, String>> urlServicosDpec;

	public PortFactory() {
		this.services = new Hashtable<String, MyServer>();
		this.ports = new Hashtable<String, Hashtable<String, Object>>();
	}

	public Object criarPort(String idServico, String UF, String tipo)
			throws RemoteException {
		Class c = this.classServices.get(idServico);
		return criarPort(idServico, c, UF, tipo);
	}

	public Object criarPort(String idServico, Class classe, String UF,
			String ambient) throws RemoteException {
		String url = null;

		if (ambient.equals(SystemProperties.AMBIENT_PRODUCAO)) {
			url = this.urlServicosNormal.get(UF).get(idServico);
		}

		if (ambient.equals(SystemProperties.AMBIENT_HOMOLOGACAO)) {
			url = this.urlServicosHomolgacao.get(UF).get(idServico);
		}

		if (ambient.equals(SystemProperties.AMBIENT_SCAN)) {
			url = this.urlServicosScan.get(UF).get(idServico);
		}

		QName portName = new QName(this.namespaceNames.get(url), this.portNames
				.get(url));
		QName servicoName = new QName(this.namespaceNames.get(url),
				this.nomeServico.get(url));

		MyServer servico = this.services.get(url);
		if (servico == null) {
			URL urlO = null;
			try {
				urlO = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				servico = new MyServer(urlO, servicoName);
			} catch (Exception e) {
				e.printStackTrace();
				this.services.remove(url);
				throw new MySenderException(e, MySenderException.ERROR_SERVICE
						+ url);
			}
			if (servico != null)
				this.services.put(url, servico);
		}

		Hashtable<String, Object> portUF = this.ports.get(url);
		if (portUF == null) {
			portUF = new Hashtable<String, Object>();
		}

		Object port = portUF.get(classe.getCanonicalName());
		if (port == null) {
			try {
				port = servico.getPort(portName, classe);
			} catch (Exception e) {
				e.printStackTrace();
				this.ports.remove(classe.getCanonicalName());
				throw new MySenderException(e, MySenderException.ERROR_PORT
						+ url + portName);
			}
			portUF.put(classe.getCanonicalName(), port);
		}

		this.ports.put(url, portUF);

		return port;
	}

	public void setPortNames(Hashtable<String, String> portNames) {
		this.portNames = portNames;
	}

	public void setNamespaceNames(Hashtable<String, String> namespaceNames) {
		this.namespaceNames = namespaceNames;
	}

	public void setUrlServicosNormal(
			Hashtable<String, Hashtable<String, String>> urlServicosNormal) {
		this.urlServicosNormal = urlServicosNormal;
	}

	public void setUrlServicosScan(
			Hashtable<String, Hashtable<String, String>> urlServicosScan) {
		this.urlServicosScan = urlServicosScan;
	}

	public void setUrlServicosHomolgacao(
			Hashtable<String, Hashtable<String, String>> urlServicosHomolgacao) {
		this.urlServicosHomolgacao = urlServicosHomolgacao;
	}

	public void setNomeServico(Hashtable<String, String> nomeServico) {
		this.nomeServico = nomeServico;
	}

	public void setClassServices(Hashtable<String, Class> classServices) {
		this.classServices = classServices;
	}

	public void setUrlServicosDpec(
			Hashtable<String, Hashtable<String, String>> urlServicosDpec) {
		this.urlServicosDpec = urlServicosDpec;
	}

}
