package br.com.tti.sefaz.persistence.dao;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.util.Locator;

public class MyEntityManagerFactory {

	private static MyEntityManagerFactory INSTANCE = null;

	public static EntityManager createEntityManagerHibernate() {
		ManagerInterface manager = Locator.getManagerReference();
		DBConfig dbConfig = null;
		try {
			dbConfig = manager.getDBConfig();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("hibernate.connection.password", dbConfig.getPassword());
		h.put("hibernate.connection.username", dbConfig.getUser());
		h.put("hibernate.connection.url", dbConfig.getUrl());
		h.put("hibernate.connection.driver_class", dbConfig.getDriver());
		h.put("hibernate.hbm2ddl.auto", "update");

		EntityManagerFactory fact = Persistence.createEntityManagerFactory(
				"TTIPUHibernate", h);
		return fact.createEntityManager();
	}

	public static EntityManager createEntityManager() {
		ManagerInterface manager = Locator.getManagerReference();
		DBConfig dbConfig = null;
		try {
			dbConfig = manager.getDBConfig();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		HashMap<String, String> h = new HashMap<String, String>();
		h.put("toplink.jdbc.password", dbConfig.getPassword());
		h.put("toplink.jdbc.user", dbConfig.getUser());
		h.put("toplink.jdbc.url", dbConfig.getUrl());
		h.put("toplink.jdbc.driver", dbConfig.getDriver());
		h.put("toplink.ddl-generation", dbConfig.getProp());

		EntityManagerFactory fact = Persistence.createEntityManagerFactory(
				"TTIPU", h);
		return fact.createEntityManager();
	}

	public static EntityManager createEntityManager(DBConfig dbConfig) {
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("toplink.jdbc.password", dbConfig.getPassword());
		h.put("toplink.jdbc.user", dbConfig.getUser());
		h.put("toplink.jdbc.url", dbConfig.getUrl());
		h.put("toplink.jdbc.driver", dbConfig.getDriver());
		h.put("toplink.ddl-generation", dbConfig.getProp());

		EntityManagerFactory fact = Persistence.createEntityManagerFactory(
				"TTIPU", h);
		return fact.createEntityManager();
	}

	public static EntityManager createEntityManagerHibernate(DBConfig dbConfig) {
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("hibernate.connection.password", dbConfig.getPassword());
		h.put("hibernate.connection.username", dbConfig.getUser());
		h.put("hibernate.connection.url", dbConfig.getUrl());
		h.put("hibernate.connection.driver_class", dbConfig.getDriver());
		h.put("hibernate.hbm2ddl.auto", dbConfig.getProp());

		EntityManagerFactory fact = Persistence.createEntityManagerFactory(
				"TTIPUHibernate", h);
		return fact.createEntityManager();
	}
}
