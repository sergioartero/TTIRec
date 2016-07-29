package br.com.tti.sefaz.callback;

import java.rmi.RemoteException;
import java.util.Vector;

import br.com.tti.sefaz.contingence.ModeOperation;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.CallBackConfig;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;

public class CallBack implements CallBackInteface, ModeOperation {

	private ManagerInterface manager;
	private CallBackController controller;

	public CallBack() {
		this.manager = Locator.getManagerReference();
		assert manager != null;
		this.controller = new CallBackController(this.manager);
	}

	@Override
	public void checkXmlSet(String cnpj, String nSet, String nRecibo,
			Vector<String> keyXml, String uf, String ambient)
			throws RemoteException {
		this.controller.checkXmlSet(cnpj, nSet, nRecibo, keyXml, uf, ambient);
	}

	@Override
	public void setCallBackParameters(String cnpj, String uf,
			CallBackConfig config) throws RemoteException {
		this.controller.setParameters(cnpj, config);
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
