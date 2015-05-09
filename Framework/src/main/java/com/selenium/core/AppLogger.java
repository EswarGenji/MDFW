package com.selenium.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.selenium.global.ConfigDetails;
import com.selenium.global.GlobalVars;



public class AppLogger {
	
	private static String strlogDir = new SimpleDateFormat("MMddyy").format(new Date());
	private static SimpleDateFormat strLogFileName = new SimpleDateFormat("MMddyy_HHmmss");
	//public static Logger log = Logger.getLogger(MyLogger.class.getName());
	
	public static Logger log;
	
	public static void fCreateLogger(String strModCode) {
		try {
			
			 log = Logger.getLogger(AppLogger.class.getName());
			
			String path=".//log";
			CoreLib.createDir(path);
			
			
			String strEnvDir = path +"//"+ ConfigDetails.appName;
			CoreLib.createDir(strEnvDir);
			
			/*String strModuleDir = strEnvDir+"//" + strModCode;
			createDir(strModuleDir);*/
			String logDirPath = strEnvDir + "//" + strlogDir;
			CoreLib.createDir(logDirPath);
			String logFilePath = logDirPath + "//log_" + strModCode + "_"
					+ strLogFileName.format(new Date()) + ".txt";
			FileAppender appender = new FileAppender();
			appender.setName("MyFileAppender");
			appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m %n"));
			appender.setFile(logFilePath);
			appender.setAppend(true);
			appender.setThreshold(Level.INFO);
			appender.activateOptions();

			log.addAppender(appender);

			File logFile = new File(logFilePath);
			GlobalVars.strLogFilePath=logFile.getCanonicalPath();

			System.out.println(" Logger Path ::" + logFilePath);
		} catch (Exception e) {
			System.out
					.println("Error Occured while creating the Logger Object..::"
							+ e.getMessage());
		}
	}


}
