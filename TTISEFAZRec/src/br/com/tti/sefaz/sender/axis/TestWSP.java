package br.com.tti.sefaz.sender.axis;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.jws.soap.SOAPBinding.Use;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.soap.SOAPConstants;
import org.xml.sax.SAXException;


import br.com.taragona.nfe.danfe.ImprimirDANFEImpl;

public class TestWSP {

	public static SOAPEnvelope createSOAP11(String header, String data)
			throws SAXException {

		String request = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>"
				+ header
				+ data
				+ "</soap:Body></soap:Envelope>";
		System.out.println(request);
		InputStream inputstream = new ByteArrayInputStream(request.getBytes());

		SOAPEnvelope env = new SOAPEnvelope(inputstream);

		return env;
	}

	public static void main(String[] args) throws Exception {

		Service service = new Service();
		Call call = (Call) service.createCall();

		URL endpoint = new URL(
				"https://servicos-homolog.cbdnet.com.br/nfeService/nfePort?wsdl");

		call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		call.setTargetEndpointAddress(endpoint);
		call.setOperationUse("literal");
		call.setOperationStyle("document");

		call.setOperationName(new QName("http://servicos.gpanet.com.br/nfe",
				"recepcaoOperation"));
		call.setPortName(new QName("http://servicos.gpanet.com.br/nfe",
				"nfePort"));
		

		String nfeCabecMsg = "<cabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.02\"><versaoDados>2.00</versaoDados></cabecMsg>";
		String nfeDadosMsg = ImprimirDANFEImpl
				.lerArquivo("C:\\NFe35110150630466000149550000000000101000000019_prot.xml");
		// nfeDadosMsg = "";

		call.invoke(createSOAP11(nfeCabecMsg, nfeDadosMsg));

	}
}
