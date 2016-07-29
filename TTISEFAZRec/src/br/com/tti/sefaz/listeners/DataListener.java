package br.com.tti.sefaz.listeners;

import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.XMLData;
import br.com.tti.sefaz.remote.events.TTIEvent;

public interface DataListener {

	public void process(XMLData data);

	public void process(SetData data);

	public void process(TTIEvent event);

}
