package br.com.tti.sefaz.sender.dispatch;

import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;

import br.com.taragona.nfe.util.PropriedadesMain;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.remote.ServicesConfig;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.com.tti.sefaz.sender.xml.XMLConfigSender;
import br.com.tti.sefaz.systemconfig.SystemConfigInterface;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.MainParameters;

public class SenderDispatchController extends SenderGenericController {

	private DispatchFactory factory;

	private XMLConfigSender config;

	private InvokerDispatch invoker;

	// private SenderConfig conf;

	private ServicesConfig servicesConfig;

	public SenderDispatchController(SystemConfigInterface configurator) {
		super(configurator);

		try {
			// this.conf = configurator.getSenderConfig();
			this.servicesConfig = configurator.getServiceConfig();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// this.config = new XMLConfigSender(conf.getServicesFile());
		// this.config.parse();

		this.factory = new DispatchFactory(this.servicesConfig);
		this.invoker = new InvokerDispatch();
	}

	public SenderDispatchController(SenderConfig conf,
			ServicesConfig servicesConf) {
		super(conf);

		this.servicesConfig = servicesConf;

		// this.config = new XMLConfigSender(conf.getServicesFile());
		// this.config.parse();

		this.factory = new DispatchFactory(this.servicesConfig);
		this.invoker = new InvokerDispatch();
	}

	// @Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {
		String ambient = (String) prop.get("AMBIENT");
		String uf = (String) prop.get("UF");

		MyLogger.getLog().info("uf:" + uf);
		MyLogger.getLog().info("amb:" + ambient);

		Dispatch<SOAPMessage> dispatch = this.factory.createDispath(uf,
				idServico, ambient);

		MyLogger.getLog().info("header:" + header);
		MyLogger.getLog().info("data:" + data);

		String response = this.invoker.invoke(dispatch, header, data);

		MyLogger.getLog().info("response:" + response);
		return response;
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);

		ajustarPropriedadesFwd(
				"/home/cnoriega/SkynetNFe/certificados/myroot.jks",
				"/home/cnoriega/SkynetNFe/certificados/yoki.pfx", "crhisn",
				"yoki000");
		// XMLConfigSystem xc = new XMLConfigSystem(
		// "E:\\TTICTe\\conf\\configuracao_cte.xml");

		XMLConfigSystem xc = new XMLConfigSystem(
				"C:/TTIRec/conf/mysqlconfiguracao_hib.xml");
		xc.make();
		xc.getServiceConfig();

		// SystemConfigInterface conf = new
		SenderDispatchController s = new SenderDispatchController(xc
				.getSenderConfig(), xc.getServiceConfig());

		String cabecalho = "<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">"
				+ "<cUF>35</cUF>"
				+ "<versaoDados>1.00</versaoDados>"
				+ "</nfeCabecMsg>";

		String dados = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">" +
				"<envEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">"
				+ "<tpAmb>2</tpAmb>"
				+ "<xServ>STATUS</xServ></envEvento>"
				+ "</nfeDadosMsg>";

		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", SystemProperties.AMBIENT_PRODUCAO);
		prop.put("UF", "35");

		try {
			System.out.println(s.sendXMLMessage(
					SystemProperties.ID_SERVICO_RECEPCAO_EVENTO, cabecalho, dados,
					prop));
		} catch (RemoteException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void ajustarPropriedadesFwd(String fileTrust, String fileKey,
			String passwordTrust, String passwordKey) {
		System.setProperty("javax.net.ssl.keyStore", fileKey);
		System.setProperty("javax.net.ssl.trustStore", fileTrust);
		System.setProperty("javax.net.ssl.keyStorePassword", passwordKey);
		System.setProperty("javax.net.ssl.trustStorePassword", passwordTrust);
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
	}

}
