package br.com.tti.sefaz.main;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import br.com.taragona.nfe.gerenciador.wrapper.GerenciadorProxy;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerFacade;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.tools.InitRMIRegistry;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.util.MainParameters;

public class InitManager {

	private ManagerInterface manager;

	public void registerManager() {
		this.manager = new ManagerFacade();
		try {
			Registry registry = LocateRegistry.getRegistry(0);
			Remote obj = UnicastRemoteObject.exportObject(this.manager, 0);
			registry.rebind(Locator.MANGER_REMOTE_NAME, obj);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);

		InitRMIRegistry.init();

		InitManager init = new InitManager();
		init.registerManager();
		MyLogger.getLog().info("Gerenciador Iniciado");

		GerenciadorProxy p = new GerenciadorProxy();
		p.initGerenciadorWrapper();
		MyLogger.getLog().info("Gerenciador Proxy Iniciado...");
	}
}
