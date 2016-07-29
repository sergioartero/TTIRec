package br.com.tti.sefaz.messenger;

import java.rmi.RemoteException;

import br.com.tti.sefaz.classes.recepcao.TCTe;
import br.com.tti.sefaz.contingence.ModeOperation;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.MessengerConfig;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;

public class Messenger implements MessengerInterface, ModeOperation {

	private ManagerInterface manager;
	private MessengerController controller;
	private PreProcess preproc;

	public Messenger() {
		this.manager = Locator.getManagerReference();
		assert this.manager != null;
		this.controller = new MessengerController(this.manager);
		this.preproc = new PreProcess();

		Thread t = new Thread(this.controller);
		t.start();
	}

	@Override
	public void sendXml(String keyXml, String xml, String cnpjSender,
			String cnpjReceiver, String dateEmiss, String message, String uf,
			String ambient, boolean sign, boolean error) throws RemoteException {
		RequestThread request = new RequestThread(this.manager,
				this.controller, keyXml, xml, cnpjSender, cnpjReceiver,
				dateEmiss, message, uf, ambient, sign, error);
		Thread t = new Thread(request);
		t.start();
	}

	@Override
	public void setMessengerParameters(String cnpj, String ambient,
			MessengerConfig msnConfig) throws RemoteException {
		this.controller.setParameters(cnpj, ambient, msnConfig);

	}

	@Override
	public void changeToState(String cnpj, String ambient, MODO_OP modo)
			throws RemoteException {
		this.controller.changeToModo(cnpj, ambient, modo);
	}

	@Override
	public MODO_OP getModo(String cnpj, String ambient) throws RemoteException {
		return this.controller.getModo(cnpj, ambient);
	}

	@Override
	public void sendXml(String xml) throws RemoteException {
		this.preproc.processXML(xml);
		TCTe cte = this.preproc.getCte().getValue();

		String keyXml = cte.getInfCte().getId().replace("CTe", "");
		String cnpjSender = cte.getInfCte().getEmit().getCNPJ();
		String cnpjReceiver = cte.getInfCte().getDest().getCNPJ();
		String dateEmiss = cte.getInfCte().getIde().getDhEmi();
		String uf = cte.getInfCte().getIde().getCUF();

		String ambient = cte.getInfCte().getIde().getTpAmb();
		if (ambient.equals("1")) {
			ambient = SystemProperties.AMBIENT_PRODUCAO;
		} else {
			ambient = SystemProperties.AMBIENT_HOMOLOGACAO;
		}

		if (this.preproc.isError()) {
			this.sendXml(keyXml, xml, cnpjSender, cnpjReceiver, dateEmiss,
					this.preproc.getMessage(), uf, ambient, true, true);

		} else {
			this.sendXml(keyXml, xml, cnpjSender, cnpjReceiver, dateEmiss,
					null, uf, ambient, true, false);

		}
	}

	@Override
	public boolean isActive() throws RemoteException {
		return true;
	}

}
