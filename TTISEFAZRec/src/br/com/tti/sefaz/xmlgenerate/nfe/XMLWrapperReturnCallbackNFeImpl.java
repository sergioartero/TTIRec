package br.com.tti.sefaz.xmlgenerate.nfe;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBElement;

import br.com.taragona.nfe.classes.retrecepcao.TProtNFe;
import br.com.taragona.nfe.classes.retrecepcao.TRetConsReciNFe;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperProtocol;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperReturnCallBack;

public class XMLWrapperReturnCallbackNFeImpl implements
		XMLWrapperReturnCallBack {

	private JAXBElement<TRetConsReciNFe> ret;
	private XMLGenerator xmlGen;
	private Vector<XMLWrapperProtocol> prots;
	private String xml;

	public XMLWrapperReturnCallbackNFeImpl(String xmlReturn) throws Exception {
		this.xmlGen = XMLGenerator
				.createXMLGenerator("br.com.taragona.nfe.classes.retrecepcao");
		this.xml = this.extractElement(xmlReturn);
		MyLogger.getLog().info(xmlReturn);
		ret = (JAXBElement<TRetConsReciNFe>) this.xmlGen.toObject(this.xml);
		this.processMessage();
	}

	private String extractElement(String xml) {
		int pos1 = xml.indexOf("<retConsReciN");
		int pos2 = xml.indexOf("</nfeRetRecepcao2Result>");

		return xml.substring(pos1, pos2);
	}

	private void processMessage() {
		Hashtable<String, XMLWrapperProtocol> table = new Hashtable<String, XMLWrapperProtocol>();

		List<TProtNFe> prots = this.ret.getValue().getProtNFe();
		for (TProtNFe protNFe : prots) {
			XMLWrapperProtocolImplNFe protocol = new XMLWrapperProtocolImplNFe(
					protNFe);
			XMLWrapperProtocol lastProt = table.get(protocol.getCh());
			if (lastProt == null) {
				table.put(protocol.getCh(), protocol);
			} else {
				if (!lastProt.getCStat().equals(
						States.getINSTANCE().getAuthorizedXml().getCode() + "")) {
					table.put(protocol.getCh(), protocol);
				}
			}
			// this.prots.add();
		}
		this.prots = new Vector<XMLWrapperProtocol>();
		for (XMLWrapperProtocol p : table.values()) {
			this.prots.add(p);
		}

	}

	@Override
	public String getCStat() {
		return this.ret.getValue().getCStat();
	}

	@Override
	public Vector<XMLWrapperProtocol> getProt() {
		return this.prots;
	}

	@Override
	public String getXMotivo() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getXMotivo();
	}

	@Override
	public String getXNRec() {
		// TODO Auto-generated method stub
		return this.ret.getValue().getNRec();
	}

	@Override
	public String getXml() {
		return this.xml;
	}

}
