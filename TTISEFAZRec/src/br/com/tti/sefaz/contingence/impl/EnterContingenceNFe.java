package br.com.tti.sefaz.contingence.impl;

import java.util.List;

import br.com.tti.sefaz.contingence.AbstractContingenceStrategy;
import br.com.tti.sefaz.contingence.EnterContingenceStrategy;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;

public class EnterContingenceNFe extends AbstractContingenceStrategy implements
		EnterContingenceStrategy {

	public EnterContingenceNFe(ManagerInterface manager, String cnpj,
			String ambient) {
		super(manager);
		this.manager = manager;
		this.daoXml = DaoFactory.createDAO(XMLData.class);
		this.cnpj = cnpj;
		this.ambient = ambient;
	}

	private void setUpContingece() {
		List<XMLData> xmls = this.findXMLDataWithStates(
				this.toContingenceStates, MODO_OP.NORMAL);
		for (XMLData xml : xmls) {
			xml.setModo(MODO_OP.CONTINGENCE);
			this.daoXml.updateEntity(xml);
		}
	}

	@Override
	public void enterContingence() {
		this.setUpContingece();
	}

	@Override
	public void run() {
		this.enterContingence();
	}

}
