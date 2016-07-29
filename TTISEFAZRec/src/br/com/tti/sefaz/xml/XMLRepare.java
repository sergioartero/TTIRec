package br.com.tti.sefaz.xml;

import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;

public class XMLRepare {

	public static String repareXmlForSign(String xml) {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			return getOutHead(xml);
		}
		return xml;
	}

	public static String repareXmlAfterSign(String xml) {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			return repareAfterSign(xml);
		}
		return xml;
	}

	private static String getOutHead(String xml) {
		int posicao = xml.indexOf("<NFe");
		xml = xml.substring(posicao);
		xml = xml.replace(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"",
				"");
		return xml;
	}

	private static String repareAfterSign(String xml) {
		xml = xml.replace("<NFe>",
				"<NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\">");
		return xml;
	}
}
