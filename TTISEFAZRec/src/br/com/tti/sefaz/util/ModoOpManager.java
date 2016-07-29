package br.com.tti.sefaz.util;

import br.com.tti.sefaz.persistence.ModeOperationData;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;

public class ModoOpManager {
	private DAO<ModeOperationData> daoModo;

	public ModoOpManager() {
		this.daoModo = DaoFactory.createDAO(ModeOperationData.class);
	}

	public SystemProperties.MODO_OP getModo(String cnpj) {
		ModeOperationData modo = this.daoModo.findEntity(cnpj);
		if (modo != null)
			return modo.getModo();
		return null;
	}

	public void saveModo(String cnpj, MODO_OP modo) {

		ModeOperationData modoD = this.daoModo.findEntity(cnpj);
		if (modoD != null) {
			modoD.setCnpj(cnpj);
			modoD.setModo(modo);
			this.daoModo.updateEntity(modoD);
		} else {
			modoD = new ModeOperationData();
			modoD.setCnpj(cnpj);
			modoD.setModo(modo);
			this.daoModo.saveEntity(modoD);
		}

	}

}
