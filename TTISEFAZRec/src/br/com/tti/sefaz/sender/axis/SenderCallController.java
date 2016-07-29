package br.com.tti.sefaz.sender.axis;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.remote.ServicesConfig;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.MainParameters;

public class SenderCallController extends SenderGenericController {

	private CallFactory factory;
	private InvokerCall invoker;

	public SenderCallController(SenderConfig conf, ServicesConfig servicesConf) {
		super(conf);

		this.factory = new CallFactory(servicesConf);
		this.invoker = new InvokerCall();
	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {
		String ambient = (String) prop.get("AMBIENT");
		String uf = (String) prop.get("UF");
		
		if (!ambient.contains("v200")) {
			ambient = ambient + "v200";
		}

		MyLogger.getLog().finest("uf:" + uf);
		MyLogger.getLog().finest("amb:" + ambient);

		String url = this.factory.findURL(uf, idServico, ambient);
		// Call call = this.factory.createCaller(uf, idServico, ambient);

		MyLogger.getLog().finest("header:" + header);
		MyLogger.getLog().finest("data:" + data);

		String response = null;
		if (uf.equals("26"))
			response = this.invoker.invokeSOAP12(header, data, url);
		else
			response = this.invoker.invoke(header, data, url);

		response = response.replace("ns2:", "");

		MyLogger.getLog().finest("response:" + response);
		return response;
	}

	public String sendXMLMessageSOAP12(String idServico, String header,
			String data, Hashtable prop) throws RemoteException {
		String ambient = (String) prop.get("AMBIENT");
		String uf = (String) prop.get("UF");

		MyLogger.getLog().finest("uf:" + uf);
		MyLogger.getLog().finest("amb:" + ambient);

		String url = this.factory.findURL(uf, idServico, ambient);
		// Call call = this.factory.createCaller(uf, idServico, ambient);

		MyLogger.getLog().finest("header:" + header);
		MyLogger.getLog().finest("data:" + data);

		String response = this.invoker.invokeSOAP12(header, data, url);

		response = response.replace("ns2:", "");

		MyLogger.getLog().finest("response:" + response);
		return response;
	}

	public static void ajustarPropriedadesFwd(String fileTrust, String fileKey,
			String passwordTrust, String passwordKey) {
		System.setProperty("javax.net.ssl.keyStore", fileKey);
		System.setProperty("javax.net.ssl.trustStore", fileTrust);
		System.setProperty("javax.net.ssl.keyStorePassword", passwordKey);
		System.setProperty("javax.net.ssl.trustStorePassword", passwordTrust);
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);
		XMLConfigSystem xc = new XMLConfigSystem(
				"C:\\TTINFe\\conf\\configuracao.xml");
		xc.make();
		xc.getServiceConfig();
		SenderCallController s = new SenderCallController(xc.getSenderConfig(),
				xc.getServiceConfig());

		String xmlString = "<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\">"
				+ "<EnvioLote/>" + "</nfeDadosMsg>";

		String header = "\n<nfeCabecMsg xml:space=\"preserve\" xmlns ="
				+ "\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\""
				+ ">" + "<cUF>" + "26" + "</cUF>" + "<versaoDados>" + "2.00"
				+ "</versaoDados>" + "</nfeCabecMsg>";

		ajustarPropriedadesFwd("C:\\TTINFe\\certificados\\myroot.jks",
				"C:\\comm1.pfx", "crhisn", "itambe");

		Hashtable<String, String> prop = new Hashtable<String, String>();
		prop.put("AMBIENT", SystemProperties.AMBIENT_PRODUCAO);
		prop.put("UF", "26");
		try {
			System.out.println(s.sendXMLMessage(
					SystemProperties.ID_SERVICO_RECEPCAO, header, xmlString,
					prop));
			/*System.out.println(s.sendXMLMessage(
					SystemProperties.ID_SERVICO_RECEPCAO, header, xmlString,
					prop));*/
		} catch (RemoteException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * public static void main2(String[] args) {
	 * ajustarPropriedadesFwd("C:\\TTIRec\\certificados\\myroot.jks",
	 * "C:\\comm2.pfx", "crhisn", "itambe"); try { String endpoint =
	 * "https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeRecepcao2";
	 * 
	 * Service service = new Service(); Call call = (Call) service.createCall();
	 * 
	 * call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
	 * call.setTargetEndpointAddress(new java.net.URL(endpoint));
	 * 
	 * call.setOperationName(new QName(
	 * "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2",
	 * "nfeRecepcaoLote2")); call.setPortName(new QName(
	 * "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2",
	 * "NfeRecepcao2Soap12"));
	 * 
	 * String xmlString = "\n<nfeDadosMsg xml:space=\"preserve\"
	 * xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\">" + "" + "</nfeDadosMsg>\n";
	 * 
	 * String header = "<nfeCabecMsg xmlns =" +
	 * "\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2\"" + ">" + "<cUF>" +
	 * "31" + "</cUF>" + "<versaoDados>" + "2.00" + "</versaoDados>" + "</nfeCabecMsg>";
	 * 
	 * InputStream inputstream = new ByteArrayInputStream(
	 * createSOAPMessageString(header, xmlString, "").getBytes());
	 * 
	 * SOAPEnvelope envelope = null; call.invoke(envelope);
	 * 
	 * SOAPEnvelope env = new SOAPEnvelope(inputstream); SOAPEnvelope ret =
	 * call.invoke(env); System.out.println("Sent 'Hello!', got '" + ret + "'"); //
	 * call.invoke(createSOAPMessage("", "", "")); // String ret = (String)
	 * call.invoke(new Object[] { "Hello!" }); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	public static String createSOAPMessageString(String header, String data,
			String version) {
		String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
				+ "<soap12:Header>" + header + "</soap12:Header><soap12:Body>"
				+ data + "</soap12:Body></soap12:Envelope>";

		return request;
	}
}
