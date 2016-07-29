package br.com.tti.sefaz.test;

import java.rmi.RemoteException;

import br.com.tti.sefaz.querier.QuerierRemote;
import br.com.tti.sefaz.querier.QuerierRemoteImpl;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public class TesteQuerier {

	public static void main(String[] args) {
		try {
			QuerierRemote q = QuerierRemoteImpl.getQuerierReference();
			XMLWrapperQuery result = q.makeQueryComplete("35",
					"NFe15130663878896000550550010000016321000016327");
			System.out.println(result.getCh());
			System.out.println(result.getXMotivo());
			System.out.println(result.getDhSefaz());
			System.out.println(result.getProt());
			System.out.println(result.getXml());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
