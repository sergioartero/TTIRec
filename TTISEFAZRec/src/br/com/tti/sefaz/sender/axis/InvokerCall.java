package br.com.tti.sefaz.sender.axis;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.soap.SOAPConstants;
import org.xml.sax.SAXException;

import br.com.taragona.nfe.util.PropriedadesMain;
import br.com.tti.sefaz.exceptions.MySenderException;

public class InvokerCall {

	private String encoding;

	public InvokerCall() {
		if (PropriedadesMain.isUtf()) {
			encoding = "UTF-8";
		} else {
			encoding = "ISO-8859-1";
		}
	}

	public SOAPEnvelope createSOAP11(String header, String data, String version)
			throws SAXException {

		String request = "<?xml version=\"1.0\" encoding=\""
				+ this.encoding
				+ "\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Header>" + header + "</soap:Header><soap:Body>" + data
				+ "</soap:Body></soap:Envelope>";
		InputStream inputstream = new ByteArrayInputStream(request.getBytes());

		SOAPEnvelope env = new SOAPEnvelope(inputstream);

		return env;
	}

	public SOAPEnvelope createSOAPMessageString(String header, String data,
			String version) throws SAXException {
		String request = "<?xml version=\"1.0\" encoding=\""
				+ this.encoding
				+ "\"?>\n"
				+ "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
				+ "<soap12:Header xml:space=\"preserve\">\n" + header
				+ "\n</soap12:Header><soap12:Body>\n" + data
				+ "\n</soap12:Body></soap12:Envelope>";

		InputStream inputstream = new ByteArrayInputStream(request.getBytes());

		SOAPEnvelope env = new SOAPEnvelope(inputstream);

		return env;
	}

	// private Call call2 = null;

	public String invoke(String header, String data, String url)
			throws MySenderException {

		try {

			url = url.replace("?wsdl", "");

			Service service = new Service();

			Call call2 = (Call) service.createCall();
			// call2.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);

			/*
			 * call2.setOperationName(new QName(
			 * "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2",
			 * "nfeRecepcaoLote2"));
			 */
			/*
			 * call2.setPortName(new QName(
			 * "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2",
			 * "NfeRecepcao2Soap"));
			 */

			call2.setTargetEndpointAddress(new java.net.URL(url));

			SOAPEnvelope env = this.createSOAP11(header, data, null);
			System.out.println("SOAP1.1 Parsered:" + env.toString());
			SOAPEnvelope response = call2.invoke(env);
			return response.toString();
		} catch (Exception e) {
			// this.call2 = null;
			e.printStackTrace();
			throw new MySenderException(e, MySenderException.ERROR_CALL_SERVICE);
		}

	}

	public String invokeSOAP12(String header, String data, String url)
			throws MySenderException {

		try {

			url = url.replace("?wsdl", "");

			Service service = new Service();

			Call call2 = (Call) service.createCall();
			call2.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);

			call2.setOperationName(new QName(
					"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2",
					"nfeRecepcaoLote2"));

			call2.setPortName(new QName(
					"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2",
					"NfeRecepcao2Soap12"));

			call2.setTargetEndpointAddress(new java.net.URL(url));

			SOAPEnvelope env = this.createSOAPMessageString(header, data, null);
			// System.out.println("SOAP1.2 Parsered:" + env.toString());
			SOAPEnvelope response = call2.invoke(env);
			return response.toString();
		} catch (Exception e) {
			// this.call2 = null;
			e.printStackTrace();
			throw new MySenderException(e, MySenderException.ERROR_CALL_SERVICE);
		}

	}

	public String invoke(Call call, String header, String data)
			throws MySenderException {
		try {
			SOAPEnvelope soap = createSOAPMessageString(header, data, null);
			System.out.println("SOAP Parsered:" + soap.toString());
			SOAPEnvelope response = call.invoke(soap);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MySenderException(e, MySenderException.ERROR_CALL_SERVICE);
		}
	}
}
