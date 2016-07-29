/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfeimpressao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * 
 * @author taragona
 */
public interface TaragonaInt extends Remote {
	String recebeNFeXML(String nota) throws RemoteException;

	String recebeNFeTEXTO(String nota) throws RemoteException;

	String recebeOutrosXML(String outros) throws RemoteException;

	String recebeOutrosTEXTO(String outros) throws RemoteException;

	String cancelaNFe(String id) throws RemoteException;

	String cancelaNFe(String numero, Date dataEmissao, String serie)
			throws RemoteException;

	String inutilizaNFe(String numero) throws RemoteException;

	String imprimeDanfe(String idNota, String estado, String xml)
			throws RemoteException;

}
