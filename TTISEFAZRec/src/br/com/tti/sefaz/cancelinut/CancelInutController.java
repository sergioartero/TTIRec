package br.com.tti.sefaz.cancelinut;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.ModoOpManager;

public class CancelInutController {

	private ManagerInterface manager;
	private CancelInutThreadFactory factory;
	private Hashtable<String, MODO_OP> modos;
	private XMLDataCache cacheXml;

	private ModoOpManager managerModo;

	public CancelInutController(ManagerInterface manager) {
		super();
		this.manager = manager;
		this.factory = CancelInutThreadFactory.getFactory(this.manager);
		this.cacheXml = XMLDataCache.getInstance();

		this.managerModo = new ModoOpManager();
		this.modos = new Hashtable<String, MODO_OP>();

		this.initModos();
	}

	private void initModos() {
		try {
			Hashtable<String, CNPJData> cnpjs = this.manager.getCNPJ();
			for (String cnpj : cnpjs.keySet()) {
				MODO_OP modo = this.managerModo.getModo(cnpj);
				if (modo == null) {
					modo = MODO_OP.NORMAL;
					this.managerModo.saveModo(cnpj, modo);
				}
				this.modos.put(cnpj, modo);
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String cancelXml(String cnpj, String uf, String ambient,
			String keyXml, String protocol, String just, boolean async)
			throws Exception {

		MODO_OP modo = this.modos.get(cnpj);
		if (protocol == null) {
			XMLData xmld = this.cacheXml.findData(keyXml);
			if (xmld == null)
				return "Nota nao encontrada";

			protocol = xmld.getNAutorizedProtocol();

			if (protocol == null) {
				this.manager.makeQueryState(uf, ambient, keyXml, true, false);
				xmld = this.cacheXml.findData(keyXml);
				protocol = xmld.getNAutorizedProtocol();
			}

			if (protocol == null) {
				return null;
			}
		}

		CancelThread cancelT = this.factory.createCancelThread(cnpj, uf,
				ambient, keyXml, protocol, just, modo);

		if (async) {
			Thread t = new Thread(cancelT);
			t.start();
			return null;
		} else {
			return cancelT.initCancel();
		}

	}

	public String inutXml(String cnpj, String uf, String ambient,
			String keyXml, String justl, boolean asyn) throws Exception {
		MODO_OP modo = this.modos.get(cnpj);
		InutThread inutT = this.factory.createInutThread(uf, ambient, keyXml,
				justl, modo);
		if (asyn) {
			Thread t = new Thread(inutT);
			t.start();
			return null;
		} else {
			return inutT.initInut();
		}
	}

	public String inutXml(String nOp, String uf, String ambient, String ano,
			String cnpj, String mod, String serie, String ini, String fim,
			String just, boolean asyn) throws Exception {
		MODO_OP modo = this.modos.get(cnpj);

		InutThread inutT = this.factory.createInutThread(nOp, uf, ambient, ano,
				cnpj, mod, serie, ini, fim, just, modo);
		if (asyn) {
			Thread t = new Thread(inutT);
			t.start();
			return null;
		} else {
			return inutT.initInut();
		}
	}

	public void changeToModo(String cnpj, MODO_OP modo) {
		MyLogger.getLog().info(
				"Changing state: Cancel-Inut" + " for: " + cnpj + " with: "
						+ modo);

		this.managerModo.saveModo(cnpj, modo);
		this.modos.put(cnpj, modo);
	}
}
