package br.com.tti.sefaz.status;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.listeners.TTIEventNotifier;
import br.com.tti.sefaz.manager.ManagerFacade;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.remote.events.TTIEvent;
import br.com.tti.sefaz.status.local.CheckerStatusThread;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.cte.XMLMessageFactoryCTe;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLMessageFactoryNFe;

public class CheckerStatusController implements TTIEventNotifier {

	private Vector<TTIEventListener> listeners;
	private ManagerFacade manager;
	private XMLMessageFactory xmlFactory;

	public CheckerStatusController(ManagerFacade manager) {
		this.manager = manager;
		this.listeners = new Vector<TTIEventListener>();

		Hashtable<Vector<String>, Vector<SchemaVersionConfig>> schemaVersion = null;
		try {
			schemaVersion = this.manager.getSchemasVersion();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			this.xmlFactory = new XMLMessageFactoryNFe(schemaVersion);
		}
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			this.xmlFactory = new XMLMessageFactoryCTe(schemaVersion);
		}
	}

	@Override
	public void addListener(TTIEventListener listener) throws RemoteException {
		this.listeners.add(listener);
	}

	@Override
	public void notifyEvent(TTIEvent event) throws RemoteException {
		for (TTIEventListener lis : this.listeners) {
			lis.processEvent(event);
		}
	}
	
	public void initStatusCheckThread(String uf, String ambient){
		CheckerStatusThread check = new CheckerStatusThread(uf, ambient,
				this.manager, this.xmlFactory, this);

		Thread t = new Thread(check);
		t.start();

		
	}

	private boolean checkStatus(String uf, String ambient) {

		return true;
	}

	private boolean checkService(String uf, String ambient, String idServico) {
		Hashtable prop = null;
		try {
			return this.manager.checkXMLMessage(idServico, prop);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
