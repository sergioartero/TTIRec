package br.com.tti.sefaz.messenger;

import java.util.Calendar;
import java.util.Vector;

import br.com.tti.sefaz.cache.SetDataCache;
import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;

public class XMLBuffer {

	private String cnpj;
	private String uf;
	private String ambient;
	private Vector<String> xmls;
	private Vector<String> keyXmls;

	private int currentSizeFile;
	private int sizeSet;
	private int sizeFile;
	private long lastTime;
	private long timeout;

	private ManagerInterface manager;
	private XMLDataCache cacheXml;
	private SetDataCache cacheSet;

	private XMLMessageFactory messageFactory;

	public XMLBuffer(ManagerInterface manager,
			XMLMessageFactory messageFactory, XMLDataCache cacheXml,
			SetDataCache cacheSet) {
		this.manager = manager;

		this.cacheXml = cacheXml;
		this.cacheSet = cacheSet;
		this.messageFactory = messageFactory;

		this.xmls = new Vector<String>();
		this.keyXmls = new Vector<String>();
	}

	synchronized public void addXml(String keyXml, String xml) {
		this.lastTime = Calendar.getInstance().getTimeInMillis();

		if ((xmls.size() + 1) <= sizeSet
				&& ((currentSizeFile + keyXml.length()) < sizeFile)) {
			this.xmls.add(xml);
			this.keyXmls.add(keyXml);
			this.currentSizeFile += xml.length();
		} else {
			SetSenderThread sender = new SetSenderThread(this.manager,
					this.messageFactory, this.cacheXml, this.cacheSet,
					this.xmls, this.keyXmls, this.cnpj, this.uf, this.ambient);
			Thread t = new Thread(sender);
			t.start();

			this.xmls.clear();
			this.keyXmls.clear();

			this.currentSizeFile = SystemParameters.SET_EMPTY_SIZE
					+ xml.length();
			this.xmls.add(xml);
			this.keyXmls.add(keyXml);
		}
	}

	synchronized public void sendSet() {
		SetSenderThread sender = new SetSenderThread(this.manager,
				this.messageFactory, this.cacheXml, this.cacheSet, this.xmls,
				this.keyXmls, this.cnpj, this.uf, this.ambient);
		Thread t = new Thread(sender);
		t.start();
		this.xmls.clear();
		this.keyXmls.clear();

	}

	public long getLastTime() {
		return lastTime;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAmbient() {
		return ambient;
	}

	public void setAmbient(String ambient) {
		this.ambient = ambient;
	}

	public int getSizeSet() {
		return sizeSet;
	}

	public void setSizeSet(int sizeSet) {
		this.sizeSet = sizeSet;
	}

	public int getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(int sizeFile) {
		this.sizeFile = sizeFile;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public int getCurrentSizeFile() {
		return currentSizeFile;
	}

	public void setCurrentSizeFile(int currentSizeFile) {
		this.currentSizeFile = currentSizeFile;
	}

	public boolean isBufferEmpty() {
		return this.xmls.isEmpty();
	}

}
