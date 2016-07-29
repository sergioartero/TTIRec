package br.com.tti.sefaz.event;

import br.com.tti.sefaz.persistence.EventData;

public interface EventManagerListener {

	public void process(EventData data) throws Exception;
}
