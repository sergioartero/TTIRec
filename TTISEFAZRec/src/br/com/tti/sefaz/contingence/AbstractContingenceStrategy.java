package br.com.tti.sefaz.contingence;

import java.util.List;
import java.util.Vector;

import javax.persistence.Query;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.TTIState;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.systemconfig.SystemProperties;

public abstract class AbstractContingenceStrategy {
	protected ManagerInterface manager;

	protected DAO<XMLData> daoXml;

	protected String cnpj;
	protected String ambient;

	protected Vector<TTIState> toContingenceStates;
	protected Vector<TTIState> toSendStates;
	protected Vector<TTIState> toCheckContingenceStates;
	protected Vector<TTIState> toCancelStates;
	protected Vector<TTIState> toInutStates;

	protected Vector<TTIState> toDPECStates;
	protected Vector<TTIState> toSendDPECStates;

	public AbstractContingenceStrategy() {
		// TODO Auto-generated constructor stub
	}

	public AbstractContingenceStrategy(ManagerInterface manager) {
		this.daoXml = DaoFactory.createDAO(XMLData.class);
		this.manager = manager;

		this.toContingenceStates = States.getINSTANCE()
				.getToContingenceStates();
		this.toSendStates = States.getINSTANCE().getToSendStates();
		this.toCheckContingenceStates = States.getINSTANCE()
				.getToCheckContingenceStates();
		this.toCancelStates = States.getINSTANCE().getToCancelStates();
		this.toInutStates = States.getINSTANCE().getToInutStates();
	}

	private String formQuery(int nStates, boolean without_mod) {
		String state = " e.state = :state";
		String all = "";
		for (int i = 0; i < nStates; i++) {
			all += state + i + " or";
		}
		System.out.println("->" + all);
		all = all.substring(0, all.length() - 2);
		String sql = null;
		if (without_mod) {
			sql = "select e from XMLData as e where (" + all
					+ ") and e.cnpjEmit = :cnpj and e.ambient = :ambient";

		} else {
			sql = "select e from XMLData as e where ("
					+ all
					+ ") and e.cnpjEmit = :cnpj and e.ambient = :ambient and e.modo = :modo";

		}

		return sql;
	}

	protected List<XMLData> findXMLDataWithStates(Vector<TTIState> states,
			SystemProperties.MODO_OP modo) {
		this.daoXml.clean();

		String sql = null;
		if (modo == null)
			sql = this.formQuery(states.size(), true);
		else
			sql = this.formQuery(states.size(), false);

		System.out.println(sql);
		Query q = this.daoXml.createQuery(sql);
		int i = 0;
		for (TTIState state : states) {
			q.setParameter("state" + i, state.getState());
			i++;
		}
		System.out.println(this.cnpj);
		System.out.println(this.ambient);
		System.out.println(modo);
		q.setParameter("cnpj", this.cnpj);
		q.setParameter("ambient", this.ambient);
		if (modo != null)
			q.setParameter("modo", modo);

		List<XMLData> result = null;
		try {
			result = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		// AbstractContingenceStrategy s = new AbstractContingenceStrategy();
		// System.out.println(s.formQuery(3, true));
	}
}
