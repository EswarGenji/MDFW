package com.gmail.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.selenium.core.CoreLib;
import com.selenium.global.GlobalVars;
import com.selenium.webdriver.DriverLib;

public class LoginPage {
	
	public WebDriver driver;
	 public LoginPage(WebDriver driver)
	 {
		 this.driver=driver;
	 }
	
	
	public void fLogin(String tUserName,String tPassword)
	{
		try
		{
			
			String eUserName="Email";
			String ePassword="Passwd";
			String eSignIn="signIn";
			String eAccountImage="//a[contains(@title,'Account')]";
			
			DriverLib.fInputText(driver,eUserName, tUserName,"UserName",true);
			DriverLib.fInputText(driver,ePassword, tPassword,"Password",true);
			DriverLib.fClickAndWait(driver,eSignIn,"SignIn",true);
			DriverLib.fWaitForElementPresent(driver,GlobalVars.waitTime,eAccountImage,"AccountImage",true);
			
			CoreLib.LOGGER.info(" Login successfully with UserName '"+tUserName+"' and Password '"+tPassword+"'");
			
		}
		catch(Exception e)
		{
			System.out.println("Error occured while executing the Login common fucntion..."+e.getMessage());
			Assert.fail("Error occured while login into the application..."+e.getMessage());
			
		}
	}
	
	public void fLogout()
	{
		try
		{
			String eAccountImage="//a[contains(@title,'Account')]";
			String eSignout="//a[text()='Sign out']";
			String eSignIn="signIn";
			
			DriverLib.fClickElement(driver,eAccountImage,"AccountIcon",true);
			DriverLib.fClickAndWait(driver, eSignout, "Siginout",true);
			DriverLib.fVerifyElementPresent(driver,eSignIn,"SignIn",true);
			
			CoreLib.LOGGER.info(" Application Logout Successfully ");
			
		}
		catch(Exception e)
		{
			System.out.println("Error occured while executing the Logout common fucntion..."+e.getMessage());
			Assert.fail("Error occured while executing the Logout common fucntion..."+e.getMessage());
			
		}
	}

}
