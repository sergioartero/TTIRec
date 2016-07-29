package br.com.tti.sefaz.remote;

import java.io.Serializable;

public class SchemaVersionConfig implements Serializable{
	private String uf;
	private String schemaName;
	private String value;

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
