package br.com.tti.sefaz.messenger;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Hashtable;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.contingence.StateManager;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.printer.XMLPrinter;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.remote.MessengerConfig;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;

public class MessengerController extends Thread {

	// cnpj, ambient, modo
	private Hashtable<String, Hashtable<String, MODO_OP>> modos;
	private XMLPrinter printer;
	private ManagerInterface manager;
	private BufferFactory factory;
	private StateManager state;

	private XMLDataCache cacheXml;

	// buffers: cnpj, ambiente, buffer
	private Hashtable<String, Hashtable<String, XMLBuffer>> buffers;

	public MessengerController(ManagerInterface manager) {
		super();
		this.manager = manager;
		this.factory = BufferFactory.getFactory(this.manager);
		this.state = new StateManager();
		this.modos = new Hashtable<String, Hashtable<String, MODO_OP>>();
		this.buffers = new Hashtable<String, Hashtable<String, XMLBuffer>>();
		this.cacheXml = XMLDataCache.getInstance();

		this.initModos();
	}

	private void initModos() {
		try {
			Hashtable<String, CNPJData> cnpjs = this.manager.getCNPJ();
			for (String cnpj : cnpjs.keySet()) {
				Hashtable<String, MODO_OP> mss = new Hashtable<String, MODO_OP>();

				MODO_OP modo = this.state.getModo(cnpj,
						SystemProperties.AMBIENT_HOMOLOGACAO);
				if (modo == null) {
					modo = MODO_OP.NORMAL;

					this.state.setModo(cnpj,
							SystemProperties.AMBIENT_HOMOLOGACAO,
							MODO_OP.NORMAL);
				}
				mss.put(SystemProperties.AMBIENT_HOMOLOGACAO, modo);

				modo = this.state.getModo(cnpj,
						SystemProperties.AMBIENT_PRODUCAO);
				if (modo == null) {
					modo = MODO_OP.NORMAL;

					this.state.setModo(cnpj, SystemProperties.AMBIENT_PRODUCAO,
							MODO_OP.NORMAL);
				}
				mss.put(SystemProperties.AMBIENT_PRODUCAO, modo);

				this.modos.put(cnpj, mss);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private XMLBuffer getBufferReference(String cnpj, String ambient) {
		Hashtable<String, XMLBuffer> tableBuffer = this.buffers.get(cnpj);

		if (tableBuffer == null) {
			tableBuffer = new Hashtable<String, XMLBuffer>();
			this.buffers.put(cnpj, tableBuffer);
		}

		XMLBuffer buffer = tableBuffer.get(ambient);
		if (buffer == null) {
			buffer = this.factory.createBuffer(cnpj, ambient);
			tableBuffer.put(ambient, buffer);
		}

		return buffer;
	}

	private void persistXml(String keyXml, String xml, String uf,
			String cnpjSender, String cnpjReceiver, String dateEmiss,
			String ambient, MODO_OP modo, String message, XML_STATE state)
			throws Exception {
		XMLData xmlData = new XMLData();
		xmlData.setKeyXml(keyXml);
		xmlData.setAmbient(ambient);
		xmlData.setCnpjEmit(cnpjSender);
		xmlData.setCnpjDest(cnpjReceiver);
		xmlData.setDateEmiss(dateEmiss);
		xmlData.setXmlString(xml);
		xmlData.setState(state);
		xmlData.setUf(uf);
		xmlData.setModo(modo);
		xmlData.setDateCreate(Calendar.getInstance().getTime());
		xmlData.setXMotivo(message);

		this.cacheXml.saveState(xmlData);
		this.cacheXml.forceFlushCache();

	}

	public void changeToModo(String cnpj, String ambient, MODO_OP modo) {
		MyLogger.getLog().info(
				"Changing state: Messenger" + " for: " + cnpj + " with: "
						+ modo);
		this.state.setModo(cnpj, ambient, modo);
		this.modos.get(cnpj).put(ambient, modo);
	}

	public void addXmlError(String keyXml, String xml, String uf,
			String cnpjSender, String cnpjReceiver, String dateEmiss,
			String ambient, String message) throws Exception {
		MODO_OP modo = this.modos.get(cnpjSender).get(ambient);
		this.persistXml(keyXml, xml, uf, cnpjSender, cnpjReceiver, dateEmiss,
				ambient, modo, message, XML_STATE.ERRO_VALIDACAO);
	}

	public void addXml(String keyXml, String xml, String uf, String cnpjSender,
			String cnpjReceiver, String dateEmiss, String ambient)
			throws Exception {

		MODO_OP modo = this.modos.get(cnpjSender).get(ambient);
		this.persistXml(keyXml, xml, uf, cnpjSender, cnpjReceiver, dateEmiss,
				ambient, modo, null, XML_STATE.GERADA);

		if (modo.equals(SystemProperties.MODO_OP.CONTINGENCE)) {
			MyLogger.getLog().info("In contingence: " + keyXml);
			MyLogger.getLog().info("modo: " + modo);
			// this.printer.printXml(keyXml, xml, modo);
		}

		if (modo.equals(SystemProperties.MODO_OP.CONTINGENCE_DPEC)) {
			// this.printer.printXml(keyXml, xml, modo);
		}

		if (modo.equals(SystemProperties.MODO_OP.NORMAL)) {
			XMLBuffer buffer = this.getBufferReference(cnpjSender, ambient);
			buffer.addXml(keyXml, xml);
		}

	}

	private void checkTimeOuts() {
		for (String cnpj : this.buffers.keySet()) {
			Hashtable<String, XMLBuffer> ambientss = this.buffers.get(cnpj);
			for (String ambient : ambientss.keySet()) {
				MODO_OP modo = this.modos.get(cnpj).get(ambient);
				if (modo.equals(SystemProperties.MODO_OP.NORMAL)) {
					Hashtable<String, XMLBuffer> table = this.buffers.get(cnpj);

					for (XMLBuffer buffer : table.values()) {
						long timeLapsed = Calendar.getInstance()
								.getTimeInMillis()
								- buffer.getLastTime();
						if (timeLapsed > buffer.getTimeout()
								&& !buffer.isBufferEmpty()) {
							buffer.sendSet();
						}
					}

				}
			}
		}
	}

	public void setParameters(String cnpj, String ambient,
			MessengerConfig msnConfig) throws RemoteException {
		XMLBuffer buffer = this.getBufferReference(cnpj, ambient);

		buffer.setSizeFile(msnConfig.getSizeFile());
		buffer.setSizeSet(msnConfig.getSizeFile());
		buffer.setTimeout(msnConfig.getTimeout());

		MyLogger.getLog().info("Update set size: " + buffer.getSizeSet());
		MyLogger.getLog().info("Update file size: " + buffer.getSizeFile());
		MyLogger.getLog().info("Update time-out: " + buffer.getTimeout());

	}

	public MODO_OP getModo(String cnpj, String ambient) {
		return this.modos.get(cnpj).get(ambient);
	}

	@Override
	public void run() {
		while (true) {
			this.checkTimeOuts();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
