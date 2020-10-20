package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.Assert;

public class PropertyHandler {
	public Logger logger = LoggerFile.logConfig(this.getClass().getName());
    
	Properties properties = new Properties();
	
	public String filePath = System.getProperty("user.dir") + File.separator+"\\src\\test\\resources\\properties\\filename";
   
	public PropertyHandler() {
		InputStream inPropFile = null;
		try {
			logger.info("filePath "+filePath);
			logger.info(System.getProperty("user.dir") + File.separator);
			filePath = filePath.replace("filename", "QA.properties");
			inPropFile = new FileInputStream(filePath);
			properties.load(inPropFile);
			
			inPropFile.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
	
	public String readProperty(String property) {
		String value =properties.getProperty(property);
		if(value==null) {
			Assert.fail(property +" is not present in properties file");
		}
		return value;
		
	}

}
