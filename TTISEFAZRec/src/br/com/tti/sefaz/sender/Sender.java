package br.com.tti.sefaz.sender;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.sender.mock.SenderGenericControllerMock;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.Locator;

public class Sender implements SenderInterface {

	private SenderGenericController senderController;
	private ManagerInterface manager;

	public Sender() {
		this.manager = Locator.getManagerReference();
		assert this.manager != null;

		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			this.senderController = new SenderDispatchController(this.manager);
		}
		// this.senderController = new SenderPortController(this.manager);

		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			// this.senderController = new
			// SenderDispatchController(this.manager);

			this.senderController = new SenderGenericControllerMock(
					this.manager);

		}

	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {
		return this.senderController.sendXMLMessage(idServico, header, data,
				prop);
	}

	@Override
	public boolean checkXMLMessage(String idServico, Hashtable prop)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
