package br.com.tti.sefaz.querier;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.SefazState;

public class QuerierController {

	private XMLQueryThreadFactory factory;
	private ManagerInterface manager;

	public QuerierController(ManagerInterface manager) {
		this.manager = manager;
		this.factory = XMLQueryThreadFactory.getFactory(this.manager);
	}

	public String makeQueryXML(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws Exception {
		XMLQueryThread query = this.factory.createQueryThread(uf, ambient,
				keyXml, persist);
		if (assyn) {
			Thread t = new Thread(query);
			t.start();
			return null;
		} else {
			return query.sendQuery();
		}
	}

	public SefazState makeQueryState(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws Exception {
		XMLQueryThread query = this.factory.createQueryThread(uf, ambient,
				keyXml, persist);
		if (assyn) {
			Thread t = new Thread(query);
			t.start();
			return null;
		} else {
			query.sendQuery();
			return query.getSefazState();
		}
	}
}
