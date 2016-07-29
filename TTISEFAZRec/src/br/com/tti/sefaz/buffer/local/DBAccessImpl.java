package br.com.tti.sefaz.buffer.local;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.persistence.Query;

import br.com.tti.sefaz.buffer.DBAccess;
import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.listeners.DataListener;
import br.com.tti.sefaz.listeners.DataNotifier;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.LogXML;
import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.remote.events.ChangeStateXmlEvent;
import br.com.tti.sefaz.remote.events.TTIEvent;
import br.com.tti.sefaz.systemconfig.SystemProperties;

public class DBAccessImpl implements DBAccess, DataNotifier {

	private DAO<LogXML> daoLog;
	private DAO<XMLData> daoXml;
	private Vector<DataListener> listener;

	public DBAccessImpl() {
		this.daoLog = DaoFactory.createDAO(LogXML.class);
		this.daoXml = DaoFactory.createDAO(XMLData.class);
		this.listener = new Vector<DataListener>();
	}

	public DBAccessImpl(DBConfig config) {
		this.daoXml = DaoFactory.createDAO(XMLData.class, config);
		this.daoLog = DaoFactory.createDAO(LogXML.class, config);

	}

	private String formXMLQuery(int d, int n) {
		String sql = null;

		if (d == 2)
			sql = "select x from XMLData as x where (x.dateCreate >= :d1 and x.dateCreate <= :d2) and";

		if (d == 1)
			sql = "select x from XMLData as x where (x.dateCreate >= :d1) and";

		if (d == 0)
			sql = "select x from XMLData as x where ";

		String all = "";
		String ss = "x.cnpjEmit = :c";
		for (int i = 0; i < n; i++) {
			all = all + ss + i + " or ";
		}
		all = all.substring(0, all.length() - 3);
		return sql + " (" + all
				+ ") and (x.ambient = :ambient) order by x.dateCreate desc";
	}

	private void fillQueryCNPJ(Query query, Vector<String> cnpjs) {
		int counter = 0;
		for (String cnpj : cnpjs) {
			query.setParameter("c" + counter, cnpj);
			counter++;
		}
	}

	private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	private List<XMLData> findXMLDateString(String ds1, String ds2,
			Vector<String> cnpjs, String ambient) {
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = f.parse(ds1 + " 00:01");
			if (ds2 != null)
				d2 = f.parse(ds2 + " 23:59");
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		return this.findXML(d1, d2, cnpjs, ambient);
	}

	private List<XMLData> findXML(Date d1, Date d2, Vector<String> cnpjs,
			String ambient) {

		Query query = null;
		String sql = "";
		if (d1 != null && d2 != null) {
			sql = this.formXMLQuery(2, cnpjs.size());

			MyLogger.getLog().finest("making sql query: " + sql);

			query = this.daoXml.createQuery(sql);
			this.fillQueryCNPJ(query, cnpjs);
			query.setParameter("ambient", ambient);
			query.setParameter("d1", d1);
			query.setParameter("d2", d2);

		}

		if (d1 != null && d2 == null) {
			sql = this.formXMLQuery(1, cnpjs.size());

			MyLogger.getLog().finest("making sql query: " + sql);

			query = this.daoXml.createQuery(sql);
			this.fillQueryCNPJ(query, cnpjs);
			query.setParameter("ambient", ambient);
			query.setParameter("d1", d1);
		}

		if (d1 == null && d2 == null) {
			sql = this.formXMLQuery(0, cnpjs.size());

			MyLogger.getLog().finest("making sql query: " + sql);

			query = this.daoXml.createQuery(sql);
			this.fillQueryCNPJ(query, cnpjs);
			query.setParameter("ambient", ambient);
		}

		List<XMLData> result = query.getResultList();

		return result;
	}

	@Override
	public void addListener(DataListener listener) throws RemoteException {
		this.listener.add(listener);
	}

	@Override
	public void notifyData(XMLData data) throws RemoteException {
		for (DataListener listener : this.listener) {
			listener.process(data);
		}
	}

	@Override
	public void notifyData(SetData data) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<XMLData> findXMLData(Date d1, Date d2, Vector<String> cnpjs,
			String ambient, Hashtable prop) throws RemoteException {
		Vector<XMLData> data = new Vector<XMLData>();
		List<XMLData> result = this.findXML(d1, d2, cnpjs, ambient);
		for (XMLData d : result) {
			data.add(d);
		}
		return data;
	}

	@Override
	public Vector<TTIEvent> findXMLEvent(Date d1, Date d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		Vector<TTIEvent> data = new Vector<TTIEvent>();
		List<XMLData> result = this.findXML(d1, d2, cnpjs, ambient);
		for (XMLData d : result) {
			data.add(ChangeStateXmlEvent.createEvent(d));
		}
		return data;
	}

	@Override
	public Vector<TTIEvent> findXMLEvent(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		Vector<TTIEvent> data = new Vector<TTIEvent>();
		List<XMLData> result = this.findXMLDateString(d1, d2, cnpjs, ambient);
		for (XMLData d : result) {
			data.add(ChangeStateXmlEvent.createEvent(d));
		}
		return data;
	}

	@Override
	public Vector<XMLData> findXMLData(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		List<XMLData> result = this.findXMLDateString(d1, d2, cnpjs, ambient);
		Vector<XMLData> data = new Vector<XMLData>(result);
		return data;
	}

	@Override
	public String findXML(String keyXml) throws RemoteException {
		XMLData xml = this.daoXml.findEntity(keyXml);
		if (xml == null) {
			MyLogger.getLog().info("Xml " + keyXml + " not founded!");
			throw new MySenderException("Xml " + keyXml + " not founded!");
		}
		return xml.getXmlString();
	}

	@Override
	public Vector<LogXML> findLog(String keyXml) throws RemoteException {
		String sql = "select l from LogXML as l where l.keyXml = :key";
		Query query = this.daoLog.createQuery(sql);
		query.setParameter("key", keyXml);
		Vector<LogXML> lv = new Vector(query.getResultList());
		return lv;
	}

	@Override
	public boolean checkPFXPassword(String pfx, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {

		DBConfig config = new DBConfig();
		config.setDriver("com.mysql.jdbc.Driver");
		config.setUser("root");
		config.setPassword("crhisn@");
		config.setUrl("jdbc:mysql://localhost/tti");

		DBAccessImpl ac = new DBAccessImpl(config);
		Vector<String> cnpj = new Vector<String>();
		// cnpj.add("61531620001709");
		cnpj.add("60689692000159");
		Vector<TTIEvent> xmls = null;
		try {
			xmls = ac.findXMLEvent("01/02/2009", "01/07/2009", cnpj,
					SystemProperties.AMBIENT_HOMOLOGACAO, null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (TTIEvent data : xmls) {
			// != null)
			System.out.println(data.toString());
		}

		try {
			Vector<LogXML> log = ac
					.findLog("46052738274014363952368958840745247417132336");
			for (LogXML l : log) {
				System.out.println(l.getKeyXml());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { System.out.println(ac
		 * .findXML("46052738274014363952368958840745247417132336")); } catch
		 * (RemoteException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

}
