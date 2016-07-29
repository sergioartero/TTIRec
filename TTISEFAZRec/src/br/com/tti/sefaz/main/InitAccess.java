package br.com.tti.sefaz.main;

import br.com.tti.sefaz.externaldbaccess.LaunchExternalAccess;

public class InitAccess {
	public static void main(String[] args) {
		LaunchExternalAccess l = new LaunchExternalAccess();
		l.initExternalAccess();
	}
}
