package br.com.tti.sefaz.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

import br.com.tti.sefaz.downloader.DownloaderInterface;

public class TestDown {

	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.getRegistry("localhost");
			Hashtable<String, Object> props = new Hashtable<String, Object>();
			props.put("cnpjdest", "11245802000188");
			DownloaderInterface d = (DownloaderInterface) reg
					.lookup("xmldownloaderwsC:\\TTIRec\\certificados\\tome2013.pfx");
			d.downloadNFe("35130111245802000188550020000104751010791918", props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
