package br.com.tti.sefaz.callback;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.cache.SetDataCache;
import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.CallBackConfig;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.cte.XMLMessageFactoryCTe;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLMessageFactoryNFe;

public class CheckThreadFactory {

	private XMLDataCache cacheXml;
	private SetDataCache cacheSet;
	private ManagerInterface manager;

	private XMLMessageFactory xmlFactory;
	private Hashtable<String, CallBackConfig> tableConfig;

	private static CheckThreadFactory checkFactory = null;

	public static CheckThreadFactory getFactory(ManagerInterface manager) {
		if (checkFactory == null) {
			checkFactory = new CheckThreadFactory(manager);
		}
		return checkFactory;
	}

	public static CheckThreadFactory getFactory() {
		if (checkFactory != null) {
			ManagerInterface manager = Locator.getManagerReference();
			checkFactory = new CheckThreadFactory(manager);
		}
		return checkFactory;
	}

	private CheckThreadFactory(ManagerInterface manager) {
		this.manager = manager;
		this.cacheXml = XMLDataCache.getInstance();
		this.cacheSet = SetDataCache.getINSTANCE();

		Hashtable<Vector<String>, Vector<SchemaVersionConfig>> schemaVersion = null;
		try {
			tableConfig = this.manager.getCallBackConfig();
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

	public SetCheckThread createCheckThread(String cnpj, String nRecibo,
			String nSet, Vector<String> keyXml, String uf, String ambient) {
		CallBackConfig conf = this.tableConfig.get(cnpj);
		SetCheckThread checker = new SetCheckThread(this.manager,
				this.xmlFactory, this.cacheXml, this.cacheSet, nRecibo, nSet,
				keyXml, uf, ambient, conf.getTimeProc());
		return checker;
	}

	public void changeConfig(String cnpj, CallBackConfig config) {
		this.tableConfig.put(cnpj, config);
	}

}
