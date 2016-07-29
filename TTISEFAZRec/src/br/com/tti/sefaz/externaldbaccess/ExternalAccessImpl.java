package br.com.tti.sefaz.externaldbaccess;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import nfeimpressao.TaragonaInt;
import br.com.tti.sefaz.buffer.DBAccess;
import br.com.tti.sefaz.buffer.local.DBAccessImpl;
import br.com.tti.sefaz.persistence.LogXML;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.printer.XMLPrinter;
import br.com.tti.sefaz.printer.impl.LaserPrinter;
import br.com.tti.sefaz.remote.events.TTIEvent;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.users.Profile;
import br.com.tti.sefaz.users.User;
import br.com.tti.sefaz.users.UserManager;
import br.com.tti.sefaz.users.impl.UserManagerImpl;

public class ExternalAccessImpl implements ExternalDBAccess {

	private DBAccess dba;
	private UserManager user;
	private XMLPrinter printer;

	public ExternalAccessImpl() {
		this.dba = new DBAccessImpl();
		this.user = new UserManagerImpl();
		this.printer = new LaserPrinter();
	}

	@Override
	public boolean checkPFXPassword(String pfx, String password)
			throws RemoteException {
		return this.dba.checkPFXPassword(pfx, password);
	}

	@Override
	public Vector<LogXML> findLog(String keyXml) throws RemoteException {
		return this.dba.findLog(keyXml);
	}

	@Override
	public String findXML(String keyXml) throws RemoteException {
		return this.dba.findXML(keyXml);
	}

	@Override
	public Vector<XMLData> findXMLData(Date d1, Date d2, Vector<String> cnpjs,
			String ambient, Hashtable prop) throws RemoteException {
		return this.findXMLData(d1, d2, cnpjs, ambient, prop);
	}

	@Override
	public Vector<XMLData> findXMLData(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		return this.findXMLData(d1, d2, cnpjs, ambient, prop);
	}

	@Override
	public Vector<TTIEvent> findXMLEvent(Date d1, Date d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		return this.dba.findXMLEvent(d1, d2, cnpjs, ambient, prop);
	}

	@Override
	public Vector<TTIEvent> findXMLEvent(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		return this.dba.findXMLEvent(d1, d2, cnpjs, ambient, prop);
	}

	@Override
	public void changePassword(String login, String newPassword)
			throws RemoteException {
		this.user.changePassword(login, newPassword);
	}

	@Override
	public User getUser(String login, String password) throws RemoteException {
		return this.user.getUser(login, password);
	}

	@Override
	public void register(User user, Profile p) throws RemoteException {
		this.user.register(user, p);
	}

	@Override
	public void printXml(String keyXml, String xml, MODO_OP modo)
			throws RemoteException {
		this.printer.printXml(keyXml, xml, modo);
	}

	@Override
	public void rePrintXml(String keyXml, MODO_OP modo) throws RemoteException {
		this.printer.rePrintXml(keyXml, modo);
	}

	@Override
	public void registrarImpressao(TaragonaInt imp) throws RemoteException {
		throw new RemoteException("method dont implemented yet");
	}

}
