package br.com.tti.sefaz.sender.semfazonline;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.org.abrasf.nfse.Input;
import br.org.abrasf.nfse.Nfse;
import br.org.abrasf.nfse.NfseWSService;

public class SemfazOnlineSenderController extends SenderGenericController {

	private NfseWSService service;
	private Nfse port;

	public SemfazOnlineSenderController(SenderConfig senderConfig) {
		super(senderConfig);
		this.service = new NfseWSService();
		this.port = this.service.getNfseSOAP();
	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {

		return null;
	}

}
