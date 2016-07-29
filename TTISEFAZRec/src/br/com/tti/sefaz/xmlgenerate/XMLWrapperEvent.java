package br.com.tti.sefaz.xmlgenerate;

import java.util.Vector;

public interface XMLWrapperEvent {

	public String getCStat();

	public String getXMotivo();

	public String getCh();

	public String getProt();

	public String getDhSefaz();

	public String getTpAmb();

	public String getXmlProtocol();

	public String getEmail();

	public String getNSeqEvento();

	public String getCOrgao();
	
	public Vector<XMLWrapperEventProtocol> getProtocols();
}
