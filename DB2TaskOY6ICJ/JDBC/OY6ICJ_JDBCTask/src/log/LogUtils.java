package log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static void  initLogLevel(String logLevel) {
		if(logLevel == null) {
			logger.setLevel(Level.WARNING);
		}
		else if (logLevel.equalsIgnoreCase("debug")){
			logger.setLevel(Level.INFO);
		}
	}
}
