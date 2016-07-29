package br.com.tti.sefaz.persistence.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.taragona.nfe.persistence.Propriedades;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.PFXFile;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;

import oracle.toplink.essentials.exceptions.DatabaseException;
import oracle.toplink.essentials.exceptions.ExceptionHandler;

import oracle.toplink.essentials.internal.ejb.cmp3.base.EntityManagerImpl;
import oracle.toplink.essentials.queryframework.ReadQuery;
import oracle.toplink.essentials.sessions.Session;
import oracle.toplink.essentials.tools.sessionmanagement.SessionManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class XMLDaoJPA<T> implements DAO<T> {

	private Class classs;

	private EntityManager em;

	private void addHandlerException() {
		/*
		 * MyLogger.getLog().info(
		 * "**************************************************:" +
		 * this.toString());
		 */
		Hashtable sessions = SessionManager.getManager().getSessions();

		for (Object key : sessions.keySet()) {
			Session session = (Session) sessions.get(key);
			// MyLogger.getLog().info(session.getClass().getCanonicalName());
			session.setExceptionHandler(new ExceptionHandler() {

				@Override
				public Object handleException(RuntimeException exception) {
					MyLogger.getLog().info(
							"**************** catch exception! type:"
									+ exception.getClass() + " dao: "
									+ this.toString());

					if (exception instanceof NullPointerException) {

						NullPointerException npe = (NullPointerException) exception;
						em = MyEntityManagerFactory.createEntityManager();
						MyLogger.getLog().info("creating another em:");
						// return Void.class;

					}

					if (exception instanceof DatabaseException) {
						// exception.printStackTrace();

						DatabaseException dbex = (DatabaseException) exception;

						MyLogger.getLog().info(
								"**************** catch exception DatabaseException! type:"
										+ dbex.getInternalException()
												.getClass() + " dao: "
										+ this.toString() + " query class:"
										+ dbex.getCall());

						MyLogger.getLog().info(
								"code exception: ->"
										+ ((SQLException) dbex
												.getInternalException())
												.getErrorCode());

						MyLogger.getLog().info(
								"class internal exception:"
										+ dbex.getInternalException()
												.getClass().getCanonicalName());

						if ((dbex.getInternalException() instanceof SQLException)

								&& (((SQLException) dbex.getInternalException())
										.getErrorCode() == 0)

						) {

							for (int i = 0; i < 10; i++) {
								MyLogger.getLog().info("trying" + i);
								try {
									dbex.getSession()
											.commitExternalTransaction();

									dbex.getAccessor().reestablishConnection(
											dbex.getSession());
									Vector<DAO> daos = DaoFactory.getDaos();
									for (DAO dao : daos) {
										if (dao instanceof XMLDaoJPA) {
											if (!dao.equals(this)) {
												/*
												 * MyLogger.getLog() .info(
												 * "------> %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5"
												 * );
												 */

												XMLDaoJPA jpadao = (XMLDaoJPA) dao;
												jpadao.updateEm(MyEntityManagerFactory
														.createEntityManager(DaoFactory.dbconfig));
											}
										}
									}
									MyLogger.getLog().info(
											"creating em for dao:"
													+ this.toString());
									em = MyEntityManagerFactory
											.createEntityManager(DaoFactory.dbconfig);
									break;
								} catch (Exception e) {
									MyLogger.getLog().info(
											"nao consegui!!"
													+ e.getLocalizedMessage());
								}
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}

							if (dbex.getQuery() != null) {
								MyLogger.getLog().info(
										"transaction class:"
												+ dbex.getQuery().getClass()
														.getCanonicalName());
							}
							//
							if (dbex.getQuery() != null
									&& dbex.getQuery() instanceof ReadQuery) {
								MyLogger.getLog().info("sending return 1!!");
								return dbex.getSession().executeQuery(
										dbex.getQuery(),
										dbex.getQuery().getTranslationRow());
							}

							MyLogger.getLog().info(
									"sending exception 1!!:"
											+ exception.getLocalizedMessage());
							throw exception;
						}
					}
					MyLogger.getLog().info(
							"sending exception 2!!:"
									+ exception.getLocalizedMessage());
					throw exception;
				}
			});
			// MyLogger.getLog().info("has??" + session.hasExceptionHandler());
		}
	}

	public XMLDaoJPA(Class classs, EntityManager em) {
		this.classs = classs;
		this.em = em;
		// this.addHandlerException();
	}

	@Override
	public boolean reOpenEM() {
		try {
			if (this.em == null || !this.em.isOpen()) {
				this.em = MyEntityManagerFactory
						.createEntityManager(DaoFactory.dbconfig);
			}
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			return false;
		}

		/*
		 * try { this.em.createNativeQuery("SELECT 1").getResultList(); } catch
		 * (Throwable e) { MyLogger.getLog().info(e.getLocalizedMessage()); try
		 * { MyLogger.getLog().info(
		 * "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		 * MyLogger.getLog().info(
		 * "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"); this.em =
		 * MyEntityManagerFactory .createEntityManager(DaoFactory.dbconfig);
		 * MyLogger.getLog().info( "%%%%%%%%%% recreando entity manager: " +
		 * this.em); MyLogger.getLog().info(
		 * "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"); } catch
		 * (Throwable e1) { MyLogger.getLog().log(Level.INFO,
		 * e.getLocalizedMessage(), e1); return false; }
		 * 
		 * return false; }
		 */

		MyLogger.getLog().info("testing connection!");
		try {
			PFXFile pfx = this.em.find(PFXFile.class, "mypfx");
			if (pfx == null) {
				pfx = new PFXFile();
				pfx.setPfx("mypfx");

				EntityTransaction t = this.em.getTransaction();
				t.begin();
				this.em.persist(pfx);
				t.commit();

			} else {

				EntityTransaction t = this.em.getTransaction();
				pfx.setPassword(UUID.randomUUID().toString());

				t.begin();
				this.em.merge(pfx);
				t.commit();
			}

		} catch (Exception e) {
			MyLogger.getLog().info(e.getLocalizedMessage());
			try {
				MyLogger.getLog().info(
						"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				MyLogger.getLog().info(
						"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				this.em = MyEntityManagerFactory
						.createEntityManager(DaoFactory.dbconfig);
				MyLogger.getLog().info(
						"%%%%%%%%%% recreando entity manager 2: " + this.em);
				MyLogger.getLog().info(
						"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			} catch (Throwable e1) {
				MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e1);
				return false;
			}

			return false;
		}

		MyLogger.getLog().info("end testing connection!");

		return true;
	}

	public void updateEm(EntityManager em) {
		this.em = em;
	}

	public Class getClasss() {
		return classs;
	}

	public void setClasss(Class classs) {
		this.classs = classs;
	}

	public void changeState(String keyCte, XML_STATE state, Date date) {

	}

	@Override
	public void clean() {
		this.em.clear();
	}

	@Override
	public List<T> executeQuery(String sql) {
		List result = null;
		try {
			result = this.em.createQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public T findEntity(Object id) {
		T entity = (T) this.em.find(this.classs, id);
		if (entity != null)
			this.em.refresh(entity);
		return entity;
	}

	@Override
	public T findEntity(Object id, String column) {
		// TODO Auto-generated method stub
		String sql = "select o from " + this.classs.getSimpleName()
				+ " as o where " + column + " = '" + id.toString() + "'";
		List<T> result = this.executeQuery(sql);
		if (result.size() == 0)
			return null;
		return (T) result.get(0);
	}

	@Override
	synchronized public void flush() {
		this.reOpenEM();

		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			this.em.flush();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		}

	}

	@Override
	public List<T> listAllElements() {
		String sql = "select o from " + this.classs.getSimpleName() + " as o ";
		return this.executeQuery(sql);
	}

	@Override
	public void reLoadEntity(T entity) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			this.em.refresh(entity);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		}
	}

	@Override
	synchronized public void removeEntity(T entity) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			this.em.remove(entity);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		}
	}

	@Override
	synchronized public void updateEntity(T entity) {
		this.reOpenEM();

		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			this.em.merge(entity);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		}
	}

	@Override
	synchronized public void saveEntity(T entity) {

		this.reOpenEM();

		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			this.em.persist(entity);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		}

	}

	@Override
	public Query createQuery(String sql) {
		return this.em.createQuery(sql);
	}

	@Override
	public Query createNativerQuery(String sql, Hashtable<String, Object> props) {
		if (props != null && props.containsKey("class")) {
			return this.em.createNativeQuery(sql, (Class) props.get("class"));
		}
		return this.em.createNativeQuery(sql);
	}

	@Override
	public void close() {
		try {
			if (this.em.isOpen()) {
				this.em.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		this.close();
	}

}
