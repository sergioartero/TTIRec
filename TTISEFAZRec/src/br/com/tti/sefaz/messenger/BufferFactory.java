package br.com.tti.sefaz.messenger;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import br.com.tti.sefaz.cache.SetDataCache;
import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.listeners.impl.EventManagerListenerImpl;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.MessengerConfig;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.cte.XMLMessageFactoryCTe;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLMessageFactoryNFe;

public class BufferFactory {
	private ManagerInterface manager;

	private XMLDataCache cacheXml;
	private SetDataCache cacheSet;
	private XMLMessageFactory factory;

	private Hashtable<String, MessengerConfig> tableConfig;

	private static BufferFactory bufferFactory;

	public static BufferFactory getFactory() {
		if (bufferFactory == null) {
			ManagerInterface manager = Locator.getManagerReference();
			bufferFactory = new BufferFactory(manager);
		}
		return bufferFactory;
	}

	public static BufferFactory getFactory(ManagerInterface manager) {
		if (bufferFactory == null) {
			bufferFactory = new BufferFactory(manager);
		}
		return bufferFactory;
	}

	private BufferFactory(ManagerInterface manager) {
		super();
		this.manager = manager;
		this.cacheXml = XMLDataCache.getInstance();
		this.cacheSet = SetDataCache.getINSTANCE();

		this.cacheXml.addListener(new EventManagerListenerImpl());

		Hashtable<Vector<String>, Vector<SchemaVersionConfig>> schemaVersion = null;
		try {
			tableConfig = this.manager.getMessengerConfig();
			schemaVersion = this.manager.getSchemasVersion();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			this.factory = new XMLMessageFactoryNFe(schemaVersion);
		}
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			this.factory = new XMLMessageFactoryCTe(schemaVersion);
		}
	}

	public XMLBuffer createBuffer(String cnpj, String ambient) {
		assert manager != null;

		XMLBuffer buffer = new XMLBuffer(this.manager, this.factory,
				this.cacheXml, this.cacheSet);

		MessengerConfig config = tableConfig.get(cnpj);

		buffer.setAmbient(ambient);
		buffer.setCnpj(cnpj);
		buffer.setUf(config.getUf());
		buffer.setSizeFile(config.getSizeFile());
		buffer.setSizeSet(config.getSizeSet());
		buffer.setTimeout(config.getTimeout());

		return buffer;
	}
}
