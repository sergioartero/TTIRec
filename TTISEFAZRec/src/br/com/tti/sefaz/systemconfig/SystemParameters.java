package br.com.tti.sefaz.systemconfig;

import br.com.tti.sefaz.systemconfig.SystemProperties.ARCHITECTURE;
import br.com.tti.sefaz.systemconfig.SystemProperties.SIGNER_TYPE;
import br.com.tti.sefaz.systemconfig.SystemProperties.SYSTEM;

public class SystemParameters {
	public static long INTERVAL_SEND = 2000;

	public static long INTERVAL_CHECK = 2000;

	public static int MAX_NUMBER_TENTATIVES_SEND = 3;

	public static int MAX_NUMBER_TENTATIVES_QUERY = 3;

	public static int MAX_NUMBER_TENTATIVES_CANCEL_INUT = 3;

	public static int MAX_NUMBER_TENTATIVES_CHECK = 10;

	public static int SET_EMPTY_SIZE = 100;

	public static SYSTEM system = SYSTEM.TTI_NFE;

	public static ARCHITECTURE architecture = ARCHITECTURE.LOCAL;

	public static SIGNER_TYPE signer = SIGNER_TYPE.PKCS12_SIGNER;
}
