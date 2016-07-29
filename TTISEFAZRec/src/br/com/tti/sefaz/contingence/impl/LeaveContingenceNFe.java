package br.com.tti.sefaz.contingence.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;

import br.com.tti.sefaz.contingence.AbstractContingenceStrategy;
import br.com.tti.sefaz.contingence.LeaveContingenceStrategy;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;

public class LeaveContingenceNFe extends AbstractContingenceStrategy implements
		LeaveContingenceStrategy {

	public LeaveContingenceNFe(ManagerInterface manager, String cnpj,
			String ambient) {
		super(manager);
		this.cnpj = cnpj;
		this.ambient = ambient;
	}

	private void checkContingenceStateXml() {
		List<XMLData> xmls = this.findXMLDataWithStates(
				this.toCheckContingenceStates, MODO_OP.CONTINGENCE);

		for (XMLData xml : xmls) {
			try {
				this.manager.makeQueryState(xml.getUf(), xml.getAmbient(), xml
						.getKeyXml(), true, false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (XMLData xml : xmls) {
			this.daoXml.reLoadEntity(xml);
			if (xml.getState().equals(XML_STATE.AUTORIZADA)) {
				try {
					this.manager.cancelXml(xml.getCnpjEmit(), xml.getUf(), xml
							.getAmbient(), xml.getKeyXml(), xml
							.getNAutorizedProtocol(), "SAINDO DE CONTINGENCIA "
							+ xml.getKeyXml(), true);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			if (xml.getState().equals(XML_STATE.REJEITADA)) {
				try {
					this.manager.inutXml(xml.getCnpjEmit(), xml.getUf(), xml
							.getAmbient(), xml.getKeyXml(),
							"SAINDO CONTINGENCIA " + xml.getKeyXml(), true);
				} catch (Exception e) {
					MyLogger.getLog().log(Level.SEVERE,
							e.getLocalizedMessage(), e);
				}
			}
		}

	}

	private void sendXml() {
		List<XMLData> xmls = this.findXMLDataWithStates(this.toSendStates,
				MODO_OP.CONTINGENCE);
		for (XMLData xml : xmls) {
			this.daoXml.reLoadEntity(xml);
			try {
				this.manager.sendXml(xml.getKeyXml(), xml.getXmlString(), xml
						.getCnpjEmit(), xml.getCnpjDest(), xml.getDateEmiss(),
						"", "", xml.getAmbient(), false, false);
			} catch (Exception e) {
				MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
	}

	private void cancelXml() {
		List<XMLData> xmls = this.findXMLDataWithStates(this.toCancelStates,
				null);
		for (XMLData data : xmls) {
			try {
				this.manager.cancelXml(data.getCnpjEmit(), data.getUf(), data
						.getAmbient(), data.getKeyXml(), data
						.getNAutorizedProtocol(), "CANCELADAS EM CONTINGENCIA "
						+ data.getKeyXml(), true);
			} catch (Exception e) {
				MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void leaveContingence() {
		this.checkContingenceStateXml();
		this.sendXml();
		// this.cancelXml();
	}

	@Override
	public void run() {
		this.leaveContingence();
	}

}
