package br.com.tti.sefaz.test;

import br.com.tti.sefaz.connector.ConnectorFactory;
import br.com.tti.sefaz.log.MyLogger;

public class CreateConnector {
	public static void main(String[] args) {
		try {
			ConnectorFactory
					.createConnector("br.com.tti.ifs.connector.JundiaiConnector");
		} catch (Exception e) {
			MyLogger.getLog().info(e.getLocalizedMessage());
		}
		MyLogger.getLog().info("Connector Iniciado");
	}
}
