package br.com.tti.sefaz.xmlgenerate.nfe;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperRetNFeDown;
import br.com.tti.sefaz.downloader.xml.TRetDownloadNFe;
import br.com.tti.sefaz.log.MyLogger;

public class XMLWrapperRetNFeDownImpl implements XMLWrapperRetNFeDown,
		Serializable {

	transient private XMLGenerator gen;
	private TRetDownloadNFe retdown;
	private String xmlprocnfe;
	private String xml;

	public XMLWrapperRetNFeDownImpl(String xml) throws Exception {
		int pos1 = xml.indexOf("<retDownloadNFe");
		int pos2 = xml.indexOf("</nfeDownloadNFResult>");

		if (pos1 != -1 && pos2 != -1) {
			xml = xml.substring(pos1, pos2);
			xml = new String(xml.getBytes(), "UTF-8");
		}

		this.xml = xml;

		this.gen = new XMLGenerator("br.com.tti.sefaz.downloader.xml");
		this.retdown = ((JAXBElement<TRetDownloadNFe>) this.gen.toObject(xml))
				.getValue();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);

		try {
			Document doc = factory.newDocumentBuilder().parse(
					new ByteArrayInputStream(xml.getBytes()));

			NodeList elem = doc.getDocumentElement().getElementsByTagName(
					"nfeProc");

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(elem.item(0)),
					new StreamResult(writer));
			this.xmlprocnfe = writer.getBuffer().toString()
					.replaceAll("\n|\r", "");
		} catch (Exception e) {
			MyLogger.getLog().info("erro: " + e.getLocalizedMessage());
			int posa1 = xml.indexOf("<nfeProc");
			int posa2 = xml.indexOf("</procNFe>");

			if (posa1 != -1 && posa2 != -1) {
				this.xmlprocnfe = xml.substring(posa1, posa2);
			}
		}

	}

	@Override
	public String getCompleteXML() {
		return this.xml;
	}

	@Override
	public String getCStat() {
		if (this.retdown.getRetNFe().isEmpty()) {
			return this.retdown.getCStat();
		}
		return this.retdown.getRetNFe().get(0).getCStat();
	}

	@Override
	public String getChNFe() {
		if (!this.retdown.getRetNFe().isEmpty()) {
			return this.retdown.getRetNFe().get(0).getChNFe();
		}

		return null;
	}

	@Override
	public byte[] getProcNFeZip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getXMotivo() {
		if (this.retdown.getRetNFe().isEmpty()) {
			return this.retdown.getXMotivo();
		}
		return this.retdown.getRetNFe().get(0).getXMotivo();
	}

	@Override
	public String getProcNFe() {
		return this.xmlprocnfe;
	}

}
