package br.com.tti.sefaz.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import br.com.tti.sefaz.util.MainParameters;

public class MyLogger {

	private static String defaultFile = "tti.log";

	private static Logger logger = null;

	public static Logger getLog() {
		if (logger == null) {
			FileHandler fh = null;
			try {
				if (MainParameters.getLog() != null) {
					fh = new FileHandler(MainParameters.getLog(), true);
					fh.setFormatter(new SimpleFormatter());
					fh.setLevel(Level.ALL);
					logger.addHandler(fh);
				} else
					fh = new FileHandler(defaultFile, true);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger = Logger.getLogger("br.com.tti.cterec");

			ConsoleHandler ch = new ConsoleHandler();
			if (MainParameters.isConsole())
				ch.setLevel(Level.OFF);
			else
				ch.setLevel(Level.ALL);
			// logger.addHandler(ch);

			if (MainParameters.isDebug())
				logger.setLevel(Level.FINEST);
			else {
				logger.setLevel(Level.INFO);
			}
		}
		// logger.setLevel(Level.INFO);

		return logger;
	}

	public static void main(String[] args) {
		MyLogger.getLog().info("info");
		MyLogger.getLog().warning("warning");
		MyLogger.getLog().config("config");
		MyLogger.getLog().finest("finest");
		MyLogger.getLog().log(Level.SEVERE, "nfo2");

	}
}
