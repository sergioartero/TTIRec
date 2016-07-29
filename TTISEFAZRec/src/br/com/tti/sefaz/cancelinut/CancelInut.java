package br.com.tti.sefaz.cancelinut;

import java.rmi.RemoteException;
import java.util.logging.Level;

import br.com.tti.sefaz.contingence.ModeOperation;
import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;

public class CancelInut implements CancelInutInterface, ModeOperation {

	private ManagerInterface manager;

	private CancelInutController controller;

	public CancelInut() {
		this.manager = Locator.getManagerReference();
		assert this.manager != null;
		this.controller = new CancelInutController(this.manager);
	}

	@Override
	public String cancelXml(String cnpj, String uf, String ambient,
			String keyXml, String protocol, String just, boolean async)
			throws RemoteException {
		try {
			return this.controller.cancelXml(cnpj, uf, ambient, keyXml,
					protocol, just, async);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, e.getLocalizedMessage());
		}
	}

	@Override
	public String inutXml(String cnpj, String uf, String ambient,
			String keyXml, String justl, boolean async) throws RemoteException {
		try {
			return this.controller.inutXml(cnpj, uf, ambient, keyXml, justl,
					async);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, e.getLocalizedMessage());
		}
	}

	@Override
	public String inutXml(String nOp, String uf, String ambient, String ano,
			String cnpj, String mod, String serie, String ini, String fim,
			String just, boolean async) throws RemoteException {
		try {
			return this.controller.inutXml(nOp, uf, ambient, ano, cnpj, mod,
					serie, ini, fim, just, async);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, e.getLocalizedMessage());
		}
	}

	@Override
	public void changeToState(String cnpj, String ambient, MODO_OP modo)
			throws RemoteException {
		this.controller.changeToModo(cnpj, modo);
	}

	@Override
	public MODO_OP getModo(String cnpj, String ambient) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
