package br.com.tti.sefaz.xmlgenerate;

import java.util.Hashtable;

public interface XMLQueryCreator {

	public String createXMLQuery(String cnpj, String municipio,
			Hashtable<String, Object> params);

	public <T> T convertResult(String xml, Class<T> classreturn);
}
