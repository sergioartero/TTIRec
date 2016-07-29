package br.com.tti.sefaz.contingence;

import java.rmi.RemoteException;

import br.com.tti.sefaz.contingence.impl.EnterContingenceNFe;
import br.com.tti.sefaz.contingence.impl.LeaveContingenceNFe;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;


public class ContingenceController {

	private ManagerInterface manager;

	public ContingenceController(ManagerInterface manager) {
		super();
		this.manager = manager;
	}

	public void enterContingence(String cnpj, String ambient, boolean async,
			MODO_OP type) throws RemoteException {

		if (type.equals(MODO_OP.CONTINGENCE)) {
			EnterContingenceStrategy strategy = new EnterContingenceNFe(
					this.manager, cnpj, ambient);
			if (async) {
				Thread t = new Thread(strategy);
				t.start();
			} else {
				strategy.enterContingence();
			}
		}
	}

	public void leaveContingence(String cnpj, String ambient, boolean async,
			MODO_OP modo) throws RemoteException {

		if (modo.equals(MODO_OP.CONTINGENCE)) {
			LeaveContingenceStrategy strategy = new LeaveContingenceNFe(
					this.manager, cnpj, ambient);
			if (async) {
				Thread t = new Thread(strategy);
				t.start();
			} else {
				strategy.leaveContingence();
			}
		}
	}
}
