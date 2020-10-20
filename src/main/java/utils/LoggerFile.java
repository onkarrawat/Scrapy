package utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerFile {

	public static Logger logConfig(String classname) {
		Logger logger = Logger.getLogger(classname);
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "LogFile//log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		return logger;
	}
}
