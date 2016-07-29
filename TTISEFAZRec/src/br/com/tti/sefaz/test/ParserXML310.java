package br.com.tti.sefaz.test;

import java.io.File;

import javax.xml.bind.JAXBElement;

import br.com.tti.nfev200.TNFe;
import br.com.tti.sefaz.xml.XMLGenerator;

public class ParserXML310 {

	public static void main(String[] args) {
		XMLGenerator gen = new XMLGenerator("br.com.tti.nfev200");
		try {
			TNFe nfe = ((JAXBElement<TNFe>) gen
					.toObjectFromFile(new File(
							"C:\\Users\\Administrador.WIN-SERVIDOR\\Desktop\\XML_v310.xml")))
					.getValue();
			System.out.println(nfe.getInfNFe().getVersao());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
