package br.com.tti.sefaz.remote;

import java.io.Serializable;

public class SystemConfig implements Serializable {
	private String modoDefault;
	private String pathFiles;

	public String getModoDefault() {
		return modoDefault;
	}

	public void setModoDefault(String modoDefault) {
		this.modoDefault = modoDefault;
	}

	public String getPathFiles() {
		return pathFiles;
	}

	public void setPathFiles(String pathFiles) {
		this.pathFiles = pathFiles;
	}

}
