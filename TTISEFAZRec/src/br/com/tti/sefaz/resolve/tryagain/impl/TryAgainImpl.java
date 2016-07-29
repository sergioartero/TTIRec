package br.com.tti.sefaz.resolve.tryagain.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.persistence.Query;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.TTIState;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.resolve.tryagain.TryAgain;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.util.Locator;

public class TryAgainImpl implements TryAgain {

	private ManagerInterface manager;

	private Vector<TTIState> dontSend;
	private DAO<XMLData> daoData;

	public TryAgainImpl() {
		this.manager = Locator.getManagerReference();
		this.daoData = DaoFactory.createDAO(XMLData.class);
		this.dontSend = States.getINSTANCE().getDontReSend();
	}

	private void processXML(XMLData xmlData) {
		TTIState state = new TTIState(xmlData.getState(), 1);
		if (!this.dontSend.contains(state)) {
			try {
				this.manager.sendXml(xmlData.getKeyXml(), xmlData
						.getXmlString(), xmlData.getCnpjEmit(), xmlData
						.getCnpjDest(), xmlData.getDateEmiss(), null, xmlData
						.getUf(), xmlData.getAmbient(), false, false);
			} catch (Exception e) {
				MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void synchronizeXmlWithSefaz(String keyXml) throws RemoteException {
		XMLData xmlData = null;
		try {
			xmlData = this.daoData.findEntity(keyXml);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			return;
		}

		if (xmlData != null) {
			this.manager.makeQueryState(xmlData.getUf(), xmlData.getAmbient(),
					keyXml, true, false);
			try {
				xmlData = this.daoData.findEntity(keyXml);
				if (xmlData != null)
					this.processXML(xmlData);

			} catch (Exception e) {
				MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void synchronizeXmlWithSefaz(String cnpj, String ambient, String uf)
			throws RemoteException {
		String sql = "select x from XMLData as x where x.cnpjEmit =:cnpj and x.ambient = :ambient";
		Query query = this.daoData.createQuery(sql);
		query.setParameter("cnpj", cnpj);
		query.setParameter("ambient", ambient);

		List<XMLData> xmls = null;
		try {
			xmls = query.getResultList();
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		if (xmls != null) {
			for (XMLData xml : xmls) {
				this.processXML(xml);
			}
		}
	}
}
