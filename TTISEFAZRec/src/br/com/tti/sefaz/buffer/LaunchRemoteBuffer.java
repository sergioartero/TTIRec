package br.com.tti.sefaz.buffer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import br.com.tti.sefaz.externaldbaccess.ExternalDBAccess;
import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.events.ChangeStateXmlEvent;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.util.MainParameters;

public class LaunchRemoteBuffer {

	private static String BUFFER_NAME = "ttibuffer";

	private XMLBuffer buffer;

	private ManagerInterface manager;
	private ExternalDBAccess access;

	public LaunchRemoteBuffer() {
		/*this.manager = Locator.getManagerReference();
		this.access = Locator.getExternalAccess();*/
	}

	public void register() {
		this.manager = Locator.getManagerReference();
		
		if (MainParameters.isExternaldb()) {
			this.access = Locator.getExternalAccess();
			if (this.access == null) {
				MyLogger.getLog().info("External access dont found!!");
				System.exit(0);
			}
			this.buffer = new XMLBuffer(this.access);
		} else {			
			if (this.manager == null) {
				MyLogger.getLog().info("Manager dont found!!");
				System.exit(0);
			}
			this.buffer = new XMLBuffer(this.manager);
		}

		try {
			TTIEventListener stub = (TTIEventListener) UnicastRemoteObject
					.exportObject(this.buffer, 0);
			Registry rmiregistry = LocateRegistry.getRegistry(0);
			rmiregistry.rebind(BUFFER_NAME, stub);
			this.manager.addListener(stub);
		} catch (Exception e) {
			e.printStackTrace();
			MyLogger.getLog().info("Communication problems!!");
		}
	}

	public Buffer<ChangeStateXmlEvent> getBuffer() {
		return buffer;
	}

	public static void main(String[] args) {
		LaunchRemoteBuffer launcher = new LaunchRemoteBuffer();
		launcher.register();
	}
}
