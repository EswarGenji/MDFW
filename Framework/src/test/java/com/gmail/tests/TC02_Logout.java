package com.gmail.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.app.tests.WebUITest;
import com.gmail.pageobjects.LoginPage;
import com.selenium.core.CoreLib;
import com.selenium.global.GlobalVars;


//@Listeners(com.selenium.reporting.MyListener.class)
public class TC02_Logout extends WebUITest{

	public WebDriver driver;
	
	@Test
	public void test_TC01_Logout()
	{
		try
		{
			GlobalVars.strModName="Logout";
			String className = this.getClass().getName();
			driver=intialize(className);
			LoginPage login=new LoginPage(driver);
			
			
			
			String tUserName="eswar.mca06";
			String tPassword="8886872828";
			
			login.fLogin(tUserName,tPassword);
			login.fLogout();
			
			
		}
		catch(Exception e){
			
			Assert.fail("Error occured while executing the Logout Testcase..."+e.getMessage());
		}
	}
	
	 @AfterTest
	 public void fCloseBrowser()
	 {
		 if(driver !=null)
			 driver.close();
	 }
}
