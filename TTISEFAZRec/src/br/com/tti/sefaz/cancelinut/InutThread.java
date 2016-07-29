package br.com.tti.sefaz.cancelinut;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;

import br.com.tti.sefaz.cache.XMLDataCache;
import br.com.tti.sefaz.exceptions.MySenderException;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.manager.ManagerInterface;
import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.systemconfig.States;
import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.systemconfig.SystemProperties.MODO_OP;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;
import br.com.tti.sefaz.util.KeyXmlManager;
import br.com.tti.sefaz.xmlgenerate.XMLMessageFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperFactory;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperInut;

public class InutThread implements Runnable {

	private ManagerInterface manager;
	private XMLDataCache cacheXml;
	private XMLMessageFactory factory;

	private boolean tryAgain;
	private boolean once;

	private String nOp;
	private String keyXml;
	private String uf;
	private String ambient;
	private String ano;
	private String cnpj;
	private String mod;
	private String serie;
	private String ini;
	private String fim;
	private String just;
	private String header;
	private String data;
	private Hashtable<String, String> prop;
	private Vector<SefazState> inutStates;
	private Vector<SefazState> alreadyInutStates;
	private Vector<SefazState> rejectedStates;
	private Vector<SefazState> schemaErrorStates;

	private MODO_OP modo;

	public InutThread(ManagerInterface manager, XMLDataCache cacheXml,
			XMLMessageFactory factory, String nOp, String uf, String ambient,
			String ano, String cnpj, String mod, String serie, String ini,
			String fim, String just, MODO_OP modo) {
		super();
		this.manager = manager;
		this.cacheXml = cacheXml;
		this.factory = factory;
		this.uf = uf;
		this.ambient = ambient;
		this.ano = ano;
		this.cnpj = cnpj;
		this.mod = mod;
		this.serie = serie;
		this.ini = ini;
		this.fim = fim;
		this.just = just;
		this.nOp = nOp;

		this.tryAgain = true;
		this.once = false;

		this.modo = modo;
		this.createMessages();
	}

	public InutThread(ManagerInterface manager, XMLDataCache cacheXml,
			XMLMessageFactory factory, String uf, String ambient,
			String keyXml, String justl, MODO_OP modo) {
		this.manager = manager;
		this.factory = factory;
		this.cacheXml = cacheXml;
		this.keyXml = keyXml;
		this.uf = uf;
		this.ambient = ambient;
		this.just = justl;
		this.nOp = nOp;
		this.modo = modo;

		KeyXmlManager key = new KeyXmlManager(this.keyXml);
		this.ano = key.getDdmm().substring(2, 4);
		this.mod = key.getModelo();
		this.serie = key.getSerie();
		this.ini = key.getNumeroNota();
		this.fim = key.getNumeroNota();

		this.tryAgain = true;
		this.once = true;

		this.createMessages();
	}

	private void createMessages() {
		this.header = this.factory.createHeader(this.uf, this.ambient,
				SystemProperties.ID_SERVICO_INUTILIZACAO);
		this.data = this.factory.createInutMessage(uf, ano, cnpj, mod, serie,
				ini, fim, just, ambient);
		this.prop = new Hashtable<String, String>();
		prop.put("AMBIENT", this.ambient);
		prop.put("UF", this.uf);

		this.inutStates = States.getINSTANCE().getInutdStates();
		this.alreadyInutStates = States.getINSTANCE().getAlreadyInutStates();
		this.rejectedStates = States.getINSTANCE().getRejectedStates();
		this.schemaErrorStates = States.getINSTANCE().getSchemaErrorStates();
	}

	private String makeInut() throws Exception {
		MyLogger.getLog().info("Making cancel: " + this.keyXml);
		String resultXml = null;
		try {
			resultXml = this.manager.sendXMLMessage(
					SystemProperties.ID_SERVICO_CANCELAMENTO, this.header,
					this.data, this.prop);
		} catch (Exception e) {
			if (e instanceof MySenderException) {
				this.tryAgain = true;
			} else {
				this.tryAgain = false;
			}
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

		XMLWrapperInut inut = null;
		try {
			inut = XMLWrapperFactory.createReturnInutWrapper(resultXml);
		} catch (Exception e) {
			MyLogger.getLog().log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new MySenderException(e, "XML de retorno mal formada");
		}

		SefazState state = new SefazState(Integer.parseInt(inut.getCStat()),
				inut.getXMotivo());

		MyLogger.getLog().info(
				"XML Inut: " + this.keyXml + " with op " + this.nOp);

		if (this.inutStates.contains(state)) {
			if (once) {
				this.cacheXml.updateCancelState(this.keyXml,
						inut.getProtInut(), inut.getXmlProtocol(), inut
								.getCStat(), inut.getXMotivo(), inut
								.getDhSefaz(), this.just);
			}
		}

		if (this.alreadyInutStates.contains(state)) {
			if (once) {
				this.cacheXml.updateState(this.keyXml, XML_STATE.INUTLIZADA,
						Calendar.getInstance().getTime());
			}
		}

		if (this.rejectedStates.contains(state)) {
			if (once) {
				this.cacheXml.updateStateSefaz(this.keyXml,
						XML_STATE.ERRO_INUT, Calendar.getInstance().getTime(),
						inut.getCStat(), inut.getXMotivo(), null);
			}
		}

		if (this.schemaErrorStates.contains(state)) {
			if (once) {
				this.cacheXml.updateStateSefaz(this.keyXml,
						XML_STATE.ERRO_INUT, Calendar.getInstance().getTime(),
						inut.getCStat(), inut.getXMotivo(), null);
			}
		}
		this.tryAgain = false;
		return state.getXMotive();
	}

	public String initInut() throws Exception {
		if (this.modo.equals(MODO_OP.NORMAL)) {
			return this.makeInut();
		} else {
			if (once)
				this.cacheXml.updateState(this.keyXml,
						XML_STATE.INUT_CONTINGENCIA, Calendar.getInstance()
								.getTime());
		}
		return null;
	}

	@Override
	public void run() {
		int numberTentatives = 0;
		while (this.tryAgain) {
			try {
				this.initInut();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (numberTentatives >= SystemParameters.MAX_NUMBER_TENTATIVES_CANCEL_INUT) {
				return;
			}
			numberTentatives++;
		}
		this.cacheXml.forceFlushCache();
	}
}
