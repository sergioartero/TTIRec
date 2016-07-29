package br.com.tti.sefaz.event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import br.com.tti.sefaz.event.xml.classes.ret.TRetEnvEvento;
import br.com.tti.sefaz.persistence.EventData;
import br.com.tti.sefaz.persistence.EventData.TIPO_EVENTO;

public interface EventManagerInterface extends Remote {

	public List<EventData> obterEventos(String keyxml,
			Hashtable<String, String> props) throws RemoteException;

	public String adicionarEvento(String keyxml, EventData info,
			Hashtable<String, String> props) throws Exception;

	public String xmlCCe(String keyxml, int num) throws RemoteException;

	void sendConfirmationDest(String keyxml, String just, String cnpjclient)
			throws RemoteException;

	public TRetEnvEvento sendEvent(String keyxml, String tipoevent,
			TIPO_EVENTO evento, String xconduso, String just, String datahora,
			String cnpjclient) throws RemoteException;

	public TRetEnvEvento processEventData(String keyxml, String tipoevent,
			EventData eventdata, Hashtable<String, String> props)
			throws RemoteException;

	public void addListener(EventManagerListener listener);

	public List<EventData> recoverEventData(String keyxml,
			TIPO_EVENTO tipoevento, Date data, Hashtable<String, Object> props);

}
