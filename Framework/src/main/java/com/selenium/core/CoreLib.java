package com.selenium.core;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.selenium.global.ConfigDetails;
import com.selenium.global.GlobalVars;



public class CoreLib {
	public static Logger LOGGER = Logger.getLogger(CoreLib.class.getName());
	private static SimpleDateFormat strLogFileName = new SimpleDateFormat(
			"MMddyy_HHmmss");
	private static String strlogDir = new SimpleDateFormat("MMddyy")
			.format(new Date());

	public static String fGetNodeText(String vFileName, String vblockName,
			String eTagName) {
		String eleIdentifier = "";
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(vFileName));
			doc.getDocumentElement().normalize();
			NodeList listOfBlocks = doc.getElementsByTagName(vblockName);
			Node blockOne = listOfBlocks.item(0);
			if (blockOne.getNodeType() == Node.ELEMENT_NODE) {
				Element firstBlkEle = (Element) blockOne;
				NodeList firstBlkTagList = firstBlkEle
						.getElementsByTagName(eTagName);
				Element firstBlkFirstTagEle = (Element) firstBlkTagList.item(0);
				NodeList firstBlkFirstTagTexList = firstBlkFirstTagEle
						.getChildNodes();
				if ((Node) firstBlkFirstTagTexList.item(0) != null) {
					eleIdentifier = ((Node) firstBlkFirstTagTexList.item(0))
							.getNodeValue().trim().toString();
				}
				if (eleIdentifier == null) {
					eleIdentifier = "";
				}

				// CoreUtils.LOGGER.info("----------------------------------<"+firstBlkTagList.item(0).getNodeName()+">"+eleIdentifier+"<"+firstBlkTagList.item(0).getNodeName()+">");
			}

		} catch (Exception e) {
			
			System.out.println("Error occured while reading XML file "+e.getMessage());
			// CoreUtils.LOGGER.error(npe.toString());
		} 
		return eleIdentifier;

	}

	public static void fCreateLogger(String strModCode) {
		try {
			
			String path=".//log";
			createDir(path);
			
			
			String strEnvDir = path +"//"+ ConfigDetails.appName;
			createDir(strEnvDir);
			
			/*String strModuleDir = strEnvDir+"//" + strModCode;
			createDir(strModuleDir);*/
			String logDirPath = strEnvDir + "//" + strlogDir;
			createDir(logDirPath);
			String logFilePath = logDirPath + "//log_" + strModCode + "_"
					+ strLogFileName.format(new Date()) + ".txt";
			FileAppender appender = new FileAppender();
			appender.setName("MyFileAppender");
			appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m %n"));
			appender.setFile(logFilePath);
			appender.setAppend(true);
			appender.setThreshold(Level.INFO);
			appender.activateOptions();

			LOGGER.addAppender(appender);

			File logFile = new File(logFilePath);
			GlobalVars.strLogFilePath=logFile.getCanonicalPath();

			System.out.println(" Logger Path ::" + logFilePath);
		} catch (Exception e) {
			System.out
					.println("Error Occured while creating the Logger Object..::"
							+ e.getMessage());
		}
	}

	public static void createDir(String dirName) {
		File f = new File(dirName);
		try {
			if (!f.exists()) {
				f.mkdir();
				CoreLib.LOGGER.info("Directory Created :: " + dirName);
			}
		} catch (Throwable e) {
			CoreLib.LOGGER.error("Unable to create directory  '" + dirName
					+ "'");
		}
	}
	
	/***********************************************************************************************************************************
	* @Date Created	:	26/12/2014    	
	* @author :	EGenji
	* @param Throwable     :   Throwable Value
	* @purpose	       :   converting the Throwable value to String value
	* @ReturnType          :   returns the converted string value
	* @Date Updated	:
	* @Updated by	:
	************************************************************************************************************************************/
	public static String fToString(Throwable t)
	{
	String value="";
	try
	{
	StringWriter sw = new StringWriter();
	   PrintWriter pw = new PrintWriter(sw);
	 t.printStackTrace(pw);
	   value=sw.toString();
	}
	catch(Exception e)
	{
	System.out.println("Error Occured while converting the Throwable to string .."+e.getMessage());
	}
	return value;
	}

}
