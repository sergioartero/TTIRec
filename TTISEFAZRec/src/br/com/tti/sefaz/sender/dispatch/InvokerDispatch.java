package br.com.tti.sefaz.sender.dispatch;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;

import br.com.tti.sefaz.exceptions.MySenderException;

public class InvokerDispatch {

	private MessageFactory factory;

	public InvokerDispatch() {
		try {
			factory = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		} catch (SOAPException e) {
			e.printStackTrace();
		}
	}

	public SOAPMessage createSOAPMessage(String header, String data,
			String version) {
		String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
				+ "<soap12:Header>" + header + "</soap12:Header><soap12:Body>"
				+ data + "</soap12:Body></soap12:Envelope>";

		try {
			SOAPMessage message = this.factory.createMessage();
			message.getSOAPPart().setContent(
					(Source) new StreamSource(new StringReader(request)));
			message.saveChanges();
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getSOAPMessageAsString(SOAPMessage msg) {
		ByteArrayOutputStream baos = null;
		String s = null;

		try {
			baos = new ByteArrayOutputStream();
			msg.writeTo(baos);
			s = baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public String invoke(Dispatch<SOAPMessage> dispatch, String header,
			String data) throws MySenderException {
		SOAPMessage soap = createSOAPMessage(header, data, null);
		SOAPMessage response = null;
		try {
			response = dispatch.invoke(soap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MySenderException(e, MySenderException.ERROR_CALL_SERVICE);
		}
		return getSOAPMessageAsString(response);
	}

}
