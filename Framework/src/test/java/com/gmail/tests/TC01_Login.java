package com.gmail.tests;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.app.tests.WebUITest;
import com.gmail.pageobjects.LoginPage;
import com.selenium.global.GlobalVars;

//@Listeners(com.selenium.reporting.MyListener.class)
public class TC01_Login extends WebUITest{
	
	public WebDriver driver;
	
	@Test
	public void test_TC01_Login()
	{
		try
		{
			GlobalVars.strModName="Login";
			String className = this.getClass().getName();
			driver=intialize(className);
			LoginPage login=new LoginPage(driver);
			
			
			
			String tUserName="eswar.genji";
			String tPassword="8886872828";
			
			login.fLogin(tUserName,tPassword);
			login.fLogout();
			
			
		}
		catch(Exception e){
			
			Assert.fail("Error occured while executing the Login Testcase..."+e.getMessage());
		}
	}
	
	 @AfterTest
	 public void fCloseBrowser()
	 {
		 if(driver !=null)
			 driver.close();
	 }

}
