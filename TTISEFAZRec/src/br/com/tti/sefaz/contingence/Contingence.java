package br.com.tti.sefaz.contingence;

import java.rmi.RemoteException;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.StateSystem;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;

public class Contingence implements ContingeceInterface {

	private ManagerInterface manager;
	private ContingenceController controller;
	private StateSystem system;

	
	public Contingence() {
		this.manager = Locator.getManagerReference();
		assert this.manager != null;
		this.controller = new ContingenceController(this.manager);
		this.system = new StateSystem();
	}

	@Override
	public void enterContingence(String cnpj, String ambient, boolean async,
			MODO_OP modo) throws RemoteException {
		this.controller.enterContingence(cnpj, ambient, async, modo);
	}

	@Override
	public void leaveContingence(String cnpj, String ambient, boolean async,
			MODO_OP type) throws RemoteException {
		this.controller.leaveContingence(cnpj, ambient, async, type);
	}

	

}
