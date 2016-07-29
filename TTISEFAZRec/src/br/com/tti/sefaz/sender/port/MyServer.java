package br.com.tti.sefaz.sender.port;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class MyServer extends Service{

	public MyServer(URL wsdlDocumentLocation, QName serviceName) {
		super(wsdlDocumentLocation, serviceName);
	}	
}
