package br.com.tti.sefaz.sender.port;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.remote.ServicesConfig;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.com.tti.sefaz.sender.ssl.ConfigureSSLProperties;
import br.com.tti.sefaz.systemconfig.SystemConfigInterface;

public class SenderPortController extends SenderGenericController {

	private PortFactory factory;
	private Invoker invoker;
	private ServicesConfig config;

	public SenderPortController(SenderConfig conf, ServicesConfig servicesConf) {
		super(conf);

		this.config = servicesConf;

		Hashtable<String, String> class_s = this.config.getClasses();
		Hashtable<String, Class> classs = new Hashtable<String, Class>();
		for (String t : class_s.keySet()) {
			Class c = null;
			try {
				c = Class.forName(class_s.get(t));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			classs.put(t, c);
		}

		this.factory = new PortFactory();
		this.factory.setNamespaceNames(this.config.getNamespaceNames());
		this.factory.setPortNames(this.config.getPortNames());
		this.factory.setUrlServicosHomolgacao(this.config
				.getUrlServicosHomolgacao());
		this.factory.setUrlServicosNormal(this.config.getUrlServicosNormal());
		this.factory.setUrlServicosScan(this.config.getUrlServicosScan());
		this.factory.setUrlServicosDpec(config.getUrlServicosDpec());
		this.factory.setClassServices(classs);
		this.factory.setNomeServico(this.config.getNomeServico());

		this.invoker = new Invoker();
		this.invoker.setOperationNames(this.config.getNomeOperacao());
		this.invoker.setClassServices(classs);
	}

	public SenderPortController(SystemConfigInterface configurator) {
		super(configurator);
		this.factory = new PortFactory();

		try {
			this.config = configurator.getServiceConfig();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Hashtable<String, String> class_s = this.config.getClasses();
		Hashtable<String, Class> classs = new Hashtable<String, Class>();
		for (String t : class_s.keySet()) {
			Class c = null;
			try {
				c = Class.forName(class_s.get(t));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			classs.put(t, c);
		}

		this.factory.setNamespaceNames(this.config.getNamespaceNames());
		this.factory.setPortNames(this.config.getPortNames());
		this.factory.setUrlServicosHomolgacao(this.config
				.getUrlServicosHomolgacao());
		this.factory.setUrlServicosNormal(this.config.getUrlServicosNormal());
		this.factory.setUrlServicosScan(this.config.getUrlServicosScan());
		this.factory.setUrlServicosDpec(config.getUrlServicosDpec());
		this.factory.setClassServices(classs);
		this.factory.setNomeServico(this.config.getNomeServico());

		this.invoker = new Invoker();
		this.invoker.setOperationNames(this.config.getNomeOperacao());
		this.invoker.setClassServices(classs);
	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {

		String uf = (String) prop.get("UF");
		String ambient = (String) prop.get("AMBIENT");

		MyLogger.getLog().finest(data);
		MyLogger.getLog().finest(header);

		/*ConfigureSSLProperties.setSSLCertificates(
				"E:\\TTIRec\\certificados\\myroot.jks",
				"E:\\TTIRec\\certificados\\comm.pfx", "crhisn", "itambe");*/

		Object port = this.factory.criarPort(idServico, uf, ambient);
		String result = this.invoker.invokePort(idServico, port, header, data);

		MyLogger.getLog().finest(result);
		return result;
	}
}
