package br.com.tti.sefaz.messenger;

import java.io.IOException;

import javax.xml.bind.JAXBElement;

import org.xml.sax.SAXException;

import br.com.tti.sefaz.classes.recepcao.TCTe;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xml.XMLValidator;

public class PreProcess {
	private XMLValidator validator;
	private XMLGenerator gen;
	private JAXBElement<TCTe> cte;
	private boolean error;
	private String message;
	
	public PreProcess() {
		this.validator = new XMLValidator(MainParameters.getConfig());
		this.gen = XMLGenerator.createXMLGenerator("br.com.tti.sefaz.classes.recepcao");
	}

	public void processXML(String xml) {
		error = false;
		message = "";
		try {
			this.validator.validateXml(xml);
		} catch (SAXException e) {
			message = e.getMessage();
			error = true;
		} catch (IOException e) {
			message = e.getMessage();
			error = true;
		}
		
		try {
			this.cte = (JAXBElement<TCTe>) this.gen.toObject(xml);
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public XMLValidator getValidator() {
		return validator;
	}

	public XMLGenerator getGen() {
		return gen;
	}

	public JAXBElement<TCTe> getCte() {
		return cte;
	}

	public boolean isError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
