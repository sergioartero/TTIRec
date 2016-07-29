package br.com.tti.sefaz.sender.saopaulo;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.gov.sp.prefeitura.nfe.ConsultaNFeRecebidasRequest;
import br.gov.sp.prefeitura.nfe.ConsultaNFeRecebidasResponse;
import br.gov.sp.prefeitura.nfe.LoteNFe;
import br.gov.sp.prefeitura.nfe.LoteNFeSoap;

public class SaoPauloSenderController extends SenderGenericController {

	private LoteNFe service;
	private LoteNFeSoap port;

	public SaoPauloSenderController(SenderConfig senderConfig) {
		super(senderConfig);
		this.service = new LoteNFe();
		this.port = this.service.getLoteNFeSoap();
	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {

		if (SystemProperties.ID_SERVICO_CONSULTA.equals(idServico)) {
			ConsultaNFeRecebidasRequest parameters = new ConsultaNFeRecebidasRequest();

			parameters.setMensagemXML(data);
			parameters.setVersaoSchema(1);

			ConsultaNFeRecebidasResponse response = this.port
					.consultaNFeRecebidas(parameters);

			return response.getRetornoXML();
		}

		return null;
	}
}
