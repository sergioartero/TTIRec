package br.com.tti.sefaz.util;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import nfearaujo.AraujoInt;

public class Teste {

	public static void main(String[] args) {
		try {
			Registry rmis = LocateRegistry.getRegistry("localhost");
			AraujoInt inte = (AraujoInt) rmis.lookup("AraujoInt");
			inte.enviarNota("fasd");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
