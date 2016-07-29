package br.com.tti.sefaz.systemconfig;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.remote.CNPJSerialize;
import br.com.tti.sefaz.remote.CallBackConfig;
import br.com.tti.sefaz.remote.CertificatesConfig;
import br.com.tti.sefaz.remote.ConnectorConfig;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.remote.MessengerConfig;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.remote.ServicesConfig;
import br.com.tti.sefaz.sender.xml.XMLConfigSender;
import br.com.tti.sefaz.systemconfig.schema.ArquivoConfiguracao;
import br.com.tti.sefaz.systemconfig.schema.Cnpjinfo;
import br.com.tti.sefaz.systemconfig.schema.Cnpjinfo.TIPOEMP;
import br.com.tti.sefaz.systemconfig.schema.Cnpjs;
import br.com.tti.sefaz.systemconfig.schema.Entrada;
import br.com.tti.sefaz.systemconfig.schema.ListaVersoes;
import br.com.tti.sefaz.xml.XMLGenerator;

public class XMLConfigSystem {

	private SenderConfig senderConfig;
	private DBConfig dbConfig;
	private ConnectorConfig connector;
	private ServicesConfig serviceConfig;
	private Hashtable<String, MessengerConfig> msnConfigs;
	private Hashtable<String, CallBackConfig> callsConfigs;
	private Hashtable<String, CNPJSerialize> cnpjsSer;
	private Vector<CertificatesConfig> certificates;
	private Hashtable<String, CNPJData> cnpjsData;
	private Hashtable<String, CNPJData> cnpjsNotaServico;
	private Hashtable<Vector<String>, Vector<SchemaVersionConfig>> schemasVersion;
	private Hashtable<TIPOEMP, Hashtable<String, CNPJData>> tipoCNPJ;

	private XMLGenerator xmls;

	private XMLConfigSender xmlSenderConfig;

	private String xmlFile;
	private ArquivoConfiguracao fileConfig;

	@SuppressWarnings("unchecked")
	public XMLConfigSystem(String xmlFile) {
		this.xmlFile = xmlFile;
		this.xmls = new XMLGenerator("br.com.tti.sefaz.systemconfig.schema");

		File f = new File(this.xmlFile);

		try {
			JAXBElement<ArquivoConfiguracao> c = (JAXBElement<ArquivoConfiguracao>) xmls
					.toObjectFromFile(f);
			this.fileConfig = c.getValue();
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

	}

	private void makeServicesConfig() {
		this.xmlSenderConfig = new XMLConfigSender(this.fileConfig
				.getConfiguracaoSistema().getConf().getArquivoServicos());
		this.xmlSenderConfig.parse();

		this.serviceConfig = new ServicesConfig();

		this.serviceConfig.setUrlServicosDpec(this.xmlSenderConfig
				.getUrlServicosDPEC());
		this.serviceConfig.setUrlServicosHomolgacao(this.xmlSenderConfig
				.getUrlServicosHomolgacao());
		this.serviceConfig.setUrlServicosNormal(this.xmlSenderConfig
				.getUrlServicosNormal());
		this.serviceConfig.setUrlServicosScan(this.xmlSenderConfig
				.getUrlServicosScan());

		this.serviceConfig.setClasses(this.xmlSenderConfig.getClasses());
		this.serviceConfig.setNamespaceNames(this.xmlSenderConfig
				.getNamespaceNames());
		this.serviceConfig.setNomeOperacao(this.xmlSenderConfig
				.getNomeOperacao());
		this.serviceConfig
				.setNomeServico(this.xmlSenderConfig.getNomeServico());
		this.serviceConfig.setPortNames(this.xmlSenderConfig.getPortNames());
		this.serviceConfig
				.setVersionSOAP(this.xmlSenderConfig.getVersionSOAP());
	}

	private void makeDBConfig() {
		this.dbConfig = new DBConfig();
		this.dbConfig.setDriver(this.fileConfig.getConfiguracaoSistema()
				.getConf().getDriver());
		this.dbConfig.setPassword(this.fileConfig.getConfiguracaoSistema()
				.getConf().getPassword());
		this.dbConfig.setUrl(this.fileConfig.getConfiguracaoSistema().getConf()
				.getUrlBancoDados());
		this.dbConfig.setUser(this.fileConfig.getConfiguracaoSistema()
				.getConf().getUsuario());
	}

	private void makeConnector() {
		this.connector = new ConnectorConfig();
		this.connector.setConnectorClass(this.fileConfig
				.getConfiguracaoSistema().getConf().getConnectorClass());
	}

	public void make() {
		this.makeDBConfig();
		this.processCNPJs();
		this.processSchemas();
		this.processSenderConfig();
		this.makeServicesConfig();
		this.makeConnector();
	}

	public void processCNPJs() {
		this.certificates = new Vector<CertificatesConfig>();
		this.msnConfigs = new Hashtable<String, MessengerConfig>();
		this.cnpjsData = new Hashtable<String, CNPJData>();
		this.cnpjsNotaServico = new Hashtable<String, CNPJData>();
		this.cnpjsSer = new Hashtable<String, CNPJSerialize>();
		this.callsConfigs = new Hashtable<String, CallBackConfig>();
		this.tipoCNPJ = new Hashtable<TIPOEMP, Hashtable<String, CNPJData>>();

		List<Cnpjs> cnpjsCad = this.fileConfig.getCnpjsCadastrados()
				.getCadastro();
		for (Cnpjs cnpjCad : cnpjsCad) {
			List<Cnpjinfo> cnpjs = cnpjCad.getCnpjInfo();
			Vector<String> cnpjss = new Vector<String>();
			CertificatesConfig certConfig = new CertificatesConfig();

			for (Cnpjinfo cnpjinfo : cnpjs) {
				CNPJData data = new CNPJData();
				MessengerConfig msnConfig = new MessengerConfig();
				CallBackConfig callConfig = new CallBackConfig();
				CNPJSerialize dataSer = new CNPJSerialize();

				cnpjss.add(cnpjinfo.getCnpj());
				certConfig.setPfxFile(cnpjCad.getPfx());

				callConfig.setCnpj(cnpjinfo.getCnpj());
				callConfig.setUf(cnpjCad.getUF());
				callConfig.setTimeProc(Long.parseLong(cnpjCad.getTempoProc()));

				msnConfig.setCnpj(cnpjinfo.getCnpj());
				msnConfig
						.setSizeFile(Integer.parseInt(cnpjCad.getTamanhoLote()));
				msnConfig.setTimeout(Long.parseLong(cnpjCad.getTimeout()));
				msnConfig.setUf(cnpjCad.getUF());
				msnConfig.setSizeSet(Integer.parseInt(cnpjCad.getNNotas()));

				data.setCnpj(cnpjinfo.getCnpj());
				data.setUf(cnpjCad.getUF());
				data.setXName(cnpjinfo.getNomeFantasia());
				data.setTipo(cnpjinfo.getTipoemp());
				data.setMunicipio(cnpjCad.getMunicipio());
				data.setLogin(cnpjinfo.getLogin());
				data.setSenha(cnpjinfo.getSenha());
				data.setIm(cnpjinfo.getIm());

				if (cnpjCad.getMunicipio() != null
						&& !cnpjCad.getMunicipio().isEmpty()) {
					this.cnpjsNotaServico.put(cnpjinfo.getCnpj(), data);
				}

				dataSer.setDirectoryworks(cnpjCad.getPastaSaida());
				dataSer.setEmails(cnpjCad.getEmails());

				this.msnConfigs.put(cnpjinfo.getCnpj(), msnConfig);
				this.cnpjsData.put(cnpjinfo.getCnpj(), data);
				this.callsConfigs.put(cnpjinfo.getCnpj(), callConfig);
				this.cnpjsSer.put(cnpjinfo.getCnpj(), dataSer);

				if (cnpjinfo.getTipoemp() != null) {
					Hashtable<String, CNPJData> tablecnpj = this.tipoCNPJ
							.get(cnpjinfo.getTipoemp());
					if (tablecnpj == null) {
						tablecnpj = new Hashtable<String, CNPJData>();
						this.tipoCNPJ.put(cnpjinfo.getTipoemp(), tablecnpj);
					}
					tablecnpj.put(data.getCnpj(), data);
				}
			}

			certConfig.setCnpjs(cnpjss);
			this.certificates.add(certConfig);
		}

	}

	private void processSchemas() {
		this.schemasVersion = new Hashtable<Vector<String>, Vector<SchemaVersionConfig>>();

		List<ListaVersoes> schemasList = this.fileConfig.getVersoesEsquemas()
				.getVersao();
		for (ListaVersoes listaVersoes : schemasList) {
			List<Entrada> schemas = listaVersoes.getElementos();
			Vector<SchemaVersionConfig> list = new Vector<SchemaVersionConfig>();
			for (Entrada entrada : schemas) {
				SchemaVersionConfig sc = new SchemaVersionConfig();
				sc.setSchemaName(entrada.getNomeEsquema());
				sc.setValue(entrada.getVersaoEsquema());

				list.add(sc);
			}

			Vector<String> ufsv = new Vector<String>();
			String[] ufs = listaVersoes.getUfs();
			for (String uf : ufs) {
				ufsv.add(uf);
			}
			this.schemasVersion.put(ufsv, list);

		}

	}

	private void processSenderConfig() {
		this.senderConfig = new SenderConfig();

		this.senderConfig.setPfxTransmission(this.fileConfig
				.getConfiguracaoSistema().getConf().getPfxDeTransmissao());
		this.senderConfig.setTrustStoreFile(this.fileConfig
				.getConfiguracaoSistema().getConf().getTrustStore());
		this.senderConfig.setServicesFile(this.fileConfig
				.getConfiguracaoSistema().getConf().getArquivoServicos());

	}

	public SenderConfig getSenderConfig() {
		return senderConfig;
	}

	public DBConfig getDbConfig() {
		return dbConfig;
	}

	public ServicesConfig getServiceConfig() {
		return serviceConfig;
	}

	public Hashtable<String, MessengerConfig> getMsnConfigs() {
		return msnConfigs;
	}

	public Vector<CertificatesConfig> getCertificates() {
		return certificates;
	}

	public Hashtable<String, CNPJData> getCnpjsData() {
		return cnpjsData;
	}

	public Hashtable<String, CNPJData> getCnpjsNotaServico() {
		return cnpjsNotaServico;
	}

	public Hashtable<Vector<String>, Vector<SchemaVersionConfig>> getSchemasVersion() {
		return schemasVersion;
	}

	public Hashtable<String, CallBackConfig> getCallsConfigs() {
		return callsConfigs;
	}

	public Hashtable<String, CNPJSerialize> getCnpjsSer() {
		return cnpjsSer;
	}

	public ConnectorConfig getConnector() {
		return connector;
	}

	/*
	 * public static void main(String[] args) { XMLGenerator xml = new
	 * XMLGenerator( "br.com.tti.sefaz.systemconfig.schema"); File f = new File(
	 * "/home/cnoriega/SkynetNFe/conf/configuracao_linux.xml"); try {
	 * JAXBElement<ArquivoConfiguracao> c = (JAXBElement<ArquivoConfiguracao>)
	 * xml .toObjectFromFile(f);
	 * System.out.println(c.getValue().getConfiguracaoSistema().getConf()); }
	 * catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	public Hashtable<TIPOEMP, Hashtable<String, CNPJData>> getTipoCNPJ() {
		return tipoCNPJ;
	}

	public void setTipoCNPJ(
			Hashtable<TIPOEMP, Hashtable<String, CNPJData>> tipoCNPJ) {
		this.tipoCNPJ = tipoCNPJ;
	}

	public static void main(String[] args) {
		XMLConfigSystem conf = new XMLConfigSystem(
				"C:\\TTIRec\\conf\\configuracao.xml");
		conf.make();
		String pfx = conf.getSenderConfig().getPfxTransmission();
		System.out.println(pfx);
		Hashtable<String, CNPJData> cnpjdata = conf.getCnpjsNotaServico();
		for (CNPJData data : cnpjdata.values()) {
			System.out.println(data.getMunicipio());
		}

	}
}
