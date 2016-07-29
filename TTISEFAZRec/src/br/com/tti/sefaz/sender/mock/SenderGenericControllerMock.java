package br.com.tti.sefaz.sender.mock;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.xml.bind.JAXBElement;

import br.com.tti.sefaz.classes.recepcao.TCTe;
import br.com.tti.sefaz.classes.recepcao.TEnviCTe;
import br.com.tti.sefaz.classes.recepcao.TRetEnviCTe;
import br.com.tti.sefaz.classes.retrecepcao.TConsReciCTe;
import br.com.tti.sefaz.classes.retrecepcao.TProtCTe;
import br.com.tti.sefaz.classes.retrecepcao.TRetConsReciCTe;
import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.sender.SenderGenericController;
import br.com.tti.sefaz.systemconfig.SystemConfigInterface;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.xml.XMLGenerator;

public class SenderGenericControllerMock extends SenderGenericController {

	private XMLGenerator xmlsrec;
	private XMLGenerator xmlsretrec;
	private Random random = new Random();

	private Hashtable<String, Vector<String>> set_xml;
	private Hashtable<String, Integer> counter;

	public SenderGenericControllerMock(SystemConfigInterface senderConfig) {
		super(senderConfig);
		this.xmlsrec = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.recepcao");
		this.xmlsretrec = XMLGenerator
				.createXMLGenerator("br.com.tti.sefaz.classes.retrecepcao");

		this.set_xml = new Hashtable<String, Vector<String>>();
		this.counter = new Hashtable<String, Integer>();
	}

	@Override
	public String sendXMLMessage(String idServico, String header, String data,
			Hashtable prop) throws RemoteException {
		String ambient = (String) prop.get("AMBIENT");
		String uf = (String) prop.get("UF");

		MyLogger.getLog().finest("uf:" + uf);
		MyLogger.getLog().finest("amb:" + ambient);

		MyLogger.getLog().finest("XML:" + data);

		if (idServico.equals(SystemProperties.ID_SERVICO_RECEPCAO)) {
			return this.returnSetReceived(data);
		}
		if (idServico.equals(SystemProperties.ID_SERVICO_RETRECEPCAO)) {
			return this.returnSetRetReceived(data);
		}

		return null;
	}

	private String generateNumber() {
		return Math.abs(this.random.nextInt()) + "";
	}

	public String returnSetReceived(String xml) {

		int pos1 = xml.indexOf("<enviCTe");
		int pos2 = xml.indexOf("</enviCTe>");

		xml = xml.substring(pos1, pos2 + "</enviCTe>".length());

		try {
			JAXBElement<TEnviCTe> obj = (JAXBElement<TEnviCTe>) this.xmlsrec
					.toObject(xml);

			Vector<String> receivedxml = new Vector<String>();

			TEnviCTe lote = obj.getValue();
			List<TCTe> xmls = lote.getCTe();
			for (TCTe tcTe : xmls) {
				receivedxml.add(tcTe.getInfCte().getId().replace("CTe", ""));
			}

			String nrec = this.generateNumber();
			this.set_xml.put(nrec, receivedxml);

			MyLogger.getLog().info("PUT NUMBER RECIBO:" + nrec);
			this.counter.put(nrec, 1);

			TRetEnviCTe ret = new TRetEnviCTe();
			TRetEnviCTe.InfRec info = new TRetEnviCTe.InfRec();
			info.setNRec(nrec);
			ret.setCStat("103");
			ret.setTpAmb("2");
			ret.setInfRec(info);

			String xmlret = this.xmlsrec.toXMLString(ret);

			MyLogger.getLog().info(xmlret);

			return xmlret + "</cteRecepcaoLoteResult>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public String returnSetRetReceived(String xml) {

		int pos1 = xml.indexOf("<consReciCTe");
		int pos2 = xml.indexOf("</consReciCTe>");

		xml = xml.substring(pos1, pos2 + "</consReciCTe>".length());

		MyLogger.getLog().finest("parsing:" + xml);

		try {
			// TODO implement this method
			JAXBElement<TConsReciCTe> obj = (JAXBElement<TConsReciCTe>) this.xmlsretrec
					.toObject(xml);
			TConsReciCTe consulta = obj.getValue();

			String xmlret = this.xmlsretrec
					.toXMLString(fazerConsulta(consulta));

			xmlret = xmlret.replace("tRetConsReciCTe", "retConsReciCTe");
			xmlret = xmlret.replace(
					" xmlns=\"http://www.w3.org/2000/09/xmldsig#\"", "");
			xmlret = xmlret.replace(":ns2", "");
			xmlret = xmlret.replace("ns2:", "");

			MyLogger.getLog().info(xmlret);
			return xmlret;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public TRetConsReciCTe fazerConsulta(TConsReciCTe consulta) {
		Vector<String> xmls = this.set_xml.get(consulta.getNRec());

		TRetConsReciCTe ret = new TRetConsReciCTe();

		MyLogger.getLog().info("FIND NUMBER RECIBO:" + consulta.getNRec());

		Integer tentatives = this.counter.get(consulta.getNRec());
		if (tentatives <= 3) {
			ret.setCStat("105");
			ret.setXMotivo("Lote em processamento");
			tentatives++;
			this.counter.put(consulta.getNRec(), tentatives);
			return ret;
		}

		ret.setCStat("104");
		ret.setXMotivo("Lote processado");
		ret.setNRec(consulta.getNRec());

		List<TProtCTe> lista = ret.getProtCTe();

		for (String idxml : xmls) {
			TProtCTe prot = new TProtCTe();
			TProtCTe.InfProt info = new TProtCTe.InfProt();

			info.setCStat("100");
			info.setXMotivo("Autorizado o uso da NF-e");

			info.setChCTe(idxml);
			info.setTpAmb("123");
			info.setVerAplic("123");
			info.setNProt("123456789");

			prot.setInfProt(info);

			lista.add(prot);
		}
		return ret;
	}
}
