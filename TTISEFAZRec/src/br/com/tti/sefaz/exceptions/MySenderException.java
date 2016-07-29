package br.com.tti.sefaz.exceptions;

import java.rmi.RemoteException;

public class MySenderException extends RemoteException {
	public static String ERROR_SERVICE = "error creating service: ";
	public static String ERROR_PORT = "error creating port: ";
	public static String ERROR_CALL_SERVICE = "error call service: ";
	public static String ERROR_DISPATCH = "error creating dispatch: ";

	private static final long serialVersionUID = 1L;
	private String msn;

	public MySenderException(RemoteException re, String msn) {
		this.msn = msn;
		this.setStackTrace(re.getStackTrace());

	}

	public MySenderException(Exception re, String msn) {
		this.msn = msn;
		this.setStackTrace(re.getStackTrace());

	}

	public MySenderException(String msn) {
		this.msn = msn;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public static void teste1() throws RemoteException {
		teste2();
	}

	public static void teste2() throws RemoteException {
		throw new MySenderException("ds");
	}

	public static void main(String[] args) {
		try {
			teste1();
		} catch (RemoteException e) {
			if (e instanceof MySenderException) {
				MySenderException ms = MySenderException.class.cast(e);
				System.out.println(ms.getMsn());
			}

		}
	}
}
