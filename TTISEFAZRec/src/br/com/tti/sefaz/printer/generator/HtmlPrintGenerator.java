package br.com.tti.sefaz.printer.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.util.ReadFile;

public class HtmlPrintGenerator implements PrintGenerator {
	private TransformerFactory tf = TransformerFactory.newInstance();
	private StreamSource xsl;
	private Transformer transformer;
	private XMLDataCache cache;

	public HtmlPrintGenerator() {
		this.tf = TransformerFactory.newInstance();
		this.xsl = new StreamSource(new File("E:\\TTICTe\\ctes\\dacte.xsl"));
		this.cache = XMLDataCache.getInstance();
		try {
			transformer = tf.newTransformer(xsl);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void generatePrint(String keyXml) {
		try {
			String file = "D:\\Arquivos de programas\\Apache Software Foundation\\Apache2.2\\htdocs\\"
					+ keyXml + ".html";
			XMLData xmld = this.cache.findData(keyXml);
			String request = xmld.getXmlString();
			request = "<CTe-container>" + request + "</CTe-container>";
			FileOutputStream ff = new FileOutputStream(file);
			transformer.transform(new StreamSource(new StringReader(request)),
					new StreamResult(ff));
			ff.close();

			String html = ReadFile.readFile(file);
			FileWriter fw = new FileWriter(file);
			html = "<html><body onload=\"window.print();\">" + html + "</html>";
			fw.write(html);
			fw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
