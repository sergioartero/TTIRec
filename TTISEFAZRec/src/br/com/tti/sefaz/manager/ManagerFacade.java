package br.com.tti.sefaz.manager;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import nfeimpressao.TaragonaInt;
import br.com.tti.sefaz.buffer.DBAccess;
import br.com.tti.sefaz.buffer.local.DBAccessImpl;
import br.com.tti.sefaz.callback.CallBack;
import br.com.tti.sefaz.callback.CallBackInteface;
import br.com.tti.sefaz.cancelinut.CancelInut;
import br.com.tti.sefaz.cancelinut.CancelInutInterface;
import br.com.tti.sefaz.connector.Connector;
import br.com.tti.sefaz.contingence.ContingeceInterface;
import br.com.tti.sefaz.contingence.Contingence;
import br.com.tti.sefaz.contingence.ModeOperation;
import br.com.tti.sefaz.externaldbaccess.LaunchExternalAccess;
import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.messenger.Messenger;
import br.com.tti.sefaz.messenger.MessengerInterface;
import br.com.tti.sefaz.persistence.LogXML;
import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.printer.XMLPrinter;
import br.com.tti.sefaz.printer.impl.LaserPrinter;
import br.com.tti.sefaz.querier.Querier;
import br.com.tti.sefaz.querier.QuerierInterface;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.remote.CNPJSerialize;
import br.com.tti.sefaz.remote.CallBackConfig;
import br.com.tti.sefaz.remote.CertificatesConfig;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.remote.MessengerConfig;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.remote.ServicesConfig;
import br.com.tti.sefaz.remote.events.ChangeModeOpEvent;
import br.com.tti.sefaz.remote.events.TTIEvent;
import br.com.tti.sefaz.sender.Sender;
import br.com.tti.sefaz.sender.SenderInterface;
import br.com.tti.sefaz.signer.Signer;
import br.com.tti.sefaz.signer.SignerInterface;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.users.Profile;
import br.com.tti.sefaz.users.User;
import br.com.tti.sefaz.users.UserManager;
import br.com.tti.sefaz.users.impl.UserManagerImpl;
import br.com.tti.sefaz.util.Locator;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.util.ModoOpManager;

public class ManagerFacade implements ManagerInterface {

	private XMLConfigSystem configSystem;
	private ModoOpManager modoOp;

	private Vector<SignerInterface> signers;
	private Vector<MessengerInterface> messengers;
	private Vector<CallBackInteface> callbacks;
	private Vector<SenderInterface> senders;
	private Vector<QuerierInterface> queriers;
	private Vector<CancelInutInterface> cancelInut;
	private Vector<TTIEventListener> listeners;
	private Vector<ContingeceInterface> contingence;
	private Vector<DBAccess> dbAccess;
	private Vector<UserManager> users;
	private Vector<TaragonaInt> ttiprinters;
	private Vector<XMLPrinter> localprinter;
	private Vector<Connector> connectors;

	public ManagerFacade() {
		this.configSystem = new XMLConfigSystem(MainParameters.getXml());

		this.signers = new Vector<SignerInterface>();
		this.messengers = new Vector<MessengerInterface>();
		this.callbacks = new Vector<CallBackInteface>();
		this.senders = new Vector<SenderInterface>();
		this.queriers = new Vector<QuerierInterface>();
		this.cancelInut = new Vector<CancelInutInterface>();
		this.listeners = new Vector<TTIEventListener>();
		this.contingence = new Vector<ContingeceInterface>();
		this.dbAccess = new Vector<DBAccess>();
		this.users = new Vector<UserManager>();
		this.ttiprinters = new Vector<TaragonaInt>();
		this.localprinter = new Vector<XMLPrinter>();
		this.connectors = new Vector<Connector>();

		this.configSystem.make();
		if (MainParameters.isLocal()) {
			this.initLocalInstances();
		}

		this.modoOp = new ModoOpManager();
	}

	private void initLocalInstances() {
		Locator.setManager(this);

		SignerInterface signer = new Signer();
		this.signers.add(signer);

		MessengerInterface msn = new Messenger();
		this.messengers.add(msn);

		CallBackInteface call = new CallBack();
		this.callbacks.add(call);

		SenderInterface sender = new Sender();
		this.senders.add(sender);

		QuerierInterface querier = new Querier();
		this.queriers.add(querier);

		CancelInutInterface cancelInut = new CancelInut();
		this.cancelInut.add(cancelInut);

		ContingeceInterface contingence = new Contingence();
		this.contingence.add(contingence);

		LaserPrinter print = new LaserPrinter();
		this.localprinter.add(print);

		if (MainParameters.isExternaldb()) {
			LaunchExternalAccess l = new LaunchExternalAccess();
			l.initExternalAccess();
		} else {
			DBAccess db = new DBAccessImpl();
			this.dbAccess.add(db);

			UserManager user = new UserManagerImpl();
			this.users.add(user);
		}
	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {
		for (SenderInterface sender : this.senders) {
			return sender.sendXMLMessage(idServico, header, data, prop);
		}
		return null;
	}

	@Override
	public Hashtable<String, MessengerConfig> getMessengerConfig()
			throws RemoteException {
		return this.configSystem.getMsnConfigs();
	}

	@Override
	public Hashtable<String, CNPJData> getCNPJ() throws RemoteException {

		return this.configSystem.getCnpjsData();
	}

	@Override
	public Hashtable<String, CNPJSerialize> getCNPJsSerialize()
			throws RemoteException {
		return this.configSystem.getCnpjsSer();
	}

	@Override
	public DBConfig getDBConfig() throws RemoteException {
		return this.configSystem.getDbConfig();
	}

	@Override
	public Vector<CertificatesConfig> getCertificates() throws RemoteException {
		return this.configSystem.getCertificates();
	}

	@Override
	public SenderConfig getSenderConfig() throws RemoteException {
		return this.configSystem.getSenderConfig();
	}

	@Override
	public ServicesConfig getServiceConfig() throws RemoteException {
		return this.configSystem.getServiceConfig();
	}

	@Override
	public void checkXmlSet(String cnpj, String idLote, String nRecibo,
			Vector<String> notas, String uf, String ambient)
			throws RemoteException {
		for (CallBackInteface call : this.callbacks) {
			call.checkXmlSet(cnpj, idLote, nRecibo, notas, uf, ambient);
		}
	}

	@Override
	public void sendXml(String keyXml, String xml, String cnpjSender,
			String cnpjReceiver, String dateEmiss, String message, String uf,
			String ambient, boolean sign, boolean error) throws RemoteException {
		for (MessengerInterface msn : this.messengers) {
			msn.sendXml(keyXml, xml, cnpjSender, cnpjReceiver, dateEmiss,
					message, uf, ambient, sign, error);
		}
	}

	@Override
	public void sendXml(String xml) throws RemoteException {
		for (MessengerInterface msn : this.messengers) {
			msn.sendXml(xml);
		}
	}

	@Override
	public boolean isActive() throws RemoteException {
		for (MessengerInterface msn : this.messengers) {
			return msn.isActive();
		}
		return false;
	}

	@Override
	public MODO_OP getModo(String cnpj, String ambient) throws RemoteException {
		for (MessengerInterface msn : this.messengers) {
			return msn.getModo(cnpj, ambient);
		}
		return null;
	}

	@Override
	public void setMessengerParameters(String cnpj, String ambient,
			MessengerConfig msnConfig) throws RemoteException {
		for (MessengerInterface msn : this.messengers) {
			msn.setMessengerParameters(cnpj, ambient, msnConfig);
		}
	}

	@Override
	public Hashtable<String, CallBackConfig> getCallBackConfig()
			throws RemoteException {
		return this.configSystem.getCallsConfigs();
	}

	@Override
	public String signForCNPJ(String cnpj, String xml, String tag)
			throws RemoteException {
		for (SignerInterface signer : this.signers) {
			return signer.signForCNPJ(cnpj, xml, tag);
		}
		return null;
	}

	@Override
	public Hashtable<String, Hashtable<String, Object>> readAllPFXPRoperties() {
		for (SignerInterface signer : this.signers) {
			return signer.readAllPFXPRoperties();
		}
		return null;
	}

	@Override
	public void setCallBackParameters(String cnpj, String uf,
			CallBackConfig config) throws RemoteException {
		for (CallBackInteface callback : this.callbacks) {
			callback.setCallBackParameters(cnpj, uf, config);
		}

	}

	@Override
	public Hashtable<Vector<String>, Vector<SchemaVersionConfig>> getSchemasVersion()
			throws RemoteException {
		return this.configSystem.getSchemasVersion();
	}

	@Override
	public SefazState makeQueryState(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws RemoteException {
		for (QuerierInterface querier : this.queriers) {
			return querier.makeQueryState(uf, ambient, keyXml, persist, assyn);
		}
		return null;
	}

	@Override
	public String makeQueryXML(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws RemoteException {
		for (QuerierInterface querier : this.queriers) {
			return querier.makeQueryXML(uf, ambient, keyXml, persist, assyn);
		}
		return null;
	}

	@Override
	public String cancelXml(String cnpj, String uf, String ambient,
			String keyXml, String protocol, String just, boolean sync)
			throws RemoteException {
		for (CancelInutInterface cancel : this.cancelInut) {
			return cancel.cancelXml(cnpj, uf, ambient, keyXml, protocol, just,
					sync);
		}
		return null;
	}

	@Override
	public String inutXml(String cnpj, String uf, String ambient,
			String keyXml, String justl, boolean sync) throws RemoteException {
		for (CancelInutInterface cancel : this.cancelInut) {
			return cancel.inutXml(cnpj, uf, ambient, keyXml, justl, sync);
		}
		return null;
	}

	@Override
	public String inutXml(String nOp, String uf, String ambient, String ano,
			String cnpj, String mod, String serie, String ini, String fim,
			String just, boolean sync) throws RemoteException {
		for (CancelInutInterface cancel : this.cancelInut) {
			return cancel.inutXml(nOp, uf, ambient, ano, cnpj, mod, serie, ini,
					fim, just, sync);
		}
		return null;
	}

	// listener
	@Override
	public void processEvent(TTIEvent event) throws RemoteException {
		MyLogger.getLog().info(event.toString());
		this.notifyEvent(event);
	}

	// notifier
	@Override
	public void addListener(TTIEventListener listener) throws RemoteException {
		MyLogger.getLog().info("Adding: " + listener.toString());
		this.listeners.add(listener);
	}

	@Override
	public void notifyEvent(TTIEvent event) throws RemoteException {
		for (TTIEventListener listener : this.listeners) {
			try {
				listener.processEvent(event);
			} catch (Exception e) {
				MyLogger.getLog().info(
						"Manager listener not found: "
								+ e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void changeToState(String cnpj, String ambient, MODO_OP modo)
			throws RemoteException {
		for (ModeOperation op : this.messengers) {
			op.changeToState(cnpj, ambient, modo);
		}

		for (ModeOperation op : this.callbacks) {
			op.changeToState(cnpj, ambient, modo);
		}

		for (ModeOperation op : this.cancelInut) {
			op.changeToState(cnpj, ambient, modo);
		}

		ChangeModeOpEvent s = new ChangeModeOpEvent();
		s.setCnpj(cnpj);
		s.setAmb(ambient);
		s.setModo(modo);

		this.notifyEvent(s);
	}

	@Override
	public void enterContingence(String cnpj, String ambient, boolean async,
			MODO_OP type) throws RemoteException {
		for (ContingeceInterface cont : this.contingence) {
			cont.enterContingence(cnpj, ambient, async, type);
		}
	}

	@Override
	public void leaveContingence(String cnpj, String ambient, boolean async,
			MODO_OP type) throws RemoteException {
		this.contingence.get(0).leaveContingence(cnpj, ambient, async, type);
	}

	@Override
	public Vector<XMLData> findXMLData(Date d1, Date d2, Vector<String> cnpjs,
			String ambient, Hashtable prop) throws RemoteException {
		for (DBAccess db : this.dbAccess) {
			return db.findXMLData(d1, d2, cnpjs, ambient, prop);
		}
		return null;
	}

	@Override
	public Vector<XMLData> findXMLData(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		for (DBAccess db : this.dbAccess) {
			return db.findXMLData(d1, d2, cnpjs, ambient, prop);
		}
		return null;
	}

	@Override
	public Vector<TTIEvent> findXMLEvent(Date d1, Date d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		for (DBAccess db : this.dbAccess) {
			return db.findXMLEvent(d1, d2, cnpjs, ambient, prop);
		}
		return null;
	}

	@Override
	public Vector<TTIEvent> findXMLEvent(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		for (DBAccess db : this.dbAccess) {
			return db.findXMLEvent(d1, d2, cnpjs, ambient, prop);
		}
		return null;
	}

	@Override
	public void register(User user, Profile p) throws RemoteException {
		for (UserManager u : this.users) {
			u.register(user, p);
		}
	}

	/*
	 * @Override public MODO_OP getModoOp(String cnpj) throws RemoteException {
	 * return this.modoOp.getModo(cnpj); }
	 */

	@Override
	public void changePassword(String login, String newPassword)
			throws RemoteException {
		for (UserManager u : this.users) {
			u.changePassword(login, newPassword);
		}
	}

	@Override
	public User getUser(String login, String password) throws RemoteException {
		for (UserManager u : this.users) {
			return u.getUser(login, password);
		}
		return null;
	}

	@Override
	synchronized public void printXml(String keyXml, String xml, MODO_OP modo)
			throws RemoteException {
		MyLogger.getLog().info("Reprint XML: " + keyXml);
		Vector<TaragonaInt> remove = new Vector<TaragonaInt>();
		for (TaragonaInt print : this.ttiprinters) {
			try {
				print.imprimeDanfe(keyXml, modo.toString(), xml);
			} catch (Exception e) {
				MyLogger.getLog().info(
						"Removing printer: " + print.toString() + " reason: "
								+ e.getLocalizedMessage());
				remove.add(print);
			}
		}

		for (TaragonaInt print : remove) {
			this.ttiprinters.remove(print);
		}
	}

	@Override
	public void registrarImpressao(TaragonaInt imp) throws RemoteException {
		this.ttiprinters.add(imp);
	}

	@Override
	public void rePrintXml(String keyXml, MODO_OP modo) throws RemoteException {
		for (XMLPrinter print : this.localprinter) {
			print.rePrintXml(keyXml, modo);
		}
	}

	@Override
	public boolean checkPFXPassword(String pfx, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector<LogXML> findLog(String keyXml) throws RemoteException {
		for (DBAccess dba : this.dbAccess) {
			return dba.findLog(keyXml);
		}
		return null;
	}

	@Override
	public String findXML(String keyXml) throws RemoteException {
		for (DBAccess dba : this.dbAccess) {
			return dba.findXML(keyXml);
		}
		return null;
	}

	@Override
	public boolean checkXMLMessage(String idServico, Hashtable prop)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addConnector(Connector connector) throws RemoteException {
		MyLogger.getLog().info("Connector registered: " + connector.toString());
		this.connectors.add(connector);
	}

	@Override
	public void notifyDataConnector(TTIEvent event) throws RemoteException {
		for (Connector connect : this.connectors) {
			connect.processEvent(event);
		}
	}

}
