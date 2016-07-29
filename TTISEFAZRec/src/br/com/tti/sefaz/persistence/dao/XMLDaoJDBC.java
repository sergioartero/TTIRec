package br.com.tti.sefaz.persistence.dao;

import java.util.Hashtable;
import java.util.List;

import javax.persistence.Query;

public class XMLDaoJDBC<T> implements DAO<T> {

	@Override
	public void clean() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean reOpenEM() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<T> executeQuery(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findEntity(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public Class getClasss() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> listAllElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reLoadEntity(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEntity(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateEntity(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveEntity(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public T findEntity(Object id, String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createQuery(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createNativerQuery(String sql, Hashtable<String, Object> props) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
