package br.com.tti.sefaz.systemconfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import br.com.tti.sefaz.persistence.SefazState;
import br.com.tti.sefaz.persistence.TTIState;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.systemconfig.SystemProperties.XML_STATE;

public class States {

	private static States INSTANCE = null;

	private SefazState recivedSet;

	private SefazState authorizedXml;

	private Vector<SefazState> processedStates;
	private Vector<SefazState> nonProcessedStates;
	private Vector<SefazState> sendedStates;
	private Vector<SefazState> noSendedStates;
	private Vector<SefazState> schemaErrorStates;
	private Vector<SefazState> autorizedStates;
	private Vector<SefazState> rejectedStates;
	private Vector<SefazState> cancelStates;
	private Vector<SefazState> inutdStates;
	private Vector<SefazState> denegatedStates;
	private Vector<SefazState> statusServicesStates;
	private Vector<SefazState> consultaCadStates;
	private Vector<SefazState> alreadyCancelStates;
	private Vector<SefazState> alreadyInutStates;

	private Vector<TTIState> toContingenceStates;
	private Vector<TTIState> toSendStates;
	private Vector<TTIState> toCheckContingenceStates;
	private Vector<TTIState> toCancelStates;
	private Vector<TTIState> toInutStates;
	private Vector<TTIState> dontReSend;

	private DAO<SefazState> daoState;

	private String file1;

	private String file2;

	public States(String file, String file2) {
		// this.daoState = DaoFactory.createDAO(SefazState.class);

		this.file1 = file;
		this.file2 = file2;
		this.processedStates = new Vector<SefazState>();
		this.nonProcessedStates = new Vector<SefazState>();
		this.sendedStates = new Vector<SefazState>();
		this.noSendedStates = new Vector<SefazState>();
		this.schemaErrorStates = new Vector<SefazState>();
		this.autorizedStates = new Vector<SefazState>();
		this.rejectedStates = new Vector<SefazState>();
		this.cancelStates = new Vector<SefazState>();
		this.inutdStates = new Vector<SefazState>();
		this.denegatedStates = new Vector<SefazState>();
		this.statusServicesStates = new Vector<SefazState>();
		this.consultaCadStates = new Vector<SefazState>();
		this.alreadyCancelStates = new Vector<SefazState>();
		this.alreadyInutStates = new Vector<SefazState>();

		this.toContingenceStates = new Vector<TTIState>();
		this.toSendStates = new Vector<TTIState>();
		this.toCheckContingenceStates = new Vector<TTIState>();
		this.toCancelStates = new Vector<TTIState>();
		this.toInutStates = new Vector<TTIState>();
		this.dontReSend = new Vector<TTIState>();
	}

	public static States getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new States("codigos.conf", "codigos2.conf");
			INSTANCE.lerArquivo1();
			INSTANCE.lerArquivo2();
		}
		return INSTANCE;
	}

	public void lerArquivo1() {
		BufferedReader br = null;
		try {

			InputStream r = this.getClass().getClassLoader()
					.getResourceAsStream(this.file1);
			// InputStream r = new FileInputStream(this.file1);
			InputStreamReader isr = new InputStreamReader(r);
			br = new BufferedReader(isr);

			String line = br.readLine();

			Vector<SefazState> currentVector = null;
			while (line != null) {
				String[] parts = line.split("=");
				if (parts.length == 2) {
					SefazState state = new SefazState(Integer.parseInt(parts[0]
							.trim()), parts[1]);

					currentVector.add(state);
				} else {
					currentVector = this.getVector(line.trim());
				}
				line = br.readLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void lerArquivo2() {
		BufferedReader br = null;
		try {
			InputStream r = this.getClass().getClassLoader()
					.getResourceAsStream(this.file2);
			// InputStream r = new FileInputStream(this.file2);

			InputStreamReader isr = new InputStreamReader(r);
			br = new BufferedReader(isr);

			String line = br.readLine();

			Vector<TTIState> currentVector = null;
			while (line != null) {
				String[] parts = line.split("\\,");
				if (parts.length != 1) {
					for (String s : parts) {
						try {
							TTIState state = new TTIState(XML_STATE.valueOf(s
									.trim()), 0);
							currentVector.add(state);
						} catch (Exception e) {
							// e.printStackTrace();
						}
					}

				} else {
					currentVector = this.getVector2(line.trim());
				}
				line = br.readLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private Vector<TTIState> getVector2(String name) {
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().toLowerCase().contains(name.toLowerCase()))
				try {
					return (Vector<TTIState>) method.invoke(this,
							new Object[] {});
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	private Vector<SefazState> getVector(String name) {
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().toLowerCase().contains(name.toLowerCase()))
				try {
					return (Vector<SefazState>) method.invoke(this,
							new Object[] {});
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	public SefazState getRecivedSet() {
		return recivedSet;
	}

	public SefazState getAuthorizedXml() {
		return authorizedXml;
	}

	public Vector<SefazState> getProcessedStates() {
		return processedStates;
	}

	public Vector<SefazState> getNonProcessedStates() {
		return nonProcessedStates;
	}

	public Vector<SefazState> getSendedStates() {
		return sendedStates;
	}

	public Vector<SefazState> getNoSendedStates() {
		return noSendedStates;
	}

	public Vector<SefazState> getSchemaErrorStates() {
		return schemaErrorStates;
	}

	public Vector<SefazState> getAutorizedStates() {
		return autorizedStates;
	}

	public Vector<SefazState> getRejectedStates() {
		return rejectedStates;
	}

	public Vector<SefazState> getCancelStates() {
		return cancelStates;
	}

	public Vector<SefazState> getInutdStates() {
		return inutdStates;
	}

	public Vector<SefazState> getDenegatedStates() {
		return denegatedStates;
	}

	public Vector<SefazState> getStatusServicesStates() {
		return statusServicesStates;
	}

	public Vector<SefazState> getConsultaCadStates() {
		return consultaCadStates;
	}

	public Vector<TTIState> getToCancelStates() {
		return toCancelStates;
	}

	public Vector<TTIState> getToInutStates() {
		return toInutStates;
	}

	public DAO<SefazState> getDaoState() {
		return daoState;
	}

	public Vector<SefazState> getAlreadyCancelStates() {
		return alreadyCancelStates;
	}

	public Vector<SefazState> getAlreadyInutStates() {
		return alreadyInutStates;
	}

	public Vector<TTIState> getToContingenceStates() {
		return toContingenceStates;
	}

	public Vector<TTIState> getToCheckContingenceStates() {
		return toCheckContingenceStates;
	}

	public Vector<TTIState> getToSendStates() {
		return toSendStates;
	}

	public Vector<TTIState> getDontReSend() {
		return dontReSend;
	}

	public static void main(String[] args) {
		States s = new States(
				"C:/Backu/eclipse_workspace/TTISEFAZEvolution/src/codigos.conf",
				"C:/Backup/eclipse_workspace/TTISEFAZEvolution/src/codigos2.conf");
		// s.lerArquivo1();
		s.lerArquivo2();
		Vector<SefazState> v = s.getVector("processedStates");
		System.out.println(s.getDontReSend().toString());
	}

}
