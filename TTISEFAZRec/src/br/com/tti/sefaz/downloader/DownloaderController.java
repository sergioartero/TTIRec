package br.com.tti.sefaz.downloader;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import br.com.tti.sefaz.log.MyLogger;

public class DownloaderController {
	public static void main(String[] args) {
		try {
			String pfxparam = args[0];
			Registry reg = LocateRegistry.getRegistry("localhost", 0);
			DownloaderInterface downloader = new Downloader(pfxparam);
			DownloaderInterface stub = (DownloaderInterface) UnicastRemoteObject
					.exportObject(downloader, 0);

			reg.rebind("xmldownloaderws" + pfxparam, stub);
			MyLogger.getLog().info("Downloader inicializado...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
