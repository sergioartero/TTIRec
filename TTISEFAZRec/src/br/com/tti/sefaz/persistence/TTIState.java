package br.com.tti.sefaz.persistence;

import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;

public class TTIState {
	private XML_STATE state;
	private int sefazState;

	public TTIState(XML_STATE state, int sefazState) {
		super();
		this.state = state;
		this.sefazState = sefazState;
	}

	public XML_STATE getState() {
		return state;
	}

	public void setState(XML_STATE state) {
		this.state = state;
	}

	public int getSefazState() {
		return sefazState;
	}

	public void setSefazState(int sefazState) {
		this.sefazState = sefazState;
	}

	@Override
	public String toString() {
		return this.state.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TTIState) {
			TTIState new_name = (TTIState) obj;
			return this.state.equals(new_name.getState());
		}
		return false;
	}
}
