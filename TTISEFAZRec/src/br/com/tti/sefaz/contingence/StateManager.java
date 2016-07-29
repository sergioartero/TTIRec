package br.com.tti.sefaz.contingence;

import javax.persistence.Query;

import br.com.tti.sefaz.persistence.StateSystem;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;

public class StateManager {

	private DAO<StateSystem> daoState;
	private Query q;

	public StateManager() {
		this.daoState = DaoFactory.createDAO(StateSystem.class);
		this.q = this.daoState
				.createQuery("select s from StateSystem as s where s.prop=:prop and s.cnpj=:cnpj and s.ambient=:ambient");

	}

	public MODO_OP getModo(String cnpj, String ambient) {
		q.setParameter("prop", "MODO_OP");
		q.setParameter("cnpj", cnpj);
		q.setParameter("ambient", ambient);

		StateSystem s = null;
		try {
			s = (StateSystem) q.getSingleResult();
		} catch (Exception e) {
			return null;
		}

		MODO_OP modo = null;

		try {
			modo = MODO_OP.valueOf(s.getValue());
		} catch (Exception e) {
		}
		return modo;
	}

	public void setModo(String cnpj, String ambient, MODO_OP modo) {
		q.setParameter("prop", "MODO_OP");
		q.setParameter("cnpj", cnpj);
		q.setParameter("ambient", ambient);

		StateSystem s = null;
	
		try {
			s = (StateSystem) q.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (s != null) {
			s.setValue(modo.toString());

			this.daoState.updateEntity(s);
		} else {
			s = new StateSystem();
			s.setCnpj(cnpj);
			s.setAmbient(ambient);
			s.setValue(modo.toString());
			s.setProp("MODO_OP");

			this.daoState.saveEntity(s);
		}
	}
}
