package br.com.tti.sefaz.sender.xml.schema;

import java.io.FileWriter;

import br.com.tti.sefaz.xml.XMLGenerator;

public class TesteXML {

	public void teste() {

		ConfiguracaoServico s = new ConfiguracaoServico();
		s.setUrl("http:///");
		s.setNameSpace("http://namespace");
		s.setNomePorta("NFeRecepcap");
		s.setId("Recepcao");
		s.setNomeOperacao("nfeRecepcaoLote");
		s.setTagResposta("enviNfe");

		NFeServico servico = new NFeServico();
		servico.getServico().add(s);
		servico.getServico().add(s);

		servico.setTipo(new String[] { "normal" });
		servico.setUf(new String[] { "SP" });

		NFeServicos servicos = new NFeServicos();
		servicos.getNFeServico().add(servico);
		servicos.getNFeServico().add(servico);

		XMLGenerator xmls = new XMLGenerator(
				"br.com.taragona.nfe.util.xml.teste");
		try {
			FileWriter fw = new FileWriter("/home/cnoriega/servicos.xml");
			fw.write(xmls.toXMLString(servicos));
			fw.close();
			System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		TesteXML t = new TesteXML();
		t.teste();
	}
}
