package br.com.tti.sefaz.persistence.dao;

import java.util.Hashtable;
import java.util.List;

import javax.persistence.Query;

public interface DAO<T> {
	public T findEntity(Object id);

	public T findEntity(Object id, String column);

	public void saveEntity(T entity);

	public void reLoadEntity(T entity);

	public void removeEntity(T entity);

	public void updateEntity(T entity);

	public List<T> executeQuery(String sql);

	public Query createQuery(String sql);

	public List<T> listAllElements();

	public void flush();

	public void clean();

	public Query createNativerQuery(String sql, Hashtable<String, Object> props);

	public Class getClasss();
	
	public void close();

	boolean reOpenEM();
}
