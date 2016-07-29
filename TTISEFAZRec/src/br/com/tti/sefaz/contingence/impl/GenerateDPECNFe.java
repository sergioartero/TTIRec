package br.com.tti.sefaz.contingence.impl;

import javax.xml.bind.JAXBElement;

import br.com.taragona.nfe.classes.recepcao.TNFe;
import br.com.tti.dpec.classes.ObjectFactory;
import br.com.tti.dpec.classes.TDPEC;
import br.com.tti.dpec.classes.TDPEC.InfDPEC.ResNFe;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.xml.XMLGenerator;

public class GenerateDPECNFe {

	private XMLGenerator xgenDpec;
	private XMLGenerator xgenXml;
	private ObjectFactory factory;
	
	public GenerateDPECNFe() {
		this.xgenDpec = XMLGenerator.createXMLGenerator("pacote de xml do dpec");
		this.xgenXml = XMLGenerator.createXMLGenerator("dafsd");
		this.factory = new ObjectFactory();
		
	}
	
	public String makeDPECXMLString(XMLData xmlData){
	
		try {
			JAXBElement<TNFe> nfe = (JAXBElement<TNFe>) this.xgenXml
					.toObject(xmlData.getXmlString());
			String key = nfe.getValue().getInfNFe().getId();
			
			TDPEC dpec = this.factory.createTDPEC();
			dpec.setInfDPEC(this.factory.createTDPECInfDPEC());			
		
			
			/*
			items = nfe.getValue().ge*/
			
			ResNFe res = this.factory.createTDPECInfDPECResNFe();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;		
	}
}
