package br.com.tti.sefaz.querier;

import java.rmi.RemoteException;
import java.util.logging.Level;

import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.util.Locator;

public class Querier implements QuerierInterface {

	private QuerierController controller;
	private ManagerInterface manager;

	public Querier() {
		this.manager = Locator.getManagerReference();
		assert this.manager != null;
		this.controller = new QuerierController(this.manager);
	}

	@Override
	public SefazState makeQueryState(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws RemoteException {
		try {
			return this.controller.makeQueryState(uf, ambient, keyXml, persist,
					assyn);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, e.getLocalizedMessage());
		}
	}

	@Override
	public String makeQueryXML(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws RemoteException {
		try {
			return this.controller.makeQueryXML(uf, ambient, keyXml, persist,
					assyn);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, e.getLocalizedMessage());
		}
	}

}
