package br.com.tti.sefaz.xmlgenerate.cte;

import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.classes.recepcao.TRetEnviCTe;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperReturnSend;

public class XMLWrapperReturnCTeImpl implements XMLWrapperReturnSend {

	private JAXBElement<TRetEnviCTe> ret;

	private XMLGenerator gen;

	public XMLWrapperReturnCTeImpl(String xml) throws Exception {
		this.gen = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.recepcao");

		xml = this.extractElement(xml);
		this.ret = (JAXBElement<TRetEnviCTe>) this.gen.toObject(xml);
	}

	private String extractElement(String xml) {
		int pos1 = xml.indexOf("<retEnviCte");
		int pos2 = xml.indexOf("</cteRecepcaoLoteResult>");

		return xml.substring(pos1, pos2);
	}

	@Override
	public String getCStat() {
		return this.ret.getValue().getCStat();
	}

	@Override
	public String getDhRecbto() {
		if (this.ret.getValue().getInfRec().getDhRecbto() != null)
			return this.ret.getValue().getInfRec().getDhRecbto().toString();
		return null;
	}

	@Override
	public String getNumberRecibo() {
		return this.ret.getValue().getInfRec().getNRec();
	}

	@Override
	public String getTpAmb() {
		return this.ret.getValue().getTpAmb();
	}

	@Override
	public String getXMotivo() {
		return this.ret.getValue().getXMotivo();
	}

	public static void main(String[] args) {
		String xml = "<retEnviCte xmlns=\"http://www.portalfiscal.inf.br/cte\" versao=\"1.02\"><tpAmb>2</tpAmb><cUF>35</cUF><verAplic>SP_PL_CTe_102</verAplic><cStat>103</cStat><xMotivo>Lote recebido com sucesso</xMotivo><infRec><nRec>350000000165010</nRec><dhRecbto>2009-04-05T13:11:17</dhRecbto><tMed/></infRec></retEnviCte>";
		try {
			XMLWrapperReturnCTeImpl r = new XMLWrapperReturnCTeImpl(xml);

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
