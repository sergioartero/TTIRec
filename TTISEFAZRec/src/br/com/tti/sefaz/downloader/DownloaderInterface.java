package br.com.tti.sefaz.downloader;

import java.rmi.Remote;
import java.util.Hashtable;

import br.com.tti.sefaz.xmlgenerate.XMLWrapperRetNFeDown;
import br.com.tti.sefaz.xmlgenerate.nfe.XMLWrapperRetNFeDownImpl;

public interface DownloaderInterface extends Remote {

	public String downloadNFe(String keyxml, Hashtable<String, Object> props)
			throws Exception;

	public XMLWrapperRetNFeDown downloadNFeWithProtocol(String keyxml,
			Hashtable<String, Object> props) throws Exception;

	public boolean isAlive() throws Exception;

}
