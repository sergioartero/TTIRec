package br.com.tti.sefaz.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;

import br.com.tti.sefaz.log.MyLogger;
import br.com.tti.sefaz.util.MainParameters;

public class ProcessDirectory implements Runnable {

	private Vector<String> directories;
	private long timeout;
	private SimpleDateFormat format;

	public ProcessDirectory(long timeout) {
		this.timeout = timeout;
		this.directories = new Vector<String>();
		this.format = new SimpleDateFormat("ddMMyyyyhhmmss");
	}

	public void addDirectory(String dir) {
		this.directories.add(dir);
	}

	public void processList() {
		for (String path : this.directories) {
			System.out.println("Processing directory..." + path);
			File dir = new File(path);
			this.processDirectory(dir);
		}
	}

	public void processDirectory(File dir) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.getAbsolutePath().endsWith(".xml")) {
				try {
					SendXml xml = new SendXml(file.getAbsolutePath());
					xml.enviar();
				} catch (Exception e) {
					MyLogger.getLog().log(
							Level.INFO,
							"Problems in send XML file: "
									+ file.getAbsolutePath(), e);
				}
				this.changeNameFile(file);
			}
		}
	}

	private void changeNameFile(File f) {
		try {
			String newName = f.getAbsolutePath().replace(".xml", "");
			newName = newName
					+ this.format.format(Calendar.getInstance().getTime())
					+ ".sended";
			File dest = new File(newName);
			f.renameTo(dest);
		} catch (Exception e) {
			try {
				f.delete();
			} catch (Exception e1) {
				MyLogger.getLog().log(Level.INFO,
						"Problems in delete XML file: " + f.getAbsolutePath(),
						e1);
			}
		}
	}

	public void check() {

		try {
			this.processList();
		} catch (Exception e) {
			MyLogger.getLog().info("Process directory problem... exit forced");
			System.exit(1);
		}
		try {
			Thread.sleep(this.timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	@Override
	public void run() {
		this.check();
	}

	public static void main(String[] args) {
		MainParameters.processArguments(args);

		long time = MainParameters.getQuantidade();

		ProcessDirectory proc = new ProcessDirectory(time);

		Vector<String> list = MainParameters.getDirs();
		for (String dir : list) {
			proc.addDirectory(dir);
		}

		System.out.println("Initialized!...");

		Thread t = new Thread(proc);
		t.start();
	}
}
