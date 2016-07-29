package br.com.tti.sefaz.xmlgenerate.cte;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.classes.retrecepcao.TProtCTe;
import br.com.tti.sefaz.classes.retrecepcao.TRetConsReciCTe;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperProtocol;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperReturnCallBack;

public class XMLWrapperReturnCallBackCTeImpl implements
		XMLWrapperReturnCallBack {

	private JAXBElement<TRetConsReciCTe> ret;
	private Vector<XMLWrapperProtocol> prots;
	private XMLGenerator gen;

	public XMLWrapperReturnCallBackCTeImpl(String xml) throws Exception {
		this.gen = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.retrecepcao");
		xml = this.getStringXML(xml);
		this.ret = (JAXBElement<TRetConsReciCTe>) this.gen.toObject(xml);

		this.processMessage();
	}

	private String getStringXML(String xml) {
		int pos1 = xml.indexOf("<retConsReciCTe");
		int pos2 = xml.indexOf("</cteRetRecepcaoResult>");

		if (pos1 != -1 && pos2 != -1)
			return xml.substring(pos1, pos2);
		return xml;
	}

	private void processMessage() {
		Hashtable<String, XMLWrapperProtocol> table = new Hashtable<String, XMLWrapperProtocol>();

		List<TProtCTe> prots = this.ret.getValue().getProtCTe();
		for (TProtCTe protNFe : prots) {
			XMLWrapperProtocolCTeImpl protocol = new XMLWrapperProtocolCTeImpl(
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
		return this.ret.getValue().getXMotivo();
	}

	@Override
	public String getXNRec() {
		return this.ret.getValue().getNRec();
	}

	public static void main(String[] args) {
		String xml = "<retConsReciCTe xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\"1.01\"><tpAmb>2</tpAmb><verAplic>SP_PL_CTe_102</verAplic><nRec>350000000165030</nRec><cStat>225</cStat><xMotivo>Rejeição: Falha no Schema XML do CT-e</xMotivo><cUF>35</cUF></retConsReciCTe>";
		try {
			XMLWrapperReturnCallBackCTeImpl wr = new XMLWrapperReturnCallBackCTeImpl(
					xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getXml() {
		// TODO Auto-generated method stub
		return null;
	}
}
