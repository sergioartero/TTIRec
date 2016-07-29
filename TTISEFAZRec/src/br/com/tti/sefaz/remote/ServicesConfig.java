package br.com.tti.sefaz.remote;

import java.io.Serializable;
import java.util.Hashtable;

public class ServicesConfig implements Serializable {
	private Hashtable<String, Hashtable<String, String>> urlServicosNormal;
	private Hashtable<String, Hashtable<String, String>> urlServicosScan;
	private Hashtable<String, Hashtable<String, String>> urlServicosHomolgacao;
	private Hashtable<String, Hashtable<String, String>> urlServicosDpec;

	private Hashtable<String, String> portNames;
	private Hashtable<String, String> namespaceNames;
	private Hashtable<String, String> nomeOperacao;
	private Hashtable<String, String> nomeServico;
	private Hashtable<String, String> classes;
	private Hashtable<String, String> versionSOAP;

	public Hashtable<String, Hashtable<String, String>> getUrlServicosNormal() {
		return urlServicosNormal;
	}

	public void setUrlServicosNormal(
			Hashtable<String, Hashtable<String, String>> urlServicosNormal) {
		this.urlServicosNormal = urlServicosNormal;
	}

	public Hashtable<String, Hashtable<String, String>> getUrlServicosScan() {
		return urlServicosScan;
	}

	public void setUrlServicosScan(
			Hashtable<String, Hashtable<String, String>> urlServicosScan) {
		this.urlServicosScan = urlServicosScan;
	}

	public Hashtable<String, Hashtable<String, String>> getUrlServicosHomolgacao() {
		return urlServicosHomolgacao;
	}

	public void setUrlServicosHomolgacao(
			Hashtable<String, Hashtable<String, String>> urlServicosHomolgacao) {
		this.urlServicosHomolgacao = urlServicosHomolgacao;
	}

	public Hashtable<String, Hashtable<String, String>> getUrlServicosDpec() {
		return urlServicosDpec;
	}

	public void setUrlServicosDpec(
			Hashtable<String, Hashtable<String, String>> urlServicosDpec) {
		this.urlServicosDpec = urlServicosDpec;
	}

	public Hashtable<String, String> getPortNames() {
		return portNames;
	}

	public void setPortNames(Hashtable<String, String> portNames) {
		this.portNames = portNames;
	}

	public Hashtable<String, String> getNamespaceNames() {
		return namespaceNames;
	}

	public void setNamespaceNames(Hashtable<String, String> namespaceNames) {
		this.namespaceNames = namespaceNames;
	}

	public Hashtable<String, String> getNomeOperacao() {
		return nomeOperacao;
	}

	public void setNomeOperacao(Hashtable<String, String> nomeOperacao) {
		this.nomeOperacao = nomeOperacao;
	}

	public Hashtable<String, String> getNomeServico() {
		return nomeServico;
	}

	public void setNomeServico(Hashtable<String, String> nomeServico) {
		this.nomeServico = nomeServico;
	}

	public Hashtable<String, String> getClasses() {
		return classes;
	}

	public void setClasses(Hashtable<String, String> classes) {
		this.classes = classes;
	}

	public Hashtable<String, String> getVersionSOAP() {
		return versionSOAP;
	}

	public void setVersionSOAP(Hashtable<String, String> versionSOAP) {
		this.versionSOAP = versionSOAP;
	}

}
