package br.com.tti.sefaz.sender.xml;

import java.io.File;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.sender.xml.schema.ConfiguracaoServico;
import br.com.tti.sefaz.sender.xml.schema.NFeServico;
import br.com.tti.sefaz.sender.xml.schema.NFeServicos;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.xml.XMLGenerator;

public class XMLConfigSender {

	// UF, id servico, data
	private Hashtable<String, Hashtable<String, String>> urlServicosNormal;
	private Hashtable<String, Hashtable<String, String>> urlServicosScan;
	private Hashtable<String, Hashtable<String, String>> urlServicosHomolgacao;
	private Hashtable<String, Hashtable<String, String>> urlServicosDPEC;

	private Hashtable<String, String> portNames;
	private Hashtable<String, String> namespaceNames;
	private Hashtable<String, String> nomeOperacao;
	private Hashtable<String, String> nomeServico;
	private Hashtable<String, String> classes;
	private Hashtable<String, String> versionSOAP;

	private Vector<String> ufs;

	private NFeServicos sevicos;
	private XMLGenerator generator;

	private String nomeFile;

	public XMLConfigSender(String nomeFile) {
		this.generator = new XMLGenerator("br.com.tti.sefaz.sender.xml.schema");
		this.nomeFile = nomeFile;

		File f = new File(nomeFile);
		try {
			this.sevicos = ((JAXBElement<NFeServicos>) this.generator
					.toObjectFromFile(f)).getValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.namespaceNames = new Hashtable<String, String>();
		this.portNames = new Hashtable<String, String>();
		this.nomeOperacao = new Hashtable<String, String>();
		this.nomeServico = new Hashtable<String, String>();
		this.classes = new Hashtable<String, String>();
		this.versionSOAP = new Hashtable<String, String>();

		this.urlServicosScan = new Hashtable<String, Hashtable<String, String>>();
		this.urlServicosNormal = new Hashtable<String, Hashtable<String, String>>();
		this.urlServicosHomolgacao = new Hashtable<String, Hashtable<String, String>>();
		this.urlServicosDPEC = new Hashtable<String, Hashtable<String, String>>();

		this.ufs = new Vector<String>();
	}

	private void ajustarServico(Hashtable<String, String> urls, String tipo,
			String uf) {
		List<NFeServico> nfeServicos = this.sevicos.getNFeServico();
		for (NFeServico servico : nfeServicos) {
			String[] ufs = servico.getUf();
			String[] tipos = servico.getTipo();

			Vector<String> ufsv = new Vector<String>();
			Vector<String> tiposv = new Vector<String>();

			for (String s : tipos) {
				tiposv.add(s);
			}
			for (String s : ufs) {
				ufsv.add(s);
			}

			if (tiposv.contains(tipo) && ufsv.contains(uf)) {
				List<ConfiguracaoServico> servicos = servico.getServico();
				for (ConfiguracaoServico conf : servicos) {
					for (String id_servico : urls.keySet()) {
						String nova_url = urls.get(id_servico);
						if (conf.getId().equals(id_servico)) {
							conf.setUrl(nova_url);
						}
					}
				}
			}
		}
	}

	public void ajustarServico(
			Hashtable<String, Hashtable<String, String>> urls, String tipo) {
		for (String uf : urls.keySet()) {
			this.ajustarServico(urls.get(uf), tipo, uf);
		}
	}

	public void serialize() {
		try {

			String xml = this.generator.toXMLString(this.sevicos);

			FileWriter fw = new FileWriter(this.nomeFile);
			fw.write(xml);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse() {
		List<NFeServico> nfeServicos = this.sevicos.getNFeServico();
		for (NFeServico servico : nfeServicos) {
			String[] ufs = servico.getUf();
			String[] tipos = servico.getTipo();
			List<ConfiguracaoServico> servicos = servico.getServico();

			for (String uf : ufs) {
				for (String tipo : tipos) {
					if (tipo.equals(SystemProperties.AMBIENT_PRODUCAO)) {
						for (ConfiguracaoServico conf : servicos) {
							adicionarData(this.urlServicosNormal, uf, conf
									.getId(), conf.getUrl());
							this.portNames.put(conf.getUrl(), conf
									.getNomePorta());
							this.namespaceNames.put(conf.getUrl(), conf
									.getNameSpace());
							this.nomeOperacao.put(conf.getId(), conf
									.getNomeOperacao());
							this.nomeServico.put(conf.getUrl(), conf
									.getNomeServico());
							// this.versionSOAP.put(conf.getUrl(), conf
							// .getVersionSOAP());
							this.classes.put(conf.getId(), conf.getClassName());
						}
					}

					if (tipo.equals(SystemProperties.AMBIENT_HOMOLOGACAO)) {
						for (ConfiguracaoServico conf : servicos) {
							adicionarData(this.urlServicosHomolgacao, uf, conf
									.getId(), conf.getUrl());

							this.portNames.put(conf.getUrl(), conf
									.getNomePorta());
							this.namespaceNames.put(conf.getUrl(), conf
									.getNameSpace());
							this.nomeOperacao.put(conf.getId(), conf
									.getNomeOperacao());
							this.nomeServico.put(conf.getUrl(), conf
									.getNomeServico());
							// this.versionSOAP.put(conf.getUrl(), conf
							// .getVersionSOAP());
							this.classes.put(conf.getId(), conf.getClassName());

						}

					}

					if (tipo.equals(SystemProperties.AMBIENT_SCAN)) {
						for (ConfiguracaoServico conf : servicos) {
							adicionarData(this.urlServicosScan, uf, conf
									.getId(), conf.getUrl());
							this.portNames.put(conf.getUrl(), conf
									.getNomePorta());
							this.namespaceNames.put(conf.getUrl(), conf
									.getNameSpace());
							this.nomeOperacao.put(conf.getId(), conf
									.getNomeOperacao());
							this.nomeServico.put(conf.getUrl(), conf
									.getNomeServico());
							// this.versionSOAP.put(conf.getUrl(), conf
							// .getVersionSOAP());
							this.classes.put(conf.getId(), conf.getClassName());

						}
					}

					if (tipo.equals(SystemProperties.AMBIENT_DPEC)) {
						for (ConfiguracaoServico conf : servicos) {
							adicionarData(this.urlServicosDPEC, uf, conf
									.getId(), conf.getUrl());
							this.portNames.put(conf.getUrl(), conf
									.getNomePorta());
							this.namespaceNames.put(conf.getUrl(), conf
									.getNameSpace());
							this.nomeOperacao.put(conf.getId(), conf
									.getNomeOperacao());
							this.nomeServico.put(conf.getUrl(), conf
									.getNomeServico());
							// this.versionSOAP.put(conf.getUrl(), conf
							// .getVersionSOAP());
							this.classes.put(conf.getId(), conf.getClassName());

						}
					}
				}

				if (!this.ufs.contains(uf)) {
					this.ufs.add(uf);
				}
			}
		}

	}

	private void adicionarData(Hashtable<String, Hashtable<String, String>> t,
			String uf, String idServico, String data) {
		Hashtable<String, String> ufs = t.get(uf);
		if (ufs == null) {
			ufs = new Hashtable<String, String>();
		}
		ufs.put(idServico, data);
		t.put(uf, ufs);
	}

	public Hashtable<String, Hashtable<String, String>> getUrlServicosNormal() {
		return urlServicosNormal;
	}

	public Hashtable<String, Hashtable<String, String>> getUrlServicosScan() {
		return urlServicosScan;
	}

	public Hashtable<String, Hashtable<String, String>> getUrlServicosHomolgacao() {
		return urlServicosHomolgacao;
	}

	public Hashtable<String, String> getPortNames() {
		return portNames;
	}

	public Hashtable<String, String> getNamespaceNames() {
		return namespaceNames;
	}

	public Hashtable<String, String> getNomeOperacao() {
		return nomeOperacao;
	}

	public Hashtable<String, String> getVersionSOAP() {
		return versionSOAP;
	}

	public Hashtable<String, String> getNomeServico() {
		return nomeServico;
	}

	public Vector<String> getUfs() {
		return ufs;
	}

	public void setUfs(Vector<String> ufs) {
		this.ufs = ufs;
	}

	public static void main(String[] args) {
		XMLConfigSender cfg = new XMLConfigSender(
				"/home/cnoriega/SkynetNFe/conf/servicos.xml");
		cfg.parse();
		
		
		
		System.out.println(cfg.getUfs().toString());
		System.out.println(cfg.getVersionSOAP());

		/*
		 * Hashtable<String, Hashtable<String, String>> urls = cfg
		 * .getUrlServicosNormal();
		 * urls.get("51").put(PropriedadesSistema.ID_SERVICO_RECEPCAO,
		 * "jejeje");
		 * urls.get("31").put(PropriedadesSistema.ID_SERVICO_RECEPCAO,
		 * "jejeje");
		 * 
		 * cfg.ajustarServico(urls, PropriedadesSistema.ID_PRODUCAO);
		 * cfg.serialize();
		 */
	}

	public Hashtable<String, String> getClasses() {
		return classes;
	}

	public Hashtable<String, Hashtable<String, String>> getUrlServicosDPEC() {
		return urlServicosDPEC;
	}

}
