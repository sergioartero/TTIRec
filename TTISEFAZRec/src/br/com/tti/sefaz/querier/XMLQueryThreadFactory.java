package br.com.tti.sefaz.querier;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.cte.XMLMessageFactoryCTe;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLMessageFactoryNFe;

public class XMLQueryThreadFactory {

	private static XMLQueryThreadFactory factory;

	public static XMLQueryThreadFactory getFactory(ManagerInterface manager) {
		if (factory == null) {
			factory = new XMLQueryThreadFactory(manager);
		}
		return factory;
	}

	public static XMLQueryThreadFactory getFactory() {
		if (factory == null) {
			ManagerInterface manager = Locator.getManagerReference();
			factory = new XMLQueryThreadFactory(manager);
		}
		return factory;
	}

	private ManagerInterface manager;
	private XMLDataCache cacheXml;
	private XMLMessageFactory xmlFactory;

	private XMLQueryThreadFactory(ManagerInterface manager) {
		this.manager = manager;
		this.cacheXml = XMLDataCache.getInstance();

		Hashtable<Vector<String>, Vector<SchemaVersionConfig>> schemaVersion = null;

		try {
			schemaVersion = this.manager.getSchemasVersion();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			this.xmlFactory = new XMLMessageFactoryNFe(schemaVersion);
		}
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			this.xmlFactory = new XMLMessageFactoryCTe(schemaVersion);
		}
	}

	public XMLQueryThread createQueryThread(String uf, String ambient,
			String keyXml, boolean persist) {

		XMLQueryThread q = new XMLQueryThread(this.manager, this.cacheXml,
				this.xmlFactory, uf, ambient, keyXml, persist);
		return q;
	}
}
