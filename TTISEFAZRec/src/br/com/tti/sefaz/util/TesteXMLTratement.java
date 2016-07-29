package br.com.tti.sefaz.util;

public class TesteXMLTratement {
	private static String tratarXML(String xml) {
		int posi = 0;
		int pos1 = 0;

		while (pos1 != -1) {
			pos1 = xml.indexOf("<cProdPartNumber>", posi);

			if (pos1 == -1) {
				posi = posi + "<cProdPartNumber>".length();

				if (posi >= xml.length())
					break;

				continue;
			}

			int pos2 = xml.indexOf("</cProdPartNumber>", pos1);
			pos2 = pos2 + "</cProdPartNumber>".length();

			// String substring = xml.substring(posi, pos2);

			int poss = xml.indexOf("<cProd>", posi);

			String cprodpart = xml.substring(poss, pos1);
			String cnewprodpart = xml.substring(pos1, pos2);

			String newcode = xml.substring(pos1 + "<cProdPartNumber>".length(),
					pos2 - "</cProdPartNumber>".length());

			xml = xml.replace(cprodpart, "<cProd>" + newcode + "</cProd>");
			xml = xml.replace(cnewprodpart, "");

			// xml = xml.indexOf(pos2, xml.length());

			posi = pos2 + 10;
			// pos1 = xml.indexOf("<cProdPartNumber>", posi);
		}
		return xml;
	}

	public static void main(String[] args) {
		try {
			String xml = ReadFile.readFile("C:\\xmlComNovaTagDePartNumber.xml");
			String xml1 = tratarXML(xml);
			System.out.println(xml1);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
}
