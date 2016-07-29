package test;

import java.io.FileWriter;

import br.com.taragona.nfe.xmlgenerator.XMLGenerator;
import br.com.tti.sefaz.nfes.schemas.saopaulo.PedidoConsultaNFePeriodo;
import br.com.tti.sefaz.nfes.schemas.saopaulo.TpCPFCNPJ;
import br.com.tti.sefaz.nfes.schemas.saopaulo.PedidoConsultaNFePeriodo.Cabecalho;
import br.com.tti.sefaz.sender.dispatch.SenderDispatchController;
import br.com.tti.sefaz.signer.Main;
import br.com.tti.sefaz.xml.XMLValidator;
import br.gov.sp.prefeitura.nfe.ConsultaNFeEmitidasRequest;
import br.gov.sp.prefeitura.nfe.ConsultaNFeRecebidasRequest;
import br.gov.sp.prefeitura.nfe.ConsultaNFeRecebidasResponse;
import br.gov.sp.prefeitura.nfe.LoteNFe;
import br.gov.sp.prefeitura.nfe.LoteNFeSoap;

public class NFSeTest {

	public static void main(String[] args) throws Exception {

		SenderDispatchController.ajustarPropriedadesFwd(
				"C:\\TTIRec\\certificados\\myroot.jks",
				"C:\\TTIRec\\certificados\\tome_equipamentos_2014.pfx",
				"crhisn", "tome1234");

		Main s = new Main(
				"C:\\TTIRec\\certificados\\tome_equipamentos_2014.pfx",
				"tome1234");

		XMLGenerator gen = new XMLGenerator("br.com.tti.sefaz.nfes.schemas.saopaulo");

		PedidoConsultaNFePeriodo periodo = new PedidoConsultaNFePeriodo();

		Cabecalho cc = new Cabecalho();
		cc.setVersao(1);
		cc.setNumeroPagina(1);
		TpCPFCNPJ cnpj = new TpCPFCNPJ();
		cnpj.setCNPJ("44384832000124");
		cc.setCPFCNPJ(cnpj);
		TpCPFCNPJ cnpjrem = new TpCPFCNPJ();
		cnpjrem.setCNPJ("44384832000124");
		cc.setCPFCNPJRemetente(cnpjrem);

		cc.setDtInicio("2014-01-26");
		cc.setDtFim("2014-01-28");

		// cc.setDtInicio(value);

		periodo.setCabecalho(cc);

		String value = gen.toXMLString(periodo);

		System.out.println(value);

		value = value
				.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
						"");
		/*
		 * value = value.replace(":ns3", ""); value = value.replace("ns3:", "");
		 * value = value.replace(
		 * "xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" ", "");
		 */

		System.out.println("pre sign: " + value);

		value = s.assina(value, "Cabecalho");

		System.out.println("\n1 ===> " + value);

		try {
			XMLValidator val = new XMLValidator(
					"C:\\Users\\Administrador.WIN-SERVIDOR\\Documents\\ws\\schemas\\PedidoConsultaNFePeriodo_v01.xsd");

			val.validateXml(value);
		} catch (Exception e) {
			e.printStackTrace();
		}

		FileWriter fw = new FileWriter("C:\\nfsesigned2.xml");
		fw.write(value);
		fw.close();

		LoteNFe service = new LoteNFe();
		LoteNFeSoap port = service.getLoteNFeSoap();
		
		
		
		ConsultaNFeRecebidasRequest parameters = new ConsultaNFeRecebidasRequest();
		
		
		parameters.setMensagemXML(value);
		parameters.setVersaoSchema(1);
		
		
		ConsultaNFeRecebidasResponse response = port
				.consultaNFeRecebidas(parameters);
		System.out.println(response.getRetornoXML());

		FileWriter fw2 = new FileWriter("C:\\notas.xml");
		fw2.write(response.getRetornoXML());
		fw2.close();

		ConsultaNFeEmitidasRequest consultaemit = new ConsultaNFeEmitidasRequest();
		consultaemit.setMensagemXML(value);
		port.consultaNFeEmitidas(consultaemit);
	}
}
