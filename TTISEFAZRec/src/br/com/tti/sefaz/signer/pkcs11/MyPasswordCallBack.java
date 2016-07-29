package br.com.tti.sefaz.signer.pkcs11;

import javax.security.auth.callback.PasswordCallback;

public class MyPasswordCallBack extends PasswordCallback{

	public MyPasswordCallBack(String prompt, boolean echoOn) {
		super(prompt, echoOn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
