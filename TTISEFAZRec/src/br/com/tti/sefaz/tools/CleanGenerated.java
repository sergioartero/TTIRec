package br.com.tti.sefaz.tools;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.persistence.dao.MyEntityManagerFactory;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.util.MainParameters;

public class CleanGenerated {
	public static void main(String[] args) {
		MainParameters.processArguments(args);
		XMLConfigSystem config = new XMLConfigSystem(MainParameters.getXml());
		config.make();
		DBConfig dbConfig = config.getDbConfig();
		EntityManager em = MyEntityManagerFactory.createEntityManager(dbConfig);

		Query q = em
				.createQuery("select x from XMLData as x where x.state = :state");
		q.setParameter("state", XML_STATE.GERADA);

		List<XMLData> list = q.getResultList();

		for (XMLData data : list) {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(data);
			t.commit();
		}

	}
}
