package br.com.tti.sefaz.xmlgenerate;

import java.util.Vector;

public interface XMLWrapperReturnCallBack {

	public String getCStat();

	public String getXMotivo();

	public String getXNRec();

	public Vector<XMLWrapperProtocol> getProt();

	public String getXml();

}
