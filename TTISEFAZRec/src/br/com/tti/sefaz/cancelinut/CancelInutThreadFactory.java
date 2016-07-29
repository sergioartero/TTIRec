package br.com.tti.sefaz.cancelinut;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.cte.XMLMessageFactoryCTe;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLMessageFactoryNFe;

public class CancelInutThreadFactory {

	private static CancelInutThreadFactory factory;

	private ManagerInterface manager;

	private XMLMessageFactory xmlFactory;

	private XMLDataCache cacheXml;

	public static CancelInutThreadFactory getFactory(ManagerInterface manager) {
		if (factory == null) {
			factory = new CancelInutThreadFactory(manager);
		}
		return factory;
	}

	public static CancelInutThreadFactory getFactory() {
		ManagerInterface manager = Locator.getManagerReference();
		if (factory == null) {
			factory = new CancelInutThreadFactory(manager);
		}
		return factory;
	}

	private CancelInutThreadFactory(ManagerInterface mangaer) {
		super();
		this.manager = mangaer;

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

	public CancelThread createCancelThread(String cnpj, String uf, String ambient,
			String keyXml, String protocol, String just, MODO_OP modo) {
		CancelThread cancel = new CancelThread(this.manager, this.cacheXml,
				this.xmlFactory, cnpj, uf, ambient, keyXml, protocol, just, modo);
		return cancel;
	}

	public InutThread createInutThread(String uf, String ambient,
			String keyXml, String justl, MODO_OP modo) {
		InutThread inut = new InutThread(this.manager, this.cacheXml,
				this.xmlFactory, uf, ambient, keyXml, justl, modo);
		return inut;
	}

	public InutThread createInutThread(String nOp, String uf, String ambient,
			String ano, String cnpj, String mod, String serie, String ini,
			String fim, String just, MODO_OP modo) {
		InutThread inut = new InutThread(this.manager, this.cacheXml,
				this.xmlFactory, nOp, uf, ambient, ano, cnpj, mod, serie, ini,
				fim, just, modo);
		return inut;
	}

}
