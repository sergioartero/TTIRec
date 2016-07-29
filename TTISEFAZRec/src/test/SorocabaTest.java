package test;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import br.com.taragona.nfe.xmlgenerator.XMLGenerator;
import br.com.tti.sefaz.nfes.schemas.ginfes.ReqConsultaNotas;
import br.com.tti.sefaz.nfes.schemas.ginfes.ReqConsultaNotas.Cabecalho;
import br.com.tti.sefaz.nfes.schemas.saopaulo.TpCPFCNPJ;
import br.com.tti.sefaz.sender.campinas.LoteRpsService;
import br.com.tti.sefaz.sender.campinas.LoteRpsServiceLocator;
import br.com.tti.sefaz.sender.campinas.LoteRpsSoapBindingStub;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.util.ReadFile;

public class SorocabaTest {

	public static void main(String[] args) {

		try {

			SenderDispatchController.ajustarPropriedadesFwd(
					"C:\\TTIRec\\certificados\\myroot.jks",
					"C:\\TTIRec\\certificados\\tome_equipamentos_2014.pfx",
					"crhisn", "tome1234");

			LoteRpsServiceLocator locator = new LoteRpsServiceLocator();

			URL endpoint = new URL(
					"http://www.issdigitalsod.com.br/WsNFe2/LoteRps.jws");
			LoteRpsSoapBindingStub stub = new LoteRpsSoapBindingStub(endpoint,
					locator);
			String mensagemXml = ReadFile
					.readFile("C:\\remessa_consultanotas-inicial1-01-01-2010ate30-12-2011-20110303.xml");

			XMLGenerator gen = new XMLGenerator(
					"br.com.tti.sefaz.nfes.schemas.ginfes");
			ReqConsultaNotas consulta = new ReqConsultaNotas();
			Cabecalho cc = new Cabecalho();
			cc.setVersao(1);
			cc.setCodCidade(7145);
			cc.setCPFCNPJRemetente("44384832000124");
			cc.setInscricaoMunicipalPrestador(123456);
			cc.setDtInicio("2014-01-26");
			cc.setDtFim("2014-01-28");
			cc.setNotaInicial(1);
			consulta.setCabecalho(cc);

			mensagemXml = gen.toXMLString(consulta);

			mensagemXml = mensagemXml
					.replace(
							"<ns3:ReqConsultaNotas",
							"<ns3:ReqConsultaNotas xmlns:tipos=\"http://localhost:8080/WsNFe2/tp\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://localhost:8080/WsNFe2/lote http://localhost:8080/WsNFe2/xsd/ReqConsultaNotas.xsd\"");

			mensagemXml = mensagemXml.replace("ns3", "ns1");
			System.out.println(mensagemXml);

			String ss = stub.consultarNota(mensagemXml);
			System.out.println(ss);

			/*
			 * LoteRpsServiceLocator locator = new LoteRpsServiceLocator();
			 * String r = locator.getLoteRps().consultarLote("");
			 * System.out.println(r);
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
