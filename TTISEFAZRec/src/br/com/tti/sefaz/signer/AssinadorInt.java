/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.tti.sefaz.signer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author taragona
 */
public interface AssinadorInt extends Remote {
    String assina(String arquivo, String tag) throws RemoteException;
}
