package br.com.tti.sefaz.tools;

import java.util.Hashtable;
import java.util.Vector;

import javax.xml.bind.JAXBElement;

import br.com.taragona.nfe.classes.recepcao.TNFe;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.KeyXmlManager;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.util.ReadFile;
import br.com.tti.sefaz.xml.XMLGenerator;

public class SendXml {

	private String fileName;

	public SendXml(String fileName) {
		super();
		this.fileName = fileName;
	}

	public void enviar() {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			this.enviarCTe();
		}
		
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			this.enviarNFe();
		}

	}

	public void enviarOld() {
		try {
			XMLGenerator gen = XMLGenerator
					.createXMLGenerator("br.com.taragona.nfe.classes.recepcao");
			String xml = ReadFile.readFile(this.fileName);
			JAXBElement<TNFe> nfe = (JAXBElement<TNFe>) gen.toObject(xml);

			ManagerInterface manager = Locator.getManagerReference();

			Hashtable<String, CNPJData> cnpjs = manager.getCNPJ();

			for (String cnpj : cnpjs.keySet()) {
				// for (int i = 0; i < 3; i++) {

				String keyXml = nfe.getValue().getInfNFe().getId().replace(
						"NFe", "");
				keyXml = KeyXmlManager.generateString(44);
				manager.sendXml(keyXml, xml, cnpj, nfe.getValue().getInfNFe()
						.getDest().getCNPJ(), nfe.getValue().getInfNFe()
						.getIde().getDEmi().toString(), "", "35",
						SystemProperties.AMBIENT_HOMOLOGACAO, true, false);
				// }
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void enviarNFe() {
		try {
			XMLGenerator gen = XMLGenerator
					.createXMLGenerator("br.com.taragona.nfe.classes.recepcao");
			String xml = ReadFile.readFile(this.fileName);
			JAXBElement<TNFe> nfe = (JAXBElement<TNFe>) gen.toObject(xml);

			ManagerInterface manager = Locator.getManagerReference();

			Vector<String> cnpjs = MainParameters.getCnpjs();

			for (String cnpj : cnpjs) {
				// for (int i = 0; i < 3; i++) {

				String keyXml = nfe.getValue().getInfNFe().getId().replace(
						"NFe", "");
				// keyXml = KeyXmlManager.generateString(44);
				manager.sendXml(keyXml, xml, cnpj, nfe.getValue().getInfNFe()
						.getDest().getCNPJ(), nfe.getValue().getInfNFe()
						.getIde().getDEmi().toString(), "", "35",
						SystemProperties.AMBIENT_HOMOLOGACAO, true, false);
				// }
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviarCTe() {
		try {
			XMLGenerator gen = XMLGenerator
					.createXMLGenerator("br.com.tti.sefaz.classes.recepcao");
			String xml = ReadFile.readFile(this.fileName);
			ManagerInterface manager = Locator.getManagerReference();
			manager.sendXml(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);
		SendXml a = new SendXml(MainParameters.getXml());
		a.enviar();
	}

}
