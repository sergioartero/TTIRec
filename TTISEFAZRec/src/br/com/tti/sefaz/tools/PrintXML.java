package br.com.tti.sefaz.tools;

import java.rmi.RemoteException;

import br.com.tti.sefaz.printer.XMLPrinter;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.util.ReadFile;

public class PrintXML {

	private XMLPrinter printer;

	public PrintXML() {
		printer = Locator.getManagerReference();
	}

	public void printXML(String keyXml, String xml) {
		try {
			this.printer.printXml(keyXml, xml, MODO_OP.NORMAL);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);

		PrintXML print = new PrintXML();
		try {
			String xml = ReadFile.readFile(MainParameters.getXml());
			print.printXML(MainParameters.getOutros(), xml);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
