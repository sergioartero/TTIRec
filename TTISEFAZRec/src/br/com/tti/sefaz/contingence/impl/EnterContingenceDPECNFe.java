package br.com.tti.sefaz.contingence.impl;

import java.util.List;

import br.com.tti.sefaz.contingence.AbstractContingenceStrategy;
import br.com.tti.sefaz.contingence.EnterContingenceStrategy;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;

public class EnterContingenceDPECNFe extends AbstractContingenceStrategy implements
		EnterContingenceStrategy {

	public EnterContingenceDPECNFe(ManagerInterface manager) {
		super(manager);
	}

	@Override
	public void enterContingence() {
		List<XMLData> xmls = this.findXMLDataWithStates(this.toDPECStates,
				MODO_OP.CONTINGENCE_DPEC);
		for (XMLData data : xmls) {
			// generate dpec
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
