package br.com.tti.sefaz.printer.impl;

import java.rmi.RemoteException;

import nfeimpressao.TaragonaInt;
import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.printer.XMLPrinter;
import br.com.tti.sefaz.printer.generator.HtmlPrintGenerator;
import br.com.tti.sefaz.printer.generator.PrintGenerator;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;

public class LaserPrinter implements XMLPrinter {

	private XMLPrinter printer;
	private XMLDataCache cache;
	private PrintGenerator generator;

	public LaserPrinter() {
		this.printer = Locator.getManagerReference();
		this.generator = new HtmlPrintGenerator();
	}

	@Override
	public void printXml(String keyXml, String xml, MODO_OP modo)
			throws RemoteException {
		this.printer.printXml(keyXml, xml, modo);
	}

	@Override
	public void rePrintXml(String keyXml, MODO_OP modo) throws RemoteException {
		this.cache = XMLDataCache.getInstance();

		XMLData xmlData = null;
		try {
			xmlData = this.cache.findData(keyXml);
			this.generator.generatePrint(keyXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (xmlData != null) {
			this.printer.printXml(xmlData.getKeyXml(), xmlData.getXmlString(),
					modo);
		}
	}

	@Override
	public void registrarImpressao(TaragonaInt imp) throws RemoteException {
		this.printer.registrarImpressao(imp);
	}

}
