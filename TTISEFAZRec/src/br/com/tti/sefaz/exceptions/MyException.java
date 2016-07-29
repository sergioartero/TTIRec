package br.com.tti.sefaz.exceptions;

public class MyException extends Exception {
	private static final long serialVersionUID = 1L;

	private String msn;

	public MyException(Exception e, String msn) {
		this.msn = msn;
		this.setStackTrace(e.getStackTrace());
	}

	public MyException(Exception e) {
		this.setStackTrace(e.getStackTrace());
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

}
