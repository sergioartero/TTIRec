package br.com.tti.sefaz.printer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import nfeimpressao.TaragonaInt;
import br.com.tti.sefaz.systemconfig.SystemProperties;

public interface XMLPrinter extends Remote {

	public void printXml(String keyXml, String xml,
			SystemProperties.MODO_OP modo) throws RemoteException;

	public void rePrintXml(String keyXml, SystemProperties.MODO_OP modo)
			throws RemoteException;

	public void registrarImpressao(TaragonaInt imp) throws RemoteException;

}
