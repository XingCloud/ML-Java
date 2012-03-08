package com.xingcloud.ml.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MLLogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		return record.getLevel() + ":" + record.getSourceClassName()+ "-->" + record.getSourceMethodName() + "      " + record.getMessage() + "\n";
	}

}
