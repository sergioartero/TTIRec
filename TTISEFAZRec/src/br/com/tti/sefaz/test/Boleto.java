package br.com.tti.sefaz.test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import br.com.tti.sefaz.util.ReadFile;

public class Boleto {
	public static String obtemDadosBoletos(String dados) {

		System.out.println("XML Boleto:" + dados);

		DecimalFormat n_usa = (DecimalFormat) DecimalFormat
				.getInstance(Locale.US);
		DecimalFormat n_br = (DecimalFormat) DecimalFormat
				.getInstance(new Locale("pt", "BR"));
		n_usa.applyPattern("#########.##");
		n_br.applyPattern("###,###,##0.00");
		String trab = null;
		String trabf = null;
		SimpleDateFormat sdfUSA = new SimpleDateFormat();
		SimpleDateFormat sdfBR = new SimpleDateFormat();
		sdfUSA.applyPattern("yyyy-MM-dd");
		sdfBR.applyPattern("dd/MM/yyyy");
		Date dataTrab = null;
		String bancoNome = null;
		String bancoNumero = null;
		String notaRelacao = null;
		String notaSerie = null;
		String notaNumero = null;
		HashMap<String, String> logoForm = new HashMap<String, String>();
		ArrayList<String> logosaida = null;
		String labelLogo = null;

		int posi = 0;
		int posf = 0;

		StringBuffer sb = new StringBuffer();

		/*
		 * dados = dados.replaceAll("<BOLETOS><BANCONOME>", "<BOLETOS><BOLETO><BANCONOME>");
		 * dados = dados.replaceAll("</INSTRUCOES><BANCONOME>", "</INSTRUCOES></BOLETO><BOLETO><BANCONOME>");
		 * dados = dados.replaceAll("</INSTRUCOES><BOLETOS>", "</INSTRUCOES></BOLETO></BOLETOS>");
		 */
		while ((posf = dados.indexOf("<BOLETOS>", posf)) != -1) {

			System.out.println("BOLETO:" + posf);
			posi = dados.indexOf("<BANCONOME>", posf);
			posf = dados.indexOf("</BANCONOME>", posi);
			bancoNome = dados.substring(posi + "<BANCONOME>".length(), posf)
					.trim();
			posi = dados.indexOf("<BANCONUMERO>", posf);
			posf = dados.indexOf("</BANCONUMERO>", posi);
			bancoNumero = dados
					.substring(posi + "<BANCONUMERO>".length(), posf).trim();

			labelLogo = bancoNumero.substring(0, 3);
			logosaida = new ArrayList<String>();// logosBancos.get(labelLogo);
			if (logosaida != null) {
				if (logoForm.get(labelLogo) == null) {
					logoForm.put(labelLogo, "USADO");
					sb.append("/L" + labelLogo + "\n");
					sb.append("currentfile\n");
					sb.append("<< /Filter /SubFileDecode\n");
					sb
							.append("/DecodeParms << /EODCount 0 /EODString (*EOD*) >> >> /ReusableStreamDecode filter\n");
					for (String linhaLogo : logosaida) {
						sb.append(linhaLogo + "\n");
					}
					sb.append("*EOD*\n");
					sb.append("def\n");
				}
			}

			sb.append("orientacao_boleto\n");
			sb.append("BOLETO execform\n");
			if (logosaida != null) {
				sb.append("L" + labelLogo + " boleto_logo_1\n");
				sb.append("L" + labelLogo + " boleto_logo_2\n");
				sb.append("L" + labelLogo + " boleto_logo_3\n");
			}
			sb.append("(" + bancoNome + ") boleto_BancoNome\n");
			sb.append("(" + bancoNumero + ") boleto_BancoNumero\n");

			posi = dados.indexOf("<CEDENTE>", posf);
			posf = dados.indexOf("</CEDENTE>", posi);
			sb.append("("
					+ dados.substring(posi + "<CEDENTE>".length(), posf).trim()
					+ ") boleto_Cedente\n");
			posi = dados.indexOf("<AGENCIA_CODCEDENTE>", posf);
			posf = dados.indexOf("</AGENCIA_CODCEDENTE>", posi);
			sb.append("("
					+ dados.substring(posi + "<AGENCIA_CODCEDENTE>".length(),
							posf).trim() + ") boleto_Agencia_CodCedente\n");
			posi = dados.indexOf("<RELACAO>", posf);
			posf = dados.indexOf("</RELACAO>", posi);
			sb.append("("
					+ dados.substring(posi + "<RELACAO>".length(), posf).trim()
					+ ") boleto_Relacao\n");
			posi = dados.indexOf("<SERIE>", posf);
			posf = dados.indexOf("</SERIE>", posi);
			sb.append("("
					+ dados.substring(posi + "<SERIE>".length(), posf).trim()
					+ ") boleto_Serie\n");
			posi = dados.indexOf("<NUMERO>", posf);
			posf = dados.indexOf("</NUMERO>", posi);
			sb.append("("
					+ dados.substring(posi + "<NUMERO>".length(), posf).trim()
					+ ") boleto_Numero\n");
			posi = dados.indexOf("<SACADO>", posf);
			posf = dados.indexOf("</SACADO>", posi);
			sb.append("("
					+ dados.substring(posi + "<SACADO>".length(), posf).trim()
					+ ") boleto_Sacado\n");
			posi = dados.indexOf("<CIP>", posf);
			posf = dados.indexOf("</CIP>", posi);
			sb.append("("
					+ dados.substring(posi + "<CIP>".length(), posf).trim()
					+ ") boleto_CIP\n");
			posi = dados.indexOf("<DATAVENCIMENTO>", posf);
			posf = dados.indexOf("</DATAVENCIMENTO>", posi);
			trab = dados.substring(posi + "<DATAVENCIMENTO>".length(), posf)
					.trim();
			try {
				dataTrab = sdfUSA.parse(trab);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			trabf = sdfBR.format(dataTrab);
			sb.append("(" + trabf + ") boleto_DataVencimento\n");
			posi = dados.indexOf("<DATADOCUMENTO>", posf);
			posf = dados.indexOf("</DATADOCUMENTO>", posi);
			trab = dados.substring(posi + "<DATADOCUMENTO>".length(), posf)
					.trim();
			try {
				dataTrab = sdfUSA.parse(trab);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			trabf = sdfBR.format(dataTrab);
			sb.append("(" + trabf + ") boleto_DataDocumento\n");
			posi = dados.indexOf("<NUMDOCUMENTO>", posf);
			posf = dados.indexOf("</NUMDOCUMENTO>", posi);
			sb.append("("
					+ dados.substring(posi + "<NUMDOCUMENTO>".length(), posf)
							.trim() + ") boleto_NumDocumento\n");
			posi = dados.indexOf("<ESPECIEDOCUMENTO>", posf);
			posf = dados.indexOf("</ESPECIEDOCUMENTO>", posi);
			sb.append("("
					+ dados.substring(posi + "<ESPECIEDOCUMENTO>".length(),
							posf).trim() + ") boleto_EspecieDocumento\n");

			/*posi = dados.indexOf("<ACEITE>", posf);
			posf = dados.indexOf("</ACEITE>", posi);
			sb.append("( "
					+ dados.substring(posi + "<ACEITE>".length(), posf).trim()
					+ ") boleto_Aceite\n");*/

			posi = dados.indexOf("<DATAPROCESSAMENTO>", posf);
			posf = dados.indexOf("</DATAPROCESSAMENTO>", posi);
			trab = dados.substring(posi + "<DATAPROCESSAMENTO>".length(), posf)
					.trim();
			try {
				dataTrab = sdfUSA.parse(trab);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			trabf = sdfBR.format(dataTrab);
			sb.append("(" + trabf + ") boleto_DataProcessamento\n");
			posi = dados.indexOf("<NOSSONUMERO>", posf);
			posf = dados.indexOf("</NOSSONUMERO>", posi);
			sb.append("("
					+ dados.substring(posi + "<NOSSONUMERO>".length(), posf)
							.trim() + ") boleto_NossoNumero\n");
			posi = dados.indexOf("<CARTEIRA>", posf);
			posf = dados.indexOf("</CARTEIRA>", posi);
			sb.append("("
					+ dados.substring(posi + "<CARTEIRA>".length(), posf)
							.trim() + ") boleto_Carteira\n");
			posi = dados.indexOf("<MOEDA>", posf);
			posf = dados.indexOf("</MOEDA>", posi);
			sb.append("("
					+ dados.substring(posi + "<MOEDA>".length(), posf).trim()
					+ ") boleto_Moeda\n");
			posi = dados.indexOf("<VALORDOCUMENTO>", posf);
			posf = dados.indexOf("</VALORDOCUMENTO>", posi);
			trab = dados.substring(posi + "<VALORDOCUMENTO>".length(), posf)
					.trim();
			try {
				trabf = n_br.format(n_usa.parse(trab));
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			sb.append("(" + trabf + ") boleto_ValorDocumento\n");

			// int fim_instrucoes = dados.indexOf("</INSTRUCOES>", posf);

			posi = dados.indexOf("<NUMDIGITACAO>", posf);
			posf = dados.indexOf("</NUMDIGITACAO>", posi);

			String numdigitacao = dados.substring(
					posi + "<NUMDIGITACAO>".length(), posf).trim();
			sb.append("(" + numdigitacao + ") boleto_NumDigitacao\n");

			posi = dados.indexOf("<NUMBARRAS>", posf);
			posf = dados.indexOf("</NUMBARRAS>", posi);
			sb.append("("
					+ numdigitacao.replace(".", "").replace("-", "").replace(
							" ", "").trim() + ") boleto_NumBarras\n");
			posi = dados.indexOf("<LOCALPAGAMENTO>", posf);
			posf = dados.indexOf("</LOCALPAGAMENTO>", posi);
			sb.append("("
					+ dados.substring(posi + "<LOCALPAGAMENTO>".length(), posf)
							.trim() + ") boleto_LocalPagamento\n");
			posi = dados.indexOf("<SACADOLINHA1>", posf);
			posf = dados.indexOf("</SACADOLINHA1>", posi);
			sb.append("("
					+ dados.substring(posi + "<SACADOLINHA1>".length(), posf)
							.trim() + ") boleto_SacadoLinha1\n");
			posi = dados.indexOf("<SACADOLINHA2>", posf);
			posf = dados.indexOf("</SACADOLINHA2>", posi);
			sb.append("("
					+ dados.substring(posi + "<SACADOLINHA2>".length(), posf)
							.trim() + ") boleto_SacadoLinha2\n");
			posi = dados.indexOf("<SACADOLINHA3>", posf);
			posf = dados.indexOf("</SACADOLINHA3>", posi);
			sb.append("("
					+ dados.substring(posi + "<SACADOLINHA3>".length(), posf)
							.trim() + ") boleto_SacadoLinha3\n");
			posi = dados.indexOf("<SACADOLINHA4>", posf);
			posf = dados.indexOf("</SACADOLINHA4>", posi);
			sb.append("("
					+ dados.substring(posi + "<SACADOLINHA4>".length(), posf)
							.trim() + ") boleto_SacadoLinha4\n");

			posi = dados.indexOf("<MENSAGENS>", posf);
			posf = dados.indexOf("</MENSAGENS>", posi);
			sb.append("("
					+ dados.substring(posi + "<MENSAGENS>".length(), posf)
							.trim() + ") boleto_Sacador\n");

			double linha = 513.336;

			int posendboleto = dados.indexOf("</INSTRUCOES></BOLETOS>", posf);

			while ((posi = dados.indexOf("<INSTRUCOES>", posf)) != -1) {
				if (posi > posendboleto) {
					posf = posendboleto;
					break;
				}

				/*
				 * if (posi > fim_instrucoes) { posf = fim_instrucoes; break; }
				 */
				posf = dados.indexOf("</INSTRUCOES>", posi);
				linha -= 7;
				sb.append(linha
						+ " ("
						+ dados.substring(posi + "<INSTRUCOES>".length(), posf)
								.trim() + ") boleto_Instrucao\n");

			}

			sb.append("showpage\n");

		}

		return sb.toString();
	}

	public static void main(String[] args) {
		try {
			String xml = ReadFile.readFile("C:\\boleto2.xml");
			System.out.println(obtemDadosBoletos(xml));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
