package br.com.tti.sefaz.event;

import br.com.taragona.nfe.email.MailSender;
import br.com.taragona.nfe.persistence.LogEnvioEmail;
import br.com.taragona.nfe.persistence.LogEnvioEmail.TIPO_EMAIL;

public class EventoSendThread implements Runnable {

	private MailSender sender;
	private String[] filesnames;
	private String id;
	private String cnpj;
	private String cnpjtransp;

	public EventoSendThread(MailSender sender, String[] filesnames, String id,
			String cnpj, String cnpjtransp) {
		super();
		this.sender = sender;
		this.filesnames = filesnames;
		this.id = id;
		this.cnpj = cnpj;
		this.cnpjtransp = cnpjtransp;
	}

	@Override
	public void run() {
		LogEnvioEmail logemail = new LogEnvioEmail();
		logemail.setTipo(TIPO_EMAIL.DESTINATARIO);
		/*try {
			this.sender.sendCNPJDBEvent(id, cnpj, filesnames, logemail, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.sender.sendCNPJDBEvent(id, cnpjtransp, filesnames, logemail,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
