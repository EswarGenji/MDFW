package com.app.tests;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;

import com.selenium.core.CoreLib;
import com.selenium.global.GlobalVars;
import com.selenium.webdriver.DriverLib;



public class WebUITest {
	
	protected WebDriver driver = null;
	
	
	 public WebDriver intialize(String testName)
	 {
		 try
		 {
			 testName=testName.substring(testName.lastIndexOf('.') + 1);
			 
			 CoreLib.fCreateLogger(testName);
			 driver=DriverLib.fLanchBrowser();
			 DriverLib.fOpenApplication(driver);
			 
			 
			 GlobalVars.testName=testName;
		 }
		 catch(Exception e)
		 {
			 CoreLib.LOGGER.error("Error occured while executing the intizlize method....."+e.getMessage());
		 }
		 
		 return driver;
	 }
	 
	 
	 @AfterMethod
	 public void fCloseBrowser()
	 {
		 if(driver !=null)
			 driver.close();
	 }
	
	/*@BeforeTest
	public void fLanchApp()
	{
		DriverLib.fLanchBrowser();
		DriverLib.fOpenApplication();
	}
	
	@AfterTest
	public void fCloseBrowser()
	{
		DriverLib.fClose();
	}*/

}
