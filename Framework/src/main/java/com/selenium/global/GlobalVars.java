package com.selenium.global;

import java.util.HashMap;
import java.util.List;



		public class GlobalVars {
				  
			  //public static WebDriver driver;	  
			  
			  
			  public static final String locOfData="./TestData/CAMMIS/";
			  public static final String locOfPages="./ObjectRepository/Pages/";
			  public static final String locOfDrivers="./Drivers/";
			  public static String browser="";
			  public static HashMap<String,List<String>> testCasesResutls=new HashMap<String,List<String>>();

			  
			  public static final int waitTime=10;
              public static String defaultLogo="http://www.seleniumhq.org/images/selenium-ide-logo.png";
              public static String testName="";
            
              
              //For Reporting Purpose
              public static String strApplicationName="CAMMIS";
              public static String strTestType="Smoke";
              public static String strBUildScreenshotPath="";
              public static String strModName="";
              public static String testHTMLResultPath="";
              public static String TestResultsPath="";
              public static String strScreenshotPath="";
              public static String strLogFilePath="";
              
              public static Long totalTimeTaken=0l;


			  
/*******************************************************************************************************************************************************************
																End of GlobalVars.
********************************************************************************************************************************************************************/
		}


