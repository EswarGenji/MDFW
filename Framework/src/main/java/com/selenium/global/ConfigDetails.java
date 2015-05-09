package com.selenium.global;

import com.selenium.core.CoreLib;

public class ConfigDetails {
	
	public static String browser;
	public static String appName;
	public static String release;
	public static String url;
	
	
	public static String isEmail;
	public static String isScreenshot;
	
	public static void fReadConfigDetails()
	{
		try
		{
			String configXlsPath="./config.xml";
			browser = CoreLib.fGetNodeText(configXlsPath, "config","browser");
			appName = CoreLib.fGetNodeText(configXlsPath, "config", "appName");
			release = CoreLib.fGetNodeText(configXlsPath, "config", "release");
			url     = CoreLib.fGetNodeText(configXlsPath, appName, "url");
			
			
			isEmail=CoreLib.fGetNodeText(configXlsPath, "Properties","EMail");
			isScreenshot=CoreLib.fGetNodeText(configXlsPath, "Properties","Screenshots");
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	public static void main(String[] args) {
		
		ConfigDetails.fReadConfigDetails();
		
		System.out.println(" Browser   ::"+ConfigDetails.browser);
		System.out.println(" AppName   ::"+ConfigDetails.appName);
		System.out.println(" Release   ::"+ConfigDetails.release);
		
		
	}

}
