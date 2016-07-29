package br.com.tti.sefaz.util;

import java.util.Vector;

public class MainParameters {

	private static String SEPARADOR = ";";
	private static Vector<String> cnpjs = new Vector<String>();
	private static Vector<String> ips = new Vector<String>();
	private static Vector<String> dirs = new Vector<String>();
	private static String trustStore;
	private static String keyStore;
	private static String pfx;
	private static String xml;
	private static String outros;
	private static String config;
	private static String log;
	private static int quantidade;
	private static boolean local = false;
	private static boolean pedirSenha = false;
	private static boolean rmiregistry = false;
	private static boolean debug = false;
	private static boolean console = false;
	private static boolean externaldb = false;
	private static boolean activeconnector = false;
	private static boolean pastas = false;
	private static boolean localdb = false;
	private static boolean codigobarras = false;
	private static boolean semseparacao = false;
	private static boolean utf = false;
	private static boolean iso = false;
	private static boolean gerartxt = false;
	private static boolean usar_ad = false;

	public static void processArguments(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals("-cnpj")) {
				String[] cnpjs_s = args[i + 1].split(SEPARADOR);
				for (String cnpj : cnpjs_s) {
					cnpjs.add(cnpj);
				}
				i++;
			}

			if (arg.equals("-n")) {
				quantidade = Integer.parseInt(args[i + 1]);
				i++;
			}

			if (arg.equals("-ip")) {
				String[] ips_s = args[i + 1].split(SEPARADOR);
				for (String ip : ips_s) {
					ips.add(ip);
				}
				i++;
			}

			if (arg.equals("-dirs")) {
				String[] ips_s = args[i + 1].split(SEPARADOR);
				for (String ip : ips_s) {
					dirs.add(ip);
				}
				i++;
			}

			if (arg.equals("-t")) {
				trustStore = args[i + 1];
				i++;
			}

			if (arg.equals("-log")) {
				log = args[i + 1];
				i++;
			}

			if (arg.equals("-k")) {
				keyStore = args[i + 1];
				i++;
			}

			if (arg.equals("-pfx")) {
				pfx = args[i + 1];
				i++;
			}

			if (arg.equals("-xml")) {
				xml = args[i + 1];
				i++;
			}

			if (arg.equals("-cfg")) {
				config = args[i + 1];
				i++;
			}

			if (arg.equals("-o")) {
				outros = args[i + 1];
				i++;
			}

			if (arg.equals("-local")) {
				local = true;
			}

			if (arg.equals("-debug")) {
				debug = true;
			}

			if (arg.equals("-offconsole")) {
				console = true;
			}

			if (arg.equals("-p")) {
				pedirSenha = true;
			}

			if (arg.equals("-rmiregistry")) {
				rmiregistry = true;
			}

			if (arg.equals("-externaldb")) {
				externaldb = true;
			}

			if (arg.equals("-connector")) {
				activeconnector = true;
			}

			if (arg.equals("-localdb")) {
				localdb = true;
			}

			if (arg.equals("-pastas")) {
				pastas = true;
			}

			if (arg.equals("-codigobarras")) {
				codigobarras = true;
			}

			if (arg.equals("-semseparacao")) {
				semseparacao = true;
			}

			if (arg.equals("-utf")) {
				utf = true;
			}

			if (arg.equals("-iso")) {
				iso = true;
			}

			if (arg.equals("-gerartxt")) {
				gerartxt = true;
			}

			if (arg.equals("-usar_ad")) {
				usar_ad = true;
			}
		}
	}

	public static boolean isCodigobarras() {
		return codigobarras;
	}

	public static boolean isSemseparacao() {
		return semseparacao;
	}

	public static void setSemseparacao(boolean semseparacao) {
		MainParameters.semseparacao = semseparacao;
	}

	public static void main(String[] args) {
		processArguments(args);
	}

	public static Vector<String> getIps() {
		return ips;
	}

	public static String getTrustStore() {
		return trustStore;
	}

	public static String getKeyStore() {
		return keyStore;
	}

	public static String getPfx() {
		return pfx;
	}

	public static String getXml() {
		return xml;
	}

	public static String getOutros() {
		return outros;
	}

	public static boolean isLocal() {
		return local;
	}

	public static String getConfig() {
		return config;
	}

	public static boolean isPedirSenha() {
		return pedirSenha;
	}

	public static Vector<String> getCnpjs() {
		return cnpjs;
	}

	public static int getQuantidade() {
		return quantidade;
	}

	public static boolean isRmiregistry() {
		return rmiregistry;
	}

	public static String getLog() {
		return log;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static boolean isConsole() {
		return console;
	}

	public static Vector<String> getDirs() {
		return dirs;
	}

	public static boolean isExternaldb() {
		return externaldb;
	}

	public static boolean isActiveconnector() {
		return activeconnector;
	}

	public static boolean isLocaldb() {
		return localdb;
	}

	public static boolean isPastas() {
		return pastas;
	}

	public static boolean isUtf() {
		return utf;
	}

	public static void setUtf(boolean utf) {
		MainParameters.utf = utf;
	}

	public static boolean isIso() {
		return iso;
	}

	public static void setIso(boolean iso) {
		MainParameters.iso = iso;
	}

	public static boolean isGerartxt() {
		return gerartxt;
	}

	public static boolean isUsar_ad() {
		return usar_ad;
	}

}
