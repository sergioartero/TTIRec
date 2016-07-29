package br.com.tti.sefaz.tools;

import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.ModeOperationData;
import br.com.tti.sefaz.persistence.PFXFile;
import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.persistence.dao.MyEntityManagerFactory;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.util.MainParameters;

public class CreateDB {

	private XMLConfigSystem config;

	public CreateDB(String fileXml) {
		this.config = new XMLConfigSystem(fileXml);
		this.config.make();
	}

	public void createDB() {
		DBConfig dbConfig = this.config.getDbConfig();
		dbConfig.setProp("drop-and-create-tables");
		EntityManager em = MyEntityManagerFactory.createEntityManager(dbConfig);

		PFXFile f = new PFXFile();
		f.setPfx("123");
		f.setPassword("ds");

		XMLData d = new XMLData();
		d.setKeyXml("dfasf");

		SetData sd = new SetData();
		sd.setNumberSet("12");

		ModeOperationData m = new ModeOperationData();
		m.setCnpj("321");
		m.setModo(MODO_OP.NORMAL);

		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(sd);
		em.persist(f);
		em.persist(d);
		em.persist(m);
		t.commit();
	}

	public static void main(String[] args) {
		try {
			MainParameters.processArguments(args);
			CreateDB dbc = new CreateDB(MainParameters.getXml());
			dbc.createDB();
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, "Problems in table creation!", e);
		}

	}

}
