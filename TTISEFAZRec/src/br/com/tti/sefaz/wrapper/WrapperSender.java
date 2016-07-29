package br.com.tti.sefaz.wrapper;

import java.io.FileWriter;
import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.taragona.nfe.gerenciador.GerenciadorInterface;
import br.com.taragona.nfe.sender.Sender;
import br.com.taragona.nfe.util.PropriedadesMain;
import br.com.taragona.nfe.util.PropriedadesSistema;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperCancel;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperEvent;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperInut;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperReturnCallBack;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperReturnSend;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLMessageFactoryNFe;

public class WrapperSender extends Sender {

	private SenderDispatchController dispatcher;

	private XMLMessageFactoryNFe xmlFactory;

	public WrapperSender(GerenciadorInterface gerenciador) throws Exception {
		super(gerenciador);
		XMLConfigSystem xc = new XMLConfigSystem(PropriedadesMain.getXml());
		xc.make();
		xc.getServiceConfig();

		this.dispatcher = new SenderDispatchController(xc.getSenderConfig(),
				xc.getServiceConfig());

		this.xmlFactory = new XMLMessageFactoryNFe(xc.getSchemasVersion());
	}

	@Override
	public String enviarMensagemXML(String idServico, String cnpj,
			String header, String xml, Hashtable prop) throws RemoteException {
		String uf = (String) prop.get("UF");
		String ambient = (String) prop.get("TIPO");
		prop.put("AMBIENT", ambient);

		if (prop.containsKey("UFREAL")) {
			uf = (String) prop.get("UFREAL");
		}

		String xmlSoap = null;
		String resultXml = null;

		header = header
				.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
						"");
		xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		xml = xml.replace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
				"");

		if (idServico.equals(PropriedadesSistema.ID_SERVICO_RECEPCAO)) {

			xmlSoap = this.xmlFactory.createSendMessageXML(xml, uf, ambient);
			header = this.xmlFactory.createHeader(uf, ambient,
					SystemProperties.ID_SERVICO_RECEPCAO);

			MyLogger.getLog().finest("header: " + header);
			MyLogger.getLog().finest("xml: " + xmlSoap);

			resultXml = this.dispatcher
					.sendXMLMessage(SystemProperties.ID_SERVICO_RECEPCAO,
							header, xmlSoap, prop);

			XMLWrapperReturnSend recibe = null;
			try {
				recibe = XMLWrapperFactory.createReturnSendWrapper(resultXml);
				return recibe.getXml();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (idServico.equals(PropriedadesSistema.ID_SERVICO_RETRECEPCAO)) {
			xmlSoap = this.xmlFactory
					.createCallbackMessageXML(xml, uf, ambient);
			header = this.xmlFactory.createHeader(uf, ambient,
					SystemProperties.ID_SERVICO_RETRECEPCAO);

			resultXml = this.dispatcher.sendXMLMessage(
					SystemProperties.ID_SERVICO_RETRECEPCAO, header, xmlSoap,
					prop);

			XMLWrapperReturnCallBack recibe = null;
			try {
				recibe = XMLWrapperFactory
						.createReturnCallbackWrapper(resultXml);
				return recibe.getXml();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (idServico.equals(PropriedadesSistema.ID_SERVICO_CONSULTA)) {
			xmlSoap = this.xmlFactory
					.createQueryXmlMessageXML(xml, uf, ambient);
			header = this.xmlFactory.createHeader(uf, ambient,
					SystemProperties.ID_SERVICO_CONSULTA);
			resultXml = this.dispatcher
					.sendXMLMessage(SystemProperties.ID_SERVICO_CONSULTA,
							header, xmlSoap, prop);

			XMLWrapperQuery recibe = null;
			try {
				recibe = XMLWrapperFactory.createReturnQueryWrapper(resultXml);
				return recibe.getXml();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (idServico.equals(PropriedadesSistema.ID_SERVICO_CANCELAMENTO)) {
			xmlSoap = this.xmlFactory.createCancelMessageXML(xml, uf, ambient);
			header = this.xmlFactory.createHeader(uf, ambient,
					SystemProperties.ID_SERVICO_CANCELAMENTO);
			resultXml = this.dispatcher.sendXMLMessage(
					SystemProperties.ID_SERVICO_CANCELAMENTO, header, xmlSoap,
					prop);

			XMLWrapperCancel recibe = null;
			try {
				recibe = XMLWrapperFactory.createReturnCancelWrapper(resultXml);
				return recibe.getXml();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (idServico.equals(PropriedadesSistema.ID_SERVICO_INUTILIZACAO)) {
			xmlSoap = this.xmlFactory.createInutMessageXML(xml, ambient);
			header = this.xmlFactory.createHeader(uf, ambient,
					SystemProperties.ID_SERVICO_INUTILIZACAO);

			resultXml = this.dispatcher.sendXMLMessage(
					SystemProperties.ID_SERVICO_INUTILIZACAO, header, xmlSoap,
					prop);

			XMLWrapperInut recibe = null;
			try {
				recibe = XMLWrapperFactory.createReturnInutWrapper(resultXml);
				return recibe.getXml();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (idServico.equals(PropriedadesSistema.ID_SERVICO_RECEPCAO_EVENTO)) {
			xmlSoap = this.xmlFactory.createEventXML(xml, null, null, uf,
					ambient);

			header = this.xmlFactory.createHeader(uf, ambient,
					SystemProperties.ID_SERVICO_RECEPCAO_EVENTO);

			System.out.println(xmlSoap);

			try {
				FileWriter fw = new FileWriter("C:\\cceassinado_send.xml");
				fw.write(xmlSoap);
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			resultXml = this.dispatcher.sendXMLMessage(
					SystemProperties.ID_SERVICO_RECEPCAO_EVENTO, header,
					xmlSoap, prop);

			System.out.println(header);
			System.out.println(resultXml);

			XMLWrapperEvent recibe = null;
			try {
				recibe = XMLWrapperFactory.createEventWrapper(resultXml);
				return recibe.getXmlProtocol();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
