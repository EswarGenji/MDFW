package com.gmail.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.app.tests.WebUITest;
import com.gmail.pageobjects.LoginPage;
import com.selenium.core.CoreLib;


//@Listeners(com.selenium.reporting.MyListener.class)
public class TC02_Logout extends WebUITest{

	protected WebDriver driver;
	
	@Test
	public void test_TC01_Logout()
	{
		try
		{
			String className = this.getClass().getName();
			driver=intialize(className);
			//LoginPage login=new LoginPage(driver);
			//login.fLogin();
			
			
			CoreLib.LOGGER.info("Gmail Logout test cases");
			
		}
		catch(Exception e)
		{
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
