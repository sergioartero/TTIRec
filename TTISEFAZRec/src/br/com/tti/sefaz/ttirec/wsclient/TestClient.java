package br.com.tti.sefaz.ttirec.wsclient;

import java.util.List;

public class TestClient {

	public static void main(String[] args) {
		TTIWSEndpointService ws = new TTIWSEndpointService();
		TTIWSEndpoint port = ws.getTTIWSEndpointPort();
		try {
			TRetEnvEvento rr = port.enviarEvento(
					"35130611245802000188550020000137281011167221", 2, "",
					"2013-05-12T12:12:12-03:00");

			System.out.println(rr.getXMotivo());
			List<TretEvento> list = rr.getRetEvento();
			for (TretEvento r : list) {
				System.out.println(r.getInfEvento().getXMotivo());
			}

		} catch (Exception_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
