package com.xingcloud.ml.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xingcloud.ml.ML;

public class MLLogger {

	protected static MLLogger instance = null;

	protected Logger log = null;

	public static MLLogger getLogger() {
		if (instance == null) {
			instance = new MLLogger();
		}
		return instance;
	}

	protected MLLogger() {
		String filename = "ML.log";
		if (ML.config.containsKey("cache_dir")) {
			filename = ML.config.get("cache_dir") + File.separator + "ML.log";
		}
		log = Logger.getLogger("ML");
		log.setLevel(Level.INFO);
		FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler(filename, true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileHandler.setLevel(Level.INFO);
		fileHandler.setFormatter(new MLLogFormatter());
		log.addHandler(fileHandler);
	}

	public void error(String message) {
		log.severe(message);
	}

	public void info(String message) {
		log.info(message);
	}

	public void warn(String message) {
		log.warning(message);
	}
}
