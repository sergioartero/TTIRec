package br.com.tti.sefaz.wsendpoint;

import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.Endpoint;

import br.com.tti.sefaz.consultadest.TRetConsNFeDest;
import br.com.tti.sefaz.event.EventManagerImpl;
import br.com.tti.sefaz.event.xml.classes.TEvento;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.persistence.EventData.TIPO_EVENTO;
import br.com.tti.sefaz.querier.dest.QuerierDestImpl;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.signer.Signer;
import br.com.tti.sefaz.systemconfig.XMLConfigSystem;
import br.com.tti.sefaz.util.KeyXmlManager;
import br.com.tti.sefaz.util.MainParameters;
import br.com.tti.sefaz.event.xml.classes.ret.TRetEnvEvento;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class TTIWSEndpoint {

	private SenderDispatchController sender;
	private Signer signer;

	private QuerierDestImpl querierdest;
	private EventManagerImpl eventmanager;
	private ResourceBundle bundle;
	private String xmlFile;

	public TTIWSEndpoint() {
		this.init();
	}

	private void init() {
		this.bundle = ResourceBundle.getBundle("configure");
		this.xmlFile = this.bundle.getString("fileconfig");

		String[] args = new String[3];
		args[0] = "-xml";
		args[1] = this.xmlFile;
		args[2] = "-localdb";

		MainParameters.processArguments(args);

		XMLConfigSystem configSystem = new XMLConfigSystem(
				MainParameters.getXml());
		configSystem.make();

		this.sender = new SenderDispatchController(
				configSystem.getSenderConfig(), configSystem.getServiceConfig());
		this.signer = new Signer(configSystem.getCertificates());
		this.querierdest = new QuerierDestImpl(this.sender);

		this.eventmanager = new EventManagerImpl(this.sender, this.signer);

	}

	@WebMethod
	public TRetEnvEvento enviarEvento(
			@WebParam(name = "chaveNFe") String keyxml,
			@WebParam(name = "tipo") int tipo,
			@WebParam(name = "justificativa") String justificativap,
			@WebParam(name = "dataHora") String datahorap) throws Exception {

		TIPO_EVENTO tpEvento = TIPO_EVENTO.values()[tipo];

		if (TIPO_EVENTO.DESCON_OP.equals(tpEvento)
				&& justificativap.length() < 15) {
			throw new Exception("justificativa");
		}

		KeyXmlManager key = new KeyXmlManager(keyxml);

		String xconduso = tpEvento.getMensagem();
		String just = justificativap;
		String datahora = datahorap;
		String cnpjclient = key.getCnpj();
		return this.eventmanager.sendEvent(keyxml, tpEvento.getCodigo(),
				tpEvento, xconduso, just, datahora, cnpjclient);

	}

	@WebMethod
	public List<TEvento> obterEventos(
			@WebParam(name = "chaveNFe") String keyxml,
			@WebParam(name = "params") Hashtable<String, String> props)
			throws Exception {
		return null;
	}

	@WebMethod
	public List<TRetConsNFeDest> consultaNFeDest(
			@WebParam(name = "cnpj") String cnpj,
			@WebParam(name = "params") Hashtable<String, String> props)
			throws Exception {

		this.querierdest.consultarNFeDest(cnpj, null);
		return null;
	}

	public static void main(String[] args) {
		MyLogger.getLog().info("starting...");
		Endpoint.publish("http://0.0.0.0:8080/TTIWSEndpoint",
				new TTIWSEndpoint());
	}
}
