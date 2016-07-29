package br.com.tti.sefaz.persistence.dao;

import java.util.Vector;

import javax.persistence.EntityManager;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.MainParameters;

public class DaoFactory {

	public static enum TYPE_DAO {
		JPA_DAO, JDBC_DAO
	};

	private static TYPE_DAO type = TYPE_DAO.JPA_DAO;

	public static DBConfig dbconfig = null;
	
	public static Vector<DAO> daos = new Vector<DAO>();

	public static <T> DAO<T> createDAO(Class<T> c) {
		DAO<T> dao = null;
		EntityManager em = null;
		if (MainParameters.isLocaldb()) {
			if (dbconfig == null) {
				XMLConfigSystem config = new XMLConfigSystem(
						MainParameters.getXml());
				config.make();
				dbconfig = config.getDbConfig();
			}
			em = MyEntityManagerFactory.createEntityManager(dbconfig);
		} else {
			em = MyEntityManagerFactory.createEntityManager();
		}

		if (em == null) {
			MyLogger.getLog().info("Entity Manager dont founded");
			System.exit(1);
		}

		if (type.equals(DaoFactory.TYPE_DAO.JPA_DAO)) {
			dao = new XMLDaoJPA<T>(c, em);
			daos.add(dao);
		}

		if (type.equals(DaoFactory.TYPE_DAO.JDBC_DAO)) {
			dao = new XMLDaoJDBC<T>();
			daos.add(dao);
		}

		return dao;
	}
	

	public static Vector<DAO> getDaos() {
		return daos;
	}

	public static <T> DAO<T> createDAO(Class<T> c, DBConfig config) {
		DAO<T> dao = null;

		EntityManager em = MyEntityManagerFactory.createEntityManager(config);

		if (em == null) {
			MyLogger.getLog().info("Entity Manager dont founded");
			System.exit(1);
		}

		if (type.equals(DaoFactory.TYPE_DAO.JPA_DAO)) {
			dao = new XMLDaoJPA<T>(c, em);
		}

		if (type.equals(DaoFactory.TYPE_DAO.JDBC_DAO)) {
			dao = new XMLDaoJDBC<T>();
		}

		return dao;
	}

	public static void main(String[] args) {
		DAO<XMLData> a = DaoFactory.createDAO(XMLData.class);
		System.out.println(a.getClasss());
	}

	public static TYPE_DAO getType() {
		return type;
	}

	public static void setType(TYPE_DAO type) {
		DaoFactory.type = type;
	}
}
