package br.com.tti.sefaz.systemconfig;

public class SystemProperties {

	public static String SOAP_12 = "soap12";
	public static String SOAP_11 = "soap1";

	public static String AMBIENT_PRODUCAO = "producaov200";
	public static String AMBIENT_HOMOLOGACAO = "homologacaov200";

	public static String AMBIENT_SCAN = "scan";
	public static String AMBIENT_DPEC = "dpec";

	public static String ID_SERVICO_RECEPCAO = "Recepcao";
	public static String ID_SERVICO_RETRECEPCAO = "RetRecepcao";
	public static String ID_SERVICO_CONSULTA = "Consulta";
	public static String ID_SERVICO_CANCELAMENTO = "Cancelamento";
	public static String ID_SERVICO_INUTILIZACAO = "Inutilizacao";
	public static String ID_SERVICO_ESTADO_SERVICOS = "StatusServicos";

	public static String ID_SERVICO_RECEPCAO_EVENTO = "RecepcaoEvento";
	public static String ID_SERVICO_DOWNLOAD_NF = "DownloadNF";
	public static String ID_SERVICO_CONSULTA_DEST = "ConsultaDest";

	public enum MODO_OP {
		CONTINGENCE, NORMAL, CONTINGENCE_DPEC, CONTINGENCE_SCAN
	};

	public enum SYSTEM {
		TTI_NFE, TTI_CTE
	};

	public enum ARCHITECTURE {
		REMOTE, LOCAL
	};

	public enum XML_STATE {
		GERADA, // 0
		ERRO_SCHEMA_XML, // 1
		ERRO_SCHEMA_LOTE, // 2
		TENTANDO_ENVIO, // 3
		ERRO_ENVIO, // 4
		ERRO_COMUNICACAO_SEFAZ, // 5
		ERRO_COMUNICACAO_LOCAL, // 6
		ENVIADA, // 7
		AUTORIZADA, // 8
		REJEITADA, // 9
		DENEGADA, // 10
		CANCELADA, // 12
		INUTLIZADA, // 13
		ERRO_CANCELADA, // 14
		ERRO_INUT, //
		CANCELADA_CONTINGENCIA, ERRO_VALIDACAO, INUT_CONTINGENCIA
	};

	public enum TYPE_PRINT {
		IMPRESSA_NORMAL, IMPRESSA_CONT, IMPRESSA_DPEC,
	};

	public static enum SET_STATE {
		ENVIADO, CONFIRMADO_ENVIO, ERRO_ENVIO, GERADO, PROCESSADO, TEMPO_PROCESSAMENTO_LIMITE, TENTANDO_ENVIO, ERRO_ESQUEMA
	};

	public static enum RECIBE_STATE {
		CHECADO, PENDENTE
	};

	public static enum SIGNER_TYPE {
		PKCS11_SIGNER, PKCS12_SIGNER
	};

	public static void main(String[] args) {

	}
}
