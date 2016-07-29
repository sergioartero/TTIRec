package br.com.tti.sefaz.querier;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.sender.SenderInterface;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.util.KeyXmlManager;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public class XMLQueryThread implements Runnable {
	private SenderInterface sender;

	private XMLDataCache cacheXml;

	private XMLMessageFactory factoryMessage;

	private String uf;
	private String ambient;

	private String keyXml;

	private String header;
	private String data;
	private Hashtable<String, String> prop;

	private boolean persist;

	private Vector<SefazState> autorizedStates;
	private Vector<SefazState> rejectedStates;
	private Vector<SefazState> schemaErroStates;

	private SefazState sefazState;

	public XMLQueryThread(SenderInterface sender, XMLDataCache cacheXml,
			XMLMessageFactory factoryMessage, String uf, String ambient,
			String keyXml, boolean persist) {
		super();
		this.sender = sender;
		this.cacheXml = cacheXml;
		this.factoryMessage = factoryMessage;
		this.uf = uf;
		this.ambient = ambient;
		this.keyXml = keyXml;
		this.persist = persist;

		this.autorizedStates = States.getINSTANCE().getAutorizedStates();
		this.rejectedStates = States.getINSTANCE().getRejectedStates();
		this.schemaErroStates = States.getINSTANCE().getSchemaErrorStates();

		this.createMessages();
	}

	private void createMessages() {
		this.header = this.factoryMessage.createHeader(this.uf, this.ambient,
				SystemProperties.ID_SERVICO_CONSULTA);
		this.data = this.factoryMessage.createQueryXmlMessage(this.keyXml,
				this.uf, this.ambient);
		this.prop = new Hashtable<String, String>();
		prop.put("AMBIENT", this.ambient);
		prop.put("UF", this.uf);
	}

	public String sendQuery() throws Exception {
		MyLogger.getLog().info("Making query:" + this.keyXml);
		String resultXml = null;
		try {
			resultXml = this.sender.sendXMLMessage(
					SystemProperties.ID_SERVICO_CONSULTA, this.header,
					this.data, this.prop);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, e.getLocalizedMessage());
		}

		XMLWrapperQuery prot = null;
		try {
			prot = XMLWrapperFactory.createReturnQueryWrapper(resultXml);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, "XML de retorno mal formada");
		}

		this.sefazState = new SefazState(Integer.parseInt(prot.getCStat()
				.trim()), prot.getXMotivo());
		if (this.persist)
			this.persistResult(prot, this.sefazState);
		return resultXml;
	}

	private void persistXml(String keyXml, String uf, String ambient)
			throws Exception {
		KeyXmlManager key = new KeyXmlManager(keyXml);
		XMLData xmlData = new XMLData();
		xmlData.setKeyXml(keyXml);
		xmlData.setAmbient(ambient);
		xmlData.setCnpjEmit(key.getCnpj());
		xmlData.setUf(uf);
		this.cacheXml.simpleSaveState(xmlData);
	}

	private void persistResult(XMLWrapperQuery prot, SefazState stateProt) {
		try {
			this.persistXml(this.keyXml, this.uf, this.ambient);

			if (this.autorizedStates.contains(stateProt)) {
				this.cacheXml.updateAutorizeState(this.keyXml, prot.getProt(),
						prot.getXmlProtocol(), prot.getCStat(), prot
								.getXMotivo(), prot.getDhSefaz());
			}

			if (this.rejectedStates.contains(stateProt)) {
				this.cacheXml.updateStateSefaz(this.keyXml,
						XML_STATE.REJEITADA, Calendar.getInstance().getTime(),
						prot.getCStat(), prot.getXMotivo(), prot.getDhSefaz());
			}

			if (this.schemaErroStates.contains(stateProt)) {
				this.cacheXml.updateStateSefaz(this.keyXml,
						XML_STATE.ERRO_SCHEMA_XML, Calendar.getInstance()
								.getTime(), prot.getCStat(), prot.getXMotivo(),
						prot.getDhSefaz());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SefazState getSefazState() {
		return sefazState;
	}

	@Override
	public void run() {
		try {
			this.sendQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.cacheXml.forceFlushCache();
	}

}
