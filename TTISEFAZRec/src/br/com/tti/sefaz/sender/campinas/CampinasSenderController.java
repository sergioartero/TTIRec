package br.com.tti.sefaz.sender.campinas;

import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.remote.SenderConfig;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.com.tti.sefaz.util.ReadFile;

public class CampinasSenderController extends SenderGenericController {

	public CampinasSenderController(SenderConfig senderConfig) {
		super(senderConfig);
	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {
		return null;
	}

	public static void main(String[] args) {
		try {
			LoteRpsServiceLocator l = new LoteRpsServiceLocator();
			LoteRps_PortType port = l.getLoteRps();
			String r = port
					.consultarNota(ReadFile
							.readFile("C:\\TTINFSe\\exemplosws\\XML Exemplo\\Campinas\\ConsultarNotas\\remessa_consultanotas_assinado2.xml"));
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
