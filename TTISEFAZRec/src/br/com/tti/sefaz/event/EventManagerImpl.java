package br.com.tti.sefaz.event;

import java.io.FileWriter;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.persistence.Query;
import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.event.xml.classes.ObjectFactory;
import br.com.tti.sefaz.event.xml.classes.TEnvEvento;
import br.com.tti.sefaz.event.xml.classes.TEvento;
import br.com.tti.sefaz.event.xml.classes.TEvento.InfEvento;
import br.com.tti.sefaz.event.xml.classes.TEvento.InfEvento.DetEvento;
import br.com.tti.sefaz.event.xml.classes.TretEvento;
import br.com.tti.sefaz.event.xml.classes.ret.TRetEnvEvento;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.EventData;
import br.com.tti.sefaz.persistence.EventData.TIPO_EVENTO;
import br.com.tti.sefaz.persistence.EventId;
import br.com.tti.sefaz.persistence.EventData.EVENTO_STATUS;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.persistence.dao.XMLDaoJPA;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.signer.Signer;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.KeyXmlManager;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.util.ReadFile;
import br.com.tti.sefaz.xml.XMLGenerator;
import br.com.tti.sefaz.xml.XMLValidator;

public class EventManagerImpl implements EventManagerInterface {

	private DAO<EventData> daoEvent;

	private XMLGenerator gen;
	private XMLGenerator genret;

	private ObjectFactory factory;
	private String cnpj;
	private String UF;

	private SimpleDateFormat sdf;
	private SenderDispatchController sender;
	private Signer signer;
	private ResourceBundle bundle;

	private String ambient;
	private String xmlFile;
	private String xCondUso = "Ciencia da Operacao";
	private List<EventManagerListener> listeners;

	public EventManagerImpl() {
		this.bundle = ResourceBundle.getBundle("configure");
		this.ambient = this.bundle.getString("ambient");
		this.xmlFile = this.bundle.getString("fileconfig");

		String[] args = new String[3];
		args[0] = "-xml";
		args[1] = this.xmlFile;
		args[2] = "-localdb";

		MainParameters.processArguments(args);

		this.daoEvent = DaoFactory.createDAO(EventData.class);
		this.listeners = new ArrayList<EventManagerListener>();

		this.gen = new XMLGenerator("br.com.tti.sefaz.event.xml.classes");
		this.genret = new XMLGenerator("br.com.tti.sefaz.event.xml.classes.ret");

		this.factory = new ObjectFactory();
		this.sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00'-02:00'");

		XMLConfigSystem configSystem = new XMLConfigSystem(
				MainParameters.getXml());
		configSystem.make();

		this.sender = new SenderDispatchController(
				configSystem.getSenderConfig(), configSystem.getServiceConfig());

		this.signer = new Signer(configSystem.getCertificates());
	}

	public EventManagerImpl(SenderDispatchController sender, Signer signer) {
		this.bundle = ResourceBundle.getBundle("configure");
		this.daoEvent = DaoFactory.createDAO(EventData.class);
		this.listeners = new ArrayList<EventManagerListener>();

		this.gen = new XMLGenerator("br.com.tti.sefaz.event.xml.classes");
		this.genret = new XMLGenerator("br.com.tti.sefaz.event.xml.classes.ret");

		this.factory = new ObjectFactory();
		this.sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00'-02:00'");

		/*
		 * this.sender = new SenderDispatchController(
		 * configSystem.getSenderConfig(), configSystem.getServiceConfig());
		 */

		this.sender = sender;
		this.signer = signer;
	}

	public EventManagerImpl(SenderDispatchController sender) {
		this.bundle = ResourceBundle.getBundle("configure");
		this.daoEvent = DaoFactory.createDAO(EventData.class);
		this.listeners = new ArrayList<EventManagerListener>();

		this.gen = new XMLGenerator("br.com.tti.sefaz.event.xml.classes");
		this.genret = new XMLGenerator("br.com.tti.sefaz.event.xml.classes.ret");

		this.factory = new ObjectFactory();
		this.sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00'-02:00'");

		XMLConfigSystem configSystem = new XMLConfigSystem(
				MainParameters.getXml());
		configSystem.make();

		/*
		 * this.sender = new SenderDispatchController(
		 * configSystem.getSenderConfig(), configSystem.getServiceConfig());
		 */

		this.sender = sender;

		this.signer = new Signer(configSystem.getCertificates());
	}

	@Override
	public List<EventData> obterEventos(String keyxml,
			Hashtable<String, String> props) {

		System.out.println("Find events for:" + keyxml);

		if (keyxml.startsWith("NFe")) {
			keyxml = keyxml.replace("NFe", "");
		}

		String sql = "SELECT o FROM EventoInfo as o where o.event.infEvento.chNFe = '"
				+ keyxml + "' order by o.event.infEvento.dhEvento desc ";

		Query query = this.daoEvent.createQuery(sql);
		// query.setParameter("e", ESTADO_NFE.AUTORIZADA);

		return query.getResultList();
	}

	public List<EventData> obterEventosSequence(DAO<EventData> daoevent,
			String keyxml, String tipoevent, Hashtable<String, String> props) {

		if (keyxml.startsWith("NFe")) {
			keyxml = keyxml.replace("NFe", "");
		}

		String sql = "SELECT o FROM EventData as o where o.event.infEvento.chNFe = '"
				+ keyxml
				+ "' and o.estado = :e and o.event.infEvento.tpEvento = :tipoevent order by o.event.infEvento.nSeqEvento desc ";

		Query query = daoevent.createQuery(sql);
		query.setParameter("e", XML_STATE.AUTORIZADA);
		query.setParameter("tipoevent", tipoevent);

		return query.getResultList();
	}

	public int obterNumberEventos(DAO<EventData> daoevent, String keyxml,
			String tipoevent, Hashtable<String, String> props) {

		if (keyxml.startsWith("NFe")) {
			keyxml = keyxml.replace("NFe", "");
		}

		String sql = "SELECT o FROM EventData as o where o.event.infEvento.chNFe = '"
				+ keyxml + "' order by o.event.infEvento.nSeqEvento desc ";

		Query query = daoevent.createQuery(sql);
		// query.setParameter("tipoevent", tipoevent);

		List result = query.getResultList();

		if (result == null) {
			return 0;
		}

		return result.size();
	}

	public String createEventHeader(String uf) {
		String version = this.bundle.getString("versaoevento");
		String cabecalho = "<nfeCabecMsg xmlns =\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">"
				+ "<cUF>"
				+ uf
				+ "</cUF>"
				+ "<versaoDados>"
				+ version
				+ "</versaoDados>" + "</nfeCabecMsg>";
		return cabecalho;
	}

	@Override
	public String adicionarEvento(String keyxml, EventData eventdata,
			Hashtable<String, String> props) throws RemoteException {

		List<EventData> oldevents = this.obterEventosSequence(this.daoEvent,
				keyxml, "210210", null);

		int nseqn = 1;
		if (oldevents != null && oldevents.size() >= 1) {
			EventData lastevent = oldevents.get(0);

			String lastnumberb = lastevent.getEvent().getInfEvento()
					.getNSeqEvento();
			if (lastnumberb != null) {
				nseqn = Integer.parseInt(lastnumberb);
				nseqn = nseqn + 1;
			}
		}

		eventdata.setStatus(EVENTO_STATUS.GERADO);
		eventdata.setEstado(XML_STATE.GERADA);
		eventdata.setDataGerada(Calendar.getInstance().getTime());

		eventdata.getEvent().getInfEvento().setNSeqEvento("1");
		eventdata.getEvent().setVersao("1.00");

		String tipoamb = "1";
		eventdata.getEvent().getInfEvento().setTpAmb(tipoamb);

		String id = eventdata.getEvent().getInfEvento().getTpEvento()
				+ eventdata.getEvent().getInfEvento().getChNFe() + "0"
				+ eventdata.getEvent().getInfEvento().getNSeqEvento();
		eventdata.getEvent().setVersao("1.00");
		eventdata.getEvent().getInfEvento().setId("ID" + id);
		String xml = null;
		try {
			this.daoEvent.saveEntity(eventdata);
			xml = this.toXMLString(eventdata);
			xml = this.repareXML(eventdata.getEvent().getInfEvento().getCNPJ(),
					xml);

			FileWriter fw = new FileWriter("ultimo_evento.xml");
			fw.write(xml);
			fw.close();

			this.notify(eventdata);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String resultxml = null;
		String header = this.createEventHeader(eventdata.getEvent()
				.getInfEvento().getCOrgao());
		try {
			resultxml = this.sender
					.sendXMLMessage(
							SystemProperties.ID_SERVICO_RECEPCAO_EVENTO,
							header,
							"<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">"
									+ xml + "</nfeDadosMsg>", props);
			System.out.println(resultxml);

			eventdata.setStatus(EVENTO_STATUS.ENVIADO);
			eventdata.setDataEnviada(Calendar.getInstance().getTime());

			this.daoEvent.updateEntity(eventdata);
			this.daoEvent.flush();

			this.notify(eventdata);

		} catch (Exception e) {
			throw new RemoteException(e.getLocalizedMessage(),
					e.fillInStackTrace());
		}

		if (resultxml != null) {
			// eventdata.setEstado(XML_STATE.AUTORIZADA);
			eventdata.setDataAtualizada(Calendar.getInstance().getTime());

			TRetEnvEvento ret = this.processResult(eventdata, resultxml);

			String endtag = "</retEnvEvento>";
			int pos1 = resultxml.indexOf("<retEnvEvento");
			int pos2 = resultxml.indexOf(endtag);

			if (pos1 != -1 && pos2 != -1) {
				resultxml = resultxml.substring(pos1, pos2 + endtag.length());
			}

			eventdata.setXmlString(xml);
			eventdata.setProtocoloXML(resultxml);

			this.daoEvent.updateEntity(eventdata);
			this.daoEvent.flush();

			this.notify(eventdata);

			return ret.getXMotivo();
		} else {
			throw new RemoteException("Evento sem retorno da sefaz");
		}

	}

	@Override
	public TRetEnvEvento processEventData(String keyxml, String tipoevent,
			EventData eventdata, Hashtable<String, String> props)
			throws RemoteException {

		List<EventData> oldevents = this.obterEventosSequence(this.daoEvent,
				keyxml, tipoevent, null);

		int nseqn = 1;
		if (oldevents != null && oldevents.size() >= 1) {
			EventData lastevent = oldevents.get(0);

			String lastnumberb = lastevent.getEvent().getInfEvento()
					.getNSeqEvento();
			if (lastnumberb != null) {
				nseqn = Integer.parseInt(lastnumberb);
				nseqn = nseqn + 1;
			}
		}

		int quantity = 1;
		try {
			quantity = this.obterNumberEventos(this.daoEvent, keyxml,
					tipoevent, props);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		// //////////////////////////////////////////////////////////

		EventId idevent = new EventId();
		idevent.setKeyxml(keyxml);
		idevent.setId(quantity);
		eventdata.setId(idevent);

		eventdata.setEstado(XML_STATE.GERADA);
		eventdata.setDataGerada(Calendar.getInstance().getTime());

		eventdata.getEvent().getInfEvento().setNSeqEvento(nseqn + "");
		eventdata.getEvent().setVersao("1.00");

		String tipoamb = "1";
		eventdata.getEvent().getInfEvento().setTpAmb(tipoamb);

		String id = eventdata.getEvent().getInfEvento().getTpEvento()
				+ eventdata.getEvent().getInfEvento().getChNFe() + "0"
				+ eventdata.getEvent().getInfEvento().getNSeqEvento();
		eventdata.getEvent().setVersao("1.00");
		eventdata.getEvent().getInfEvento().setId("ID" + id);
		String xml = null;
		try {
			this.daoEvent.saveEntity(eventdata);
			xml = this.toXMLString(eventdata);
			xml = this.repareXML(eventdata.getEvent().getInfEvento().getCNPJ(),
					xml);

			/*
			 * XMLValidator validator = new XMLValidator(
			 * "C:\\TTINFE2.0\\documentos\\EventoManifestaDestinat_v100\\envConfRecebto_v1.00.xsd"
			 * ); validator.validateXml(xml);
			 */

			FileWriter fw = new FileWriter("ultimo_evento.xml");
			fw.write(xml);
			fw.close();

			// this.notify(eventdata);

		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			throw new RemoteException(e.getLocalizedMessage(),
					e.fillInStackTrace());
		}

		String resultxml = null;
		String header = this.createEventHeader(eventdata.getEvent()
				.getInfEvento().getCOrgao());
		try {
			resultxml = this.sender
					.sendXMLMessage(
							SystemProperties.ID_SERVICO_RECEPCAO_EVENTO,
							header,
							"<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">"
									+ xml + "</nfeDadosMsg>", props);
			System.out.println(resultxml);

			eventdata.setStatus(EVENTO_STATUS.ENVIADO);
			eventdata.setDataEnviada(Calendar.getInstance().getTime());

			this.daoEvent.updateEntity(eventdata);
			this.daoEvent.flush();

			this.notify(eventdata);

		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			throw new RemoteException(e.getLocalizedMessage(),
					e.fillInStackTrace());
		}

		if (resultxml != null) {
			// eventdata.setEstado(XML_STATE.AUTORIZADA);
			// eventdata.setDataAtualizada(Calendar.getInstance().getTime());

			TRetEnvEvento ret = this.processResult(eventdata, resultxml);

			String endtag = "</retEnvEvento>";
			int pos1 = resultxml.indexOf("<retEnvEvento");
			int pos2 = resultxml.indexOf(endtag);

			if (pos1 != -1 && pos2 != -1) {
				resultxml = resultxml.substring(pos1, pos2 + endtag.length());
			}

			eventdata.setProtocoloXML(resultxml);
			eventdata.setRetevent(ret.getRetEvento().get(0));

			this.daoEvent.updateEntity(eventdata);
			this.daoEvent.flush();

			this.notify(eventdata);

			return ret;
		} else {
			throw new RemoteException("Evento sem retorno da sefaz");
		}

	}

	public TRetEnvEvento processResult(EventData eventdata, String xml) {
		String endtag = "</retEnvEvento>";
		int pos1 = xml.indexOf("<retEnvEvento");
		int pos2 = xml.indexOf(endtag);

		if (pos1 != -1 && pos2 != -1) {
			xml = xml.substring(pos1, pos2 + endtag.length());
		}

		try {

			JAXBElement<br.com.tti.sefaz.event.xml.classes.ret.TRetEnvEvento> retenv = (JAXBElement<br.com.tti.sefaz.event.xml.classes.ret.TRetEnvEvento>) this.genret
					.toObject(xml);

			String cstat = retenv.getValue().getCStat();

			MyLogger.getLog().info("cstat retorno:" + cstat);

			if (cstat.equals("128")) {
				List<br.com.tti.sefaz.event.xml.classes.ret.TretEvento> results = retenv
						.getValue().getRetEvento();

				// considera somente o primeiro retorno
				br.com.tti.sefaz.event.xml.classes.ret.TretEvento rett = results
						.get(0);
				if (rett.getInfEvento().getCStat().equals("135")
						|| rett.getInfEvento().getCStat().equals("136")) {
					eventdata.setEstado(XML_STATE.AUTORIZADA);
					eventdata.setStatus(EVENTO_STATUS.AUTORIZADO);
					eventdata.setMensagem(rett.getInfEvento().getXMotivo());
					eventdata.setDataAutorizada(Calendar.getInstance()
							.getTime());
					eventdata.setDataAtualizada(Calendar.getInstance()
							.getTime());

				} else {
					eventdata.setEstado(XML_STATE.REJEITADA);
					eventdata.setStatus(EVENTO_STATUS.REJEITADO);
					eventdata.setMensagem(rett.getInfEvento().getXMotivo());
					eventdata.setDataAtualizada(Calendar.getInstance()
							.getTime());
				}

				return retenv.getValue();

			} else {
				eventdata.setEstado(XML_STATE.ERRO_SCHEMA_LOTE);
				eventdata.setStatus(EVENTO_STATUS.ERRO);
				eventdata.setMensagem(retenv.getValue().getXMotivo());
				eventdata.setDataAtualizada(Calendar.getInstance().getTime());
			}

			return retenv.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String repareXML(String cnpj, String xml) {

		xml = xml
				.replace(
						"xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns=\"http://www.portalfiscal.inf.br/nfe\"",
						"xmlns=\"http://www.portalfiscal.inf.br/nfe\"");

		xml = xml.replace("ns2:", "");

		xml = xml
				.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
						"");

		xml = xml
				.replace("<evento versao=\"1.00\">",
						"<evento versao=\"1.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">");

		/*
		 * try { FileWriter fw = new FileWriter("C:\\preassinado.xml");
		 * fw.write(xml); fw.close(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		try {
			xml = this.signer.signForCNPJ(cnpj, xml,
					SystemProperties.ID_SERVICO_DOWNLOAD_NF);

			xml = xml.replace(
					"xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"",
					"xmlns=\"http://www.portalfiscal.inf.br/nfe\"");

			xml = xml
					.replace("<evento versao=\"1.00\">",
							"<evento versao=\"1.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">");

			xml = xml
					.replace("<envEvento versao=\"1.00\">",
							"<envEvento versao=\"1.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">");

			// XMLValidator v = new
			// XMLValidator("C:\\TTIRec\\PL\\PL\\EventoManifestaDestinat_v100\\envConfRecebto_v1.00.xsd");
			// v.validateXml(xml);

			/*
			 * FileWriter fw = new FileWriter("C:\\postpreassinado.xml");
			 * fw.write(xml); fw.close();
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	private String toXMLString(EventData info) {

		TEnvEvento envevento = new TEnvEvento();
		// envevento.setIdLote(IDSetManager.getNexIdSet().toString());
		envevento.getEvento().add(info.getEvent());
		envevento.setVersao("1.00");
		envevento.setIdLote("1234");

		try {
			String xml = this.gen.toXMLString(envevento);
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private String createXML(EventData info) {
		TEnvEvento envevento = this.factory.createTEnvEvento();
		envevento.setVersao("1.00");
		envevento.getEvento().add(info.getEvent());

		String xmlevento = null;

		try {
			xmlevento = this.gen.toXMLString(envevento);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlevento;
	}

	@Override
	public String xmlCCe(String keyxml, int num) throws RemoteException {
		String sql = "SELECT o FROM EventoInfo as o where o.event.infEvento.chNFe = '"
				+ keyxml.replace("NFe", "")
				+ "' and o.estado = :e order by o.event.infEvento.nSeqEvento desc ";

		System.out.println(sql);

		Query query = this.daoEvent.createQuery(sql);
		query.setParameter("e", XML_STATE.AUTORIZADA);

		List<EventData> result = query.getResultList();
		if (result.size() > 0) {
			return result.get(0).getXmlString();
		}
		return null;
	}

	public static void main2(String[] args) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:00'-03:00'");
		long currentlonf = Calendar.getInstance().getTimeInMillis()
				- (60 * 60 * 1000L);
		Date currentdata = new Date(currentlonf);
		System.out.println(sdf1.format(currentdata));
	}

	private void notify(EventData data) {
		for (EventManagerListener listener : this.listeners) {
			try {
				listener.process(data);
			} catch (Exception e) {
				MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void addListener(EventManagerListener listener) {
		// ttirec manager add listener
		this.listeners.add(listener);
	}

	@Override
	@Deprecated
	public void sendConfirmationDest(String keyxml, String just,
			String cnpjclient) throws RemoteException {

		EventData ed = new EventData();

		EventId id = new EventId();
		id.setKeyxml(keyxml);
		ed.setId(id);

		TEvento event = new TEvento();
		InfEvento infevent = new InfEvento();
		DetEvento detevent = new DetEvento();

		KeyXmlManager km = new KeyXmlManager(keyxml);

		infevent.setDetEvento(detevent);
		infevent.setChNFe(keyxml.replace("NFe", ""));
		infevent.setCNPJ(cnpjclient);
		infevent.setCOrgao("91");
		infevent.setTpEvento("210210");
		infevent.setVerEvento(this.bundle.getString("versaoevento"));

		long currentlonf = Calendar.getInstance().getTimeInMillis();
		// - (60 * 60 * 1000L);
		Date currentdata = new Date(currentlonf);
		infevent.setDhEvento(this.sdf.format(currentdata));

		detevent.setVersao("1.00");

		try {
			infevent.getDetEvento().setDescEvento(
					new String(xCondUso.getBytes(), "UTF-8"));
			/*
			 * infevent.getDetEvento().setXJust( new String(just.getBytes(),
			 * "UTF-8"));
			 */
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		event.setInfEvento(infevent);
		ed.setEvent(event);

		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("UF", "91");
		props.put("AMBIENT", this.bundle.getString("ambient"));

		this.adicionarEvento(keyxml, ed, props);
	}

	@Override
	public TRetEnvEvento sendEvent(String keyxml, String tipoevent,
			TIPO_EVENTO tipoevento, String xconduso, String just,
			String datahora, String cnpjclient) throws RemoteException {

		DAO<EventData> daotmp = DaoFactory.createDAO(EventData.class);

		EventData eventdata = new EventData();

		TEvento event = new TEvento();
		InfEvento infevent = new InfEvento();
		DetEvento detevent = new DetEvento();

		infevent.setDetEvento(detevent);
		infevent.setChNFe(keyxml.replace("NFe", ""));
		infevent.setCNPJ(cnpjclient);
		infevent.setCOrgao("91");
		infevent.setTpEvento(tipoevent);
		infevent.setVerEvento(this.bundle.getString("versaoevento"));
		infevent.setDhEvento(datahora);

		detevent.setVersao("1.00");

		try {
			infevent.getDetEvento().setDescEvento(
					new String(xconduso.getBytes(), "UTF-8"));

			if (just != null && !just.isEmpty()) {
				infevent.getDetEvento().setXJust(
						new String(just.getBytes(), "UTF-8"));
			}

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		event.setInfEvento(infevent);
		eventdata.setEvent(event);
		eventdata.setTipoEvento(tipoevento);

		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("UF", "91");
		props.put("AMBIENT", this.bundle.getString("ambient"));

		// /////////////////////////////////////////////////////////////////////////////////
		// ////////////////return this.processEventData(keyxml, tipoevent,
		// eventdata, props);
		// ///////////////////////////////////////////////////////////////////////////////

		List<EventData> oldevents = this.obterEventosSequence(daotmp, keyxml,
				tipoevent, null);

		int nseqn = 1;
		if (oldevents != null && oldevents.size() >= 1) {
			EventData lastevent = oldevents.get(0);

			String lastnumberb = lastevent.getEvent().getInfEvento()
					.getNSeqEvento();
			if (lastnumberb != null) {
				nseqn = Integer.parseInt(lastnumberb);
				nseqn = nseqn + 1;
			}
		}

		int quantity = 1;
		try {
			quantity = this.obterNumberEventos(daotmp, keyxml, tipoevent,
					props);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
		}

		// //////////////////////////////////////////////////////////

		EventId idevent = new EventId();
		idevent.setKeyxml(keyxml);
		idevent.setId(quantity);
		eventdata.setId(idevent);

		eventdata.setEstado(XML_STATE.GERADA);
		eventdata.setDataGerada(Calendar.getInstance().getTime());

		eventdata.getEvent().getInfEvento().setNSeqEvento(nseqn + "");
		eventdata.getEvent().setVersao("1.00");

		String tipoamb = "1";
		eventdata.getEvent().getInfEvento().setTpAmb(tipoamb);

		String id = eventdata.getEvent().getInfEvento().getTpEvento()
				+ eventdata.getEvent().getInfEvento().getChNFe() + "0"
				+ eventdata.getEvent().getInfEvento().getNSeqEvento();
		eventdata.getEvent().setVersao("1.00");
		eventdata.getEvent().getInfEvento().setId("ID" + id);
		String xml = null;

		try {
			daotmp.saveEntity(eventdata);
			daotmp.flush();

			xml = this.toXMLString(eventdata);
			xml = this.repareXML(eventdata.getEvent().getInfEvento().getCNPJ(),
					xml);

			/*
			 * XMLValidator validator = new XMLValidator(
			 * "C:\\TTINFE2.0\\documentos\\EventoManifestaDestinat_v100\\envConfRecebto_v1.00.xsd"
			 * ); validator.validateXml(xml);
			 */

			/*
			 * FileWriter fw = new FileWriter("ultimo_evento.xml");
			 * fw.write(xml); fw.close();
			 */

			// this.notify(eventdata);

		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			throw new RemoteException(e.getLocalizedMessage(),
					e.fillInStackTrace());
		}

		String resultxml = null;
		String header = this.createEventHeader(eventdata.getEvent()
				.getInfEvento().getCOrgao());
		try {
			resultxml = this.sender
					.sendXMLMessage(
							SystemProperties.ID_SERVICO_RECEPCAO_EVENTO,
							header,
							"<nfeDadosMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">"
									+ xml + "</nfeDadosMsg>", props);
			// System.out.println(resultxml);

			eventdata.setStatus(EVENTO_STATUS.ENVIADO);
			eventdata.setDataEnviada(Calendar.getInstance().getTime());

			this.notify(eventdata);

			daotmp.updateEntity(eventdata);
			daotmp.flush();

		} catch (Exception e) {
			MyLogger.getLog().log(Level.INFO, e.getLocalizedMessage(), e);
			throw new RemoteException(e.getLocalizedMessage(),
					e.fillInStackTrace());
		}

		if (resultxml != null) {
			// eventdata.setEstado(XML_STATE.AUTORIZADA);
			// eventdata.setDataAtualizada(Calendar.getInstance().getTime());

			TRetEnvEvento ret = this.processResult(eventdata, resultxml);

			String endtag = "</retEnvEvento>";
			int pos1 = resultxml.indexOf("<retEnvEvento");
			int pos2 = resultxml.indexOf(endtag);

			if (pos1 != -1 && pos2 != -1) {
				resultxml = resultxml.substring(pos1, pos2 + endtag.length());
			}

			eventdata.setProtocoloXML(resultxml);

			if (!ret.getRetEvento().isEmpty()) {
				eventdata.setRetevent(ret.getRetEvento().get(0));
			} else {
				br.com.tti.sefaz.event.xml.classes.ret.TretEvento retret = new br.com.tti.sefaz.event.xml.classes.ret.TretEvento();
				br.com.tti.sefaz.event.xml.classes.ret.TretEvento.InfEvento infret = new br.com.tti.sefaz.event.xml.classes.ret.TretEvento.InfEvento();
				infret.setCStat(ret.getCStat());
				infret.setXMotivo(ret.getXMotivo());
				infret.setDhRegEvento(Calendar.getInstance().getTime()
						.toGMTString());
				retret.setInfEvento(infret);
				eventdata.setRetevent(retret);
			}

			this.notify(eventdata);

			daotmp.updateEntity(eventdata);
			daotmp.flush();

			return ret;
		} else {
			throw new RemoteException("Evento sem retorno da sefaz");
		}

	}

	public static void main(String[] args) {

		EventManagerImpl em = new EventManagerImpl();

		try {
			em.sendConfirmationDest(
					"35130111245802000188550020000104751010791918",
					"registrando para conseguir baixar", "cnpjclient");
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<EventData> recoverEventData(String keyxml,
			TIPO_EVENTO tipoevento, Date data, Hashtable<String, Object> props) {

		MyLogger.getLog().info("SQL keyxml:" + keyxml);

		Query query = null;

		if (tipoevento == null && data == null && props == null) {
			query = this.daoEvent
					.createQuery("select e from EventData as e where e.id.keyxml = :key order by e.dataGerada desc");
			query.setParameter("key", keyxml);
		} /*
		 * else if (data == null) { query = this.daoEvent .createQuery(
		 * "select e from EventData as e where e.id.keyxml = :key and e.tipoEvento = :tipo order by e.dataGerada desc"
		 * ); query.setParameter("key", keyxml); query.setParameter("tipo",
		 * tipoevento); }
		 */
		else if (props != null && props.containsKey("estado")) {
			query = this.daoEvent
					.createQuery("select e from EventData as e where e.id.keyxml = :key and e.status = :estado order by e.dataGerada desc");
			query.setParameter("key", keyxml);
			query.setParameter("estado", props.get("estado"));
		} else {
			query = this.daoEvent
					.createQuery("select e from EventData as e where e.id.keyxml = :key and e.tipoEvento = :tipo and e.dataGerada >= :data order by e.dataGerada desc");
			query.setParameter("key", keyxml);
			query.setParameter("tipo", tipoevento);
			query.setParameter("data", data);
		}

		MyLogger.getLog().info("SQL:" + query.toString());

		List res = query.getResultList();

		MyLogger.getLog().info("SQL result size:" + res.size());

		return res;
	}

}
