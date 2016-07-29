/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nfearaujo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author taragona
 */
public interface AraujoInt extends Remote {
    public void enviarNota(String xml) throws RemoteException;
    public void cancelarNota(String id, String motivo) throws RemoteException;
    public void reimprimirNota(String id) throws RemoteException;
    public boolean estaAtivo() throws RemoteException;
    public boolean estaContingencia(String cnpj) throws RemoteException;
}
