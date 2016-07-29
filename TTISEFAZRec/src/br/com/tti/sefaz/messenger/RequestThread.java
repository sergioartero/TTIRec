package br.com.tti.sefaz.messenger;

import java.rmi.RemoteException;
import java.util.logging.Level;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.xml.XMLRepare;

public class RequestThread implements Runnable {

	private ManagerInterface manager;
	private MessengerController controller;
	private String keyXml;
	private String xml;
	private String cnpjSender;
	private String cnpjReceiver;
	private String dateEmiss;
	private String message;
	private String uf;
	private String ambient;

	private boolean sign;
	private boolean error;

	public RequestThread(ManagerInterface manager,
			MessengerController controller, String keyXml, String xml,
			String cnpjSender, String cnpjReceiver, String dateEmiss,
			String message, String uf, String ambient, boolean sign,
			boolean error) {
		super();
		this.manager = manager;
		this.controller = controller;
		this.keyXml = keyXml;
		this.xml = xml;
		this.cnpjSender = cnpjSender;
		this.cnpjReceiver = cnpjReceiver;
		this.dateEmiss = dateEmiss;
		this.message = message;
		this.uf = uf;
		this.ambient = ambient;
		this.sign = sign;
		this.error = error;
	}

	public boolean canProcess() {
		try {
			XMLData xmlData = XMLDataCache.getInstance().findData(this.keyXml);

			if (xmlData == null) {
				return true;
			}

			if (xmlData != null
					&& xmlData.getState().equals(XML_STATE.AUTORIZADA)) {
				return false;
			}

		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO,
					"XMLData dont process: " + this.keyXml, e);
			return false;
		}
		return true;
	}

	@Override
	public void run() {

		if (!this.canProcess())
			return;

		xml = XMLRepare.repareXmlForSign(xml);
		if (this.sign) {
			try {
				xml = this.manager.signForCNPJ(cnpjSender, xml,
						SystemProperties.ID_SERVICO_RECEPCAO);
			} catch (RemoteException e) {
				MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			}
			xml = XMLRepare.repareXmlAfterSign(xml);
		}
		if (this.error) {
			try {
				this.controller.addXmlError(keyXml, xml, uf, cnpjSender,
						cnpjReceiver, dateEmiss, ambient, message);
			} catch (Exception e) {
				MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			}
		} else {
			try {
				this.controller.addXml(keyXml, xml, uf, cnpjSender,
						cnpjReceiver, dateEmiss, ambient);
			} catch (Exception e) {
				MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			}
		}
	}
}
