package br.com.tti.sefaz.manager;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import nfeimpressao.TaragonaInt;
import br.com.tti.sefaz.connector.Connector;
import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.persistence.LogXML;
import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.remote.CNPJData;
import br.com.tti.sefaz.remote.CNPJSerialize;
import br.com.tti.sefaz.remote.CallBackConfig;
import br.com.tti.sefaz.remote.CertificatesConfig;
import br.com.tti.sefaz.remote.DBConfig;
import br.com.tti.sefaz.remote.MessengerConfig;
import br.com.tti.sefaz.remote.SchemaVersionConfig;
import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.remote.ServicesConfig;
import br.com.tti.sefaz.remote.events.TTIEvent;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.users.Profile;
import br.com.tti.sefaz.users.User;

public class ManagerAdapter implements ManagerInterface {

	@Override
	public boolean isActive() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendXml(String keyXml, String xml, String cnpjSender,
			String cnpjReceiver, String dateEmiss, String message, String uf,
			String ambient, boolean sign, boolean error) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendXml(String xml) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMessengerParameters(String cnpj, String ambient,
			MessengerConfig msnConfig) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeToState(String cnpj, String ambient, MODO_OP modo)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public MODO_OP getModo(String cnpj, String ambient) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkXMLMessage(String idServico, Hashtable prop)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkXmlSet(String cnpj, String idLote, String nRecibo,
			Vector<String> notas, String uf, String ambient)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCallBackParameters(String cnpj, String uf,
			CallBackConfig config) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Hashtable<String, CNPJData> getCNPJ() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, CNPJSerialize> getCNPJsSerialize()
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, CallBackConfig> getCallBackConfig()
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<CertificatesConfig> getCertificates() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBConfig getDBConfig() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, MessengerConfig> getMessengerConfig()
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<Vector<String>, Vector<SchemaVersionConfig>> getSchemasVersion()
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SenderConfig getSenderConfig() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServicesConfig getServiceConfig() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String signForCNPJ(String cnpj, String xml, String tag)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SefazState makeQueryState(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String makeQueryXML(String uf, String ambient, String keyXml,
			boolean persist, boolean assyn) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cancelXml(String cnpj, String uf, String ambient,
			String keyXml, String protocol, String just, boolean async)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String inutXml(String cnpj, String uf, String ambient,
			String keyXml, String justl, boolean sync) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String inutXml(String nOp, String uf, String ambient, String ano,
			String cnpj, String mod, String serie, String ini, String fim,
			String just, boolean async) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processEvent(TTIEvent event) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(TTIEventListener listener) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyEvent(TTIEvent event) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterContingence(String cnpj, String ambient, boolean async,
			MODO_OP modo) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveContingence(String cnpj, String ambient, boolean async,
			MODO_OP type) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkPFXPassword(String pfx, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector<LogXML> findLog(String keyXml) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findXML(String keyXml) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<XMLData> findXMLData(Date d1, Date d2, Vector<String> cnpjs,
			String ambient, Hashtable prop) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<XMLData> findXMLData(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<TTIEvent> findXMLEvent(Date d1, Date d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<TTIEvent> findXMLEvent(String d1, String d2,
			Vector<String> cnpjs, String ambient, Hashtable prop)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(String login, String newPassword)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public User getUser(String login, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(User user, Profile p) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void printXml(String keyXml, String xml, MODO_OP modo)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rePrintXml(String keyXml, MODO_OP modo) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarImpressao(TaragonaInt imp) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addConnector(Connector connector) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyDataConnector(TTIEvent data) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Hashtable<String, Hashtable<String, Object>> readAllPFXPRoperties() {
		// TODO Auto-generated method stub
		return null;
	}
}
