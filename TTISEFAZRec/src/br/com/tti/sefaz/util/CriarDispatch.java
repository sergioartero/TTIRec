package br.com.tti.sefaz.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

public class CriarDispatch {

	private MessageFactory factory;

	public CriarDispatch() {
		try {
			this.factory = MessageFactory
					.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void criarServico() {

		// cria web service
		QName serviceName = new QName("https://www.nfp.sp.gov.br/ws",
				"ArquivoNF_Mod1");
		Service service = Service.create(serviceName);

		// cria porta
		QName portName = new QName("https://www.nfp.sp.gov.br/ws",
				"ArquivoNF_Mod1Soap12");
		String bindingId = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING;
		String endpointAddress = "https://www.nfp.fazenda.sp.gov.br/ws/arquivonf_mod1.asmx?wsdl";

		// associa a porta no servico
		service.addPort(portName, bindingId, endpointAddress);

		// cria dispatch
		Dispatch<SOAPMessage> dispSOAP = service.createDispatch(portName,
				SOAPMessage.class, Service.Mode.MESSAGE);

		String arquivo = null;
		String header = "<Autenticacao Usuario=\"rafael\" Senha=\"123\" CNPJ=\"1234567890\" CategoriaUsuario=\"3\" xmlns=\"https://www.nfp.sp.gov.br/ws\" />";
		String nomearquivo = null;
		String conteudo = null;
		String obs = null;
		String data = "<Enviar xmlns=\"https://www.nfp.sp.gov.br/ws\"><NomeArquivo>"
				+ nomearquivo
				+ "</NomeArquivo><ConteudoArquivo>"
				+ conteudo
				+ "</ConteudoArquivo><Observacoes>"
				+ obs
				+ "</Observacoes></Enviar>";

		// criar mensagem
		SOAPMessage soap = createSOAPMessage(header, data);
		SOAPMessage resultado = dispSOAP.invoke(soap);
		System.out.println(this.getSOAPMessageAsString(resultado));

	}

	public SOAPMessage createSOAPMessage(String header, String data) {
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

	public static void main(String[] args) {
		CriarDispatch d = new CriarDispatch();
		d.criarServico();
	}
}
