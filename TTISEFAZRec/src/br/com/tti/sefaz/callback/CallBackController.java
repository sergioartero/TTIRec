package br.com.tti.sefaz.callback;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.remote.CallBackConfig;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.ModoOpManager;

public class CallBackController {

	private Hashtable<String, SystemProperties.MODO_OP> modos;
	private ManagerInterface manager;
	private CheckThreadFactory checkFactory;
	private ModoOpManager managerModo;

	public CallBackController(ManagerInterface manager) {
		super();
		this.manager = manager;
		this.managerModo = new ModoOpManager();
		this.checkFactory = CheckThreadFactory.getFactory(this.manager);
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

	public void checkXmlSet(String cnpj, String nSet, String nRecibo,
			Vector<String> keyXml, String uf, String ambient)
			throws RemoteException {
		MODO_OP modo = this.modos.get(cnpj);
		if (modo.equals(SystemProperties.MODO_OP.NORMAL)) {
			SetCheckThread checker = this.checkFactory.createCheckThread(cnpj,
					nRecibo, nSet, keyXml, uf, ambient);
			Thread t = new Thread(checker);
			t.start();
		}
	}

	public void changeToModo(String cnpj, MODO_OP modo) {
		MyLogger.getLog()
				.info(
						"Changing state: Callback" + " for: " + cnpj
								+ " with: " + modo);
		this.managerModo.saveModo(cnpj, modo);
		this.modos.put(cnpj, modo);
	}

	public void setParameters(String cnpj, CallBackConfig config) {
		this.checkFactory.changeConfig(cnpj, config);
	}
}
