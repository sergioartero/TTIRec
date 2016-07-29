package br.com.tti.sefaz.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import br.com.tti.sefaz.util.MainParameters;

public class XMLValidator {
	private SchemaFactory schemaFactory;
	private Schema schema;
	private Validator validator;

	public XMLValidator(String xsdFile) {
		Source schemaSource = new StreamSource(new File(xsdFile));

		this.schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			this.schema = this.schemaFactory.newSchema(schemaSource);
			this.validator = this.schema.newValidator();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void validateXml(String xml) throws SAXException, IOException {
		StringReader sr = new StringReader(xml);
		StreamSource fileStream = new StreamSource(sr);
		this.validator.validate(fileStream);
	}

	public void validateXml(File file) throws SAXException, IOException {

		StreamSource fileStream = new StreamSource(file);
		this.validator.validate(fileStream);

	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);
		XMLValidator x = new XMLValidator(
				"C:\\PL_005f\\PL_005f\\nfe_v1.10.xsd");

		File f = new File("C:/Users/Crhistian/Downloads/04648575000176_1_7601.xml");
		try {
			x.validateXml(f);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
