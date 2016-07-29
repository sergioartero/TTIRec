package br.com.tti.sefaz.xmlgenerate;

import br.com.tti.sefaz.systemconfig.SystemParameters;
import br.com.tti.sefaz.systemconfig.SystemProperties;
import br.com.tti.sefaz.xmlgenerate.cte.XMLWrapperCancelCTeImpl;
import br.com.tti.sefaz.xmlgenerate.cte.XMLWrapperQueryCTeImpl;
import br.com.tti.sefaz.xmlgenerate.cte.XMLWrapperReturnCTeImpl;
import br.com.tti.sefaz.xmlgenerate.cte.XMLWrapperReturnCallBackCTeImpl;
import br.com.tti.sefaz.xmlgenerate.cte.XMLWrapperStatusImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperCancleNFeImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperEventNFeImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperInutNFeImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperQueryNFeEventImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperQueryNFeImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperRetNFeDownImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperReturnCallbackNFeImpl;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperReturnNFeImpl;

public class XMLWrapperFactory {

	public static XMLWrapperReturnSend createReturnSendWrapper(String xmlReturn)
			throws Exception {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			XMLWrapperReturnNFeImpl wrapper = new XMLWrapperReturnNFeImpl(
					xmlReturn);
			return wrapper;
		}
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			XMLWrapperReturnCTeImpl wrapper = new XMLWrapperReturnCTeImpl(
					xmlReturn);
			return wrapper;
		}
		return null;
	}

	public static XMLWrapperReturnCallBack createReturnCallbackWrapper(
			String xmlReturn) throws Exception {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			XMLWrapperReturnCallBack wrapper = new XMLWrapperReturnCallbackNFeImpl(
					xmlReturn);
			return wrapper;
		}
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			XMLWrapperReturnCallBack wrapper = new XMLWrapperReturnCallBackCTeImpl(
					xmlReturn);
			return wrapper;
		}
		return null;
	}

	public static XMLWrapperQuery createReturnQueryWrapper(String xmlReturn)
			throws Exception {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			XMLWrapperQuery wrapper = new XMLWrapperQueryNFeEventImpl(xmlReturn);
			return wrapper;
		}
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			XMLWrapperQuery wrapper = new XMLWrapperQueryCTeImpl(xmlReturn);
			return wrapper;
		}

		return null;
	}

	public static XMLWrapperCancel createReturnCancelWrapper(String xmlReturn)
			throws Exception {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			XMLWrapperCancel wrapper = new XMLWrapperCancleNFeImpl(xmlReturn);
			return wrapper;
		}
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_CTE)) {
			XMLWrapperCancel wrapper = new XMLWrapperCancelCTeImpl(xmlReturn);
			return wrapper;
		}
		return null;
	}

	public static XMLWrapperInut createReturnInutWrapper(String xmlReturn)
			throws Exception {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			XMLWrapperInut wrapper = new XMLWrapperInutNFeImpl(xmlReturn);
			return wrapper;
		}
		return null;
	}

	public static XMLWrapperStatus createStatusWrapper(String xmlReturn)
			throws Exception {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			XMLWrapperStatus wrapper = new XMLWrapperStatusImpl(xmlReturn);
			return wrapper;
		}
		return null;
	}

	public static XMLWrapperEvent createEventWrapper(String xml) {
		if (SystemParameters.system.equals(SystemProperties.SYSTEM.TTI_NFE)) {
			XMLWrapperEvent wrapper = new XMLWrapperEventNFeImpl(xml);
			return wrapper;
		}
		return null;
	}

	public static XMLWrapperRetNFeDown createRetNFeDown(String xml) {
		try {
			return new XMLWrapperRetNFeDownImpl(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
