package br.com.tti.sefaz.remote;

import java.io.Serializable;

public class ConnectorConfig implements Serializable {
	private String connectorClass;

	public String getConnectorClass() {
		return connectorClass;
	}

	public void setConnectorClass(String connectorClass) {
		this.connectorClass = connectorClass;
	}

}
