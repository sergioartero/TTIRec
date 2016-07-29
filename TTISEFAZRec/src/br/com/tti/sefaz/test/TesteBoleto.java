package br.com.tti.sefaz.test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import br.com.tti.sefaz.util.ReadFile;

public class TesteBoleto {

	public ArrayList<String> obtemDadosBoletos(String dados) {
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
		StringBuffer geral = new StringBuffer();
		String tmp = null;
		String bankTransactionCode = null;
		int lim_sup = 0;
		int tam_fonte = 0;
		ArrayList<String> mensagens = null;
		ArrayList<String> instrucoes = null;
		ArrayList<String> boletos = new ArrayList<String>();

		int posi = 0;
		int posf = 0;

		geral.append("orientacao_boleto\n");
		geral.append("BOLETO execform\n");

		posi = dados.indexOf("<orderNumber>");
		posf = dados.indexOf("</orderNumber>", posi);
		tmp = dados.substring(posi + 13, posf).trim();
		geral.append("(" + tmp + ") boleto_NumDocumento\n");

		posi = dados.indexOf("<invoiceDate>", posf);
		posf = dados.indexOf("</invoiceDate>", posi);
		tmp = dados.substring(posi + 13, posf).trim();
		try {
			dataTrab = sdfUSA.parse(tmp);
		} catch (ParseException ex) {
			// Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
			// ex);
		}
		tmp = sdfBR.format(dataTrab);
		geral.append("(" + tmp + ") boleto_DataDocumento\n");

		posi = dados.indexOf("<customerName>", posf);
		posf = dados.indexOf("</customerName>", posi);
		tmp = dados.substring(posi + 14, posf).trim();
		geral.append("(" + tmp + ") boleto_SacadoLinha1\n");

		posi = dados.indexOf("<customerAddress1>", posf);
		posf = dados.indexOf("</customerAddress1>", posi);
		tmp = dados.substring(posi + 18, posf).trim();
		geral.append("(" + tmp + ") boleto_SacadoLinha2\n");

		posi = dados.indexOf("<customerAddress2>", posf);
		posf = dados.indexOf("</customerAddress2>", posi);
		tmp = dados.substring(posi + 18, posf).trim();
		geral.append("(" + tmp + ") boleto_SacadoLinha3\n");

		posi = dados.indexOf("<bankName>", posf);
		posf = dados.indexOf("</bankName>", posi);
		tmp = dados.substring(posi + 10, posf).trim();
		geral.append("(" + tmp + ") boleto_BancoNome\n");

		posi = dados.indexOf("<bankNumber>", posf);
		posf = dados.indexOf("</bankNumber>", posi);
		tmp = dados.substring(posi + 12, posf).trim();
		geral.append("(" + tmp + ") boleto_BancoNumero\n");

		posi = dados.indexOf("<bankTransactionCode>", posf);
		posf = dados.indexOf("</bankTransactionCode>", posi);
		tmp = dados.substring(posi + 21, posf).trim();
		geral.append("(" + tmp + ") boleto_EspecieDocumento\n");

		posi = dados.indexOf("<bankRelationDescription>", posf);
		posf = dados.indexOf("</bankRelationDescription>", posi);
		bankTransactionCode = dados.substring(posi + 25, posf).trim();

		posi = dados.indexOf("<ingramAccountNumber>", posf);
		posf = dados.indexOf("</ingramAccountNumber>", posi);
		tmp = dados.substring(posi + 21, posf).trim();
		geral.append("(" + tmp + ") boleto_Agencia_CodCedente\n");

		posi = dados.indexOf("<bankBranchName>", posf);
		posf = dados.indexOf("</bankBranchName>", posi);
		tmp = dados.substring(posi + 16, posf).trim();
		geral.append("(" + tmp + ") boleto_Cedente\n");

		posi = dados.indexOf("<currencyCode>", posf);
		posf = dados.indexOf("</currencyCode>", posi);
		tmp = dados.substring(posi + 14, posf).trim();
		geral.append("(" + tmp + ") boleto_Moeda\n");

		posi = dados.indexOf("<headingLine>", posf);
		posf = dados.indexOf("</headingLine>", posi);
		tmp = dados.substring(posi + 13, posf).trim();
		mensagens = quebraTexto(tmp, 70);
		lim_sup = 800;
		tam_fonte = 15;
		for (String mensagem : mensagens) {
			geral.append(lim_sup + " (" + mensagem.trim()
					+ ") boleto_mens_Mensagem\n");
			lim_sup -= tam_fonte;
		}

		posi = dados.indexOf("<paymentInstruction>", posf);
		posf = dados.indexOf("</paymentInstruction>", posi);
		tmp = dados.substring(posi + 20, posf).trim();
		geral.append("(" + tmp + ") boleto_LocalPagamento\n");

		posi = dados.indexOf("<customerInstruction>", posf);
		posf = dados.indexOf("</customerInstruction>", posi);
		tmp = dados.substring(posi + 21, posf).trim();
		instrucoes = quebraTexto(tmp, 70);

		int install = 0;
		while ((install = dados.indexOf("<installment>", install)) != -1) {
			install++;
			StringBuffer especifico = new StringBuffer();
			String linha_digitavel = null;
			String linha_barras = null;
			String customerInstruction = null;

			posi = dados.indexOf("<nossoNumero>", install);
			posf = dados.indexOf("</nossoNumero>", posi);
			tmp = dados.substring(posi + 13, posf).trim();
			especifico.append("(" + bankTransactionCode + "/" + tmp
					+ ") boleto_NossoNumero\n");

			posi = dados.indexOf("<digitizableLine>", install);
			posf = dados.indexOf("</digitizableLine>", posi);
			linha_digitavel = dados.substring(posi + 17, posf).trim();
			especifico
					.append("(" + linha_digitavel + ") boleto_NumDigitacao\n");

			linha_barras = montaBarras(linha_digitavel);
			especifico.append("(" + linha_barras + ") boleto_NumBarras\n");

			posi = dados.indexOf("<installmentAmount>", posf);
			posf = dados.indexOf("</installmentAmount>", posi);
			tmp = dados.substring(posi + 19, posf).trim();
			try {
				tmp = n_br.format(n_usa.parse(tmp));
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			especifico.append("(" + tmp + ") boleto_ValorDocumento\n");

			posi = dados.indexOf("<dueDate>", posf);
			posf = dados.indexOf("</dueDate>", posi);
			tmp = dados.substring(posi + 9, posf).trim();
			try {
				dataTrab = sdfUSA.parse(tmp);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			tmp = sdfBR.format(dataTrab);
			especifico.append("(" + tmp + ") boleto_DataVencimento\n");

			posi = dados.indexOf("<customerInstruction>", posf);
			posf = dados.indexOf("</customerInstruction>", posi);
			customerInstruction = dados.substring(posi + 21, posf).trim();

			instrucoes.set(0, customerInstruction);
			lim_sup = 455;
			tam_fonte = 7;
			for (String mensagem : instrucoes) {
				especifico.append(lim_sup + " (" + mensagem.trim()
						+ ") boleto_instr_Mensagem\n");
				lim_sup -= tam_fonte;
			}
			lim_sup = 188;
			tam_fonte = 7;
			for (String mensagem : instrucoes) {
				especifico.append(lim_sup + " (" + mensagem.trim()
						+ ") boleto_instr_Mensagem\n");
				lim_sup -= tam_fonte;
			}
			boletos
					.add(geral.toString() + especifico.toString()
							+ "showpage\n");
		}
		return boletos;
	}

	public String montaBarras(String numdig) {
		String tmp = null;
		int dig[] = { 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2,
				9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5,
				4, 3, 2 };
		tmp = numdig.substring(0, 3) // cod banco
				+ numdig.substring(3, 4) // cod moeda
				+ numdig.substring(40, 54) // fator vencimento + valor titulo
				+ numdig.substring(4, 5) // carteira 1ra parte
				+ numdig.substring(6, 8) // carteira 2da parte
				+ numdig.substring(8, 10) // nosso numero 1ra parte
				+ numdig.substring(12, 17) // nosso numero 2da parte
				+ numdig.substring(18, 20) // nosso numero 3ra parte
				+ numdig.substring(20, 23) // cod agencia 1ra parte
				+ numdig.substring(25, 26) // cod agencia 2da parte
				+ numdig.substring(26, 30) // numero da conta 1ra parte
				+ numdig.substring(31, 36); // numero da conta 2da parte + 000
		int len = tmp.length();

		int soma = 0;
		for (int i = 0; i < len; i++) {
			soma += Integer.parseInt(tmp.substring(i, i + 1)) * dig[i];
		}
		int resto = soma % 11;
		int dac = 11 - resto;
		String barras = tmp.substring(0, 4) + dac + tmp.substring(4);
		return (barras);
	}

	public ArrayList<String> quebraTexto(String texto, int tamanho) {
		ArrayList<String> resposta = new ArrayList<String>();
		int inicio = 0;
		int fim = 0;

		int len = texto.length();

		if (len <= tamanho) {
			resposta.add(texto);
		} else {
			while (inicio < len) {
				fim = inicio + tamanho;
				if (fim > len) {
					fim = len;
				}
				resposta.add(texto.substring(inicio, fim));
				inicio += tamanho;
			}

		}
		return resposta;
	}

	public static void main(String[] args) {
		TesteBoleto test = new TesteBoleto();
		String dados = null;
		try {
			dados = ReadFile.readFile("C:\\boleto_ingram.txt");
			System.out.println(dados);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> restul = test.obtemDadosBoletos(dados);
		for (String string : restul) {
			System.out.println(string);
		}
	}
}
