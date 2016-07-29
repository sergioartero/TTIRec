package br.com.tti.sefaz.querier;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.xmlgenerate.XMLWrapperQuery;

public class QuerierRemoteImpl implements QuerierRemote {

	private QuerierInstance qinst;

	public QuerierRemoteImpl() {
		this.qinst = new QuerierInstance();
	}

	@Override
	public XMLWrapperQuery makeQueryComplete(String uf, String key)
			throws RemoteException {
		XMLWrapperQuery nonser = this.qinst.makeQueryComplete(uf, key);

		ResultQuery rq = new ResultQuery();

		rq.setCh(nonser.getCh());
		rq.setCstat(nonser.getCStat());
		rq.setDhsefaz(nonser.getDhSefaz());
		rq.setProt(nonser.getProt());
		rq.setProtocolo(nonser.getXmlProtocol());
		rq.setTpamb(nonser.getTpAmb());
		rq.setXml(nonser.getXml());
		rq.setXmotivo(nonser.getXMotivo());
		rq.setProtCancel(nonser.getProtCancel());

		try {
			System.gc();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return rq;

	}

	public static void main(String[] args) {
		try {

			Registry reg = LocateRegistry.getRegistry("localhost");
			QuerierRemoteImpl q = new QuerierRemoteImpl();
			QuerierRemote stub = (QuerierRemote) UnicastRemoteObject
					.exportObject(q, 0);
			reg.rebind("querier", stub);
			MyLogger.getLog().info(
					"Consulta versao para NF-e 3.10, CT-e e NFS-e");
			MyLogger.getLog().info("Consulta Inicializado!!");

			// ProxyConfigurator.configureProxy();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isAlive() throws RemoteException {
		return true;
	}

	public static QuerierRemote INSTANCE = null;

	public static QuerierRemote getQuerierReference() throws Exception {
		if (INSTANCE == null) {
			MyLogger.getLog().info("Procurando Consulta!!");
			Registry reg = LocateRegistry.getRegistry("localhost");
			INSTANCE = (QuerierRemote) reg.lookup("querier");
		}
		return INSTANCE;
	}

}
