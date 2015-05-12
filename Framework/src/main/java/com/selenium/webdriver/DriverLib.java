package com.selenium.webdriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;




import com.selenium.core.CoreLib;
import com.selenium.global.ConfigDetails;
import com.selenium.global.GlobalVars;

public class DriverLib {
	
	
	public static WebDriver driver;
	static String configXlsPath = "./config.xml";
   	private static SimpleDateFormat scrShot = new SimpleDateFormat("MMddyy_HHmmss");
	private static String scrShotDir = new SimpleDateFormat("MMddyy_HHmmss").format(new Date());

	public boolean acceptNextAlert = true;

	/*******************************************************************************************************************************************************************
	 * @Date Created : 17/09/2014
	 * @author : Eswar Genji
	 * @param browserType: Type of the browser (IE,FF,GC..)
	 * @throws : Exception error
	 * @purpose : Launching the Browser
	 * ********************************************************************************************************************************************************************/
	public static WebDriver fLanchBrowser() {
		 //WebDriver driver=null;	
		 String browserType=ConfigDetails.browser;
		try {
				  
				switch (browserType) 
				{
					case "FF":
						FirefoxProfile profile = new FirefoxProfile();
						profile.setPreference("intl.accept_languages", "en-gb");
						driver = new FirefoxDriver(profile);
						break;
	
					case "GC":
						File chromeDriver = new File(GlobalVars.locOfDrivers+ "chromedriver.exe");
						System.setProperty("webdriver.chrome.driver",chromeDriver.getAbsolutePath());
	
						DesiredCapabilities capabilities = DesiredCapabilities.chrome();
						ChromeOptions options = new ChromeOptions();
						options.addArguments(new String[] { "test-type" });
						options.addArguments(new String[] { "disable-extensions" });
						capabilities.setCapability("chrome.binary",chromeDriver.getAbsolutePath());
						capabilities.setCapability(ChromeOptions.CAPABILITY,options);
						options.addArguments("--lang=en-gb");
						driver = new ChromeDriver(capabilities);
						break;
	
					case "IE":
						DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
						File iEDriver = new File(GlobalVars.locOfDrivers+ "IEDriverServer.exe");
						System.setProperty("webdriver.ie.driver",iEDriver.getAbsolutePath());
						caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
						driver = new InternetExplorerDriver(caps);
						break;
	
					case "SF":
						driver = new SafariDriver();
						break;
				}

				driver.manage().window().maximize();
				return driver;

			

		} catch (Exception e) {
			CoreLib.LOGGER.error("Error occured while lanching the Browser ::'"
					+ browserType + "'");
			Assert.fail("Error occured while lanching the Browser ::"
					+ browserType + " " + e.getMessage());
			return null;
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 25/04/2014
	 * @author       : Eswar Genji
	 * @Purpose      : Application URL will load in a browser and it will waitfor PageLoad
	 * @Updated by   :
	 * @Date Updated :
	 ********************************************************************************************************************************************************************/
	public static void fOpenApplication(WebDriver driver) {

		String url = ConfigDetails.url;
		try 
		{
			driver.get(url);
			CoreLib.LOGGER.info("Application Loaded with URL '" + url + "'");
		} 
		catch (Exception e)
		{
			CoreLib.LOGGER.error("Error occured while lanching the Application with url '"+url+"'");
			Assert.fail("Error occured while lanching the Application with url  '"+url+"'");
		}

	}


	/*******************************************************************************************************************************************************************
	 * @Date Created : 25/04/2014
	 * @author       : Eswar Genji
	 * @Purpose      : Application URL will load in a browser and it will waitfor PageLoad
	 * @Updated by   :
	 * @Date Updated :
	 ********************************************************************************************************************************************************************/
	public static void bOpenApplication(String url) throws Exception 
	{

		try 
		{
			driver.get(url);
			CoreLib.LOGGER.info("Application Lanched with URL '" + url + "'");

		} catch (Exception e) 
		{
			CoreLib.LOGGER.error("Error occured while lanching the Application with url and waiting for UserName ");
			Assert.fail("Error occured while lanching the Application with url and waiting for UserName ");
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created   : 18/09/2014
	 * @author         : Eswar Genji
	 * @param locator  : the locator of the object
	 * @Purpose        : Identify the the object in the application
	 * @return         : Returns the By Object of the locator
	 * @Updated by     :
	 * @Date Updated   :
	 ********************************************************************************************************************************************************************/
	public static By locatorToByObj(String locator) {

		
		if (locator.startsWith("//")) {
			try {
				driver.findElement(By.xpath(locator));
				return By.xpath(locator);
			} catch (Throwable e) {
			}
		}

		try {
			driver.findElement(By.id(locator));
			return By.id(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.linkText(locator));
			return By.linkText(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.cssSelector(locator));
			return By.cssSelector(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.name(locator));
			return By.name(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.className(locator));
			return By.className(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.xpath("//a[contains(text(),'"
					+ locator + "')]")); // this is for objects without
											// linkText. for eg: Home button
			return By.xpath("//a[contains(text(),'" + locator + "')]");
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.linkText(locator));
			return By.linkText(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.partialLinkText(locator));
			return By.partialLinkText(locator);
		} catch (Throwable e) {
		}
		return null;
	}

public static By locatorToByObj(WebDriver driver,String locator) {

		
		if (locator.startsWith("//")) {
			try {
				driver.findElement(By.xpath(locator));
				return By.xpath(locator);
			} catch (Throwable e) {
			}
		}

		try {
			driver.findElement(By.id(locator));
			return By.id(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.linkText(locator));
			return By.linkText(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.cssSelector(locator));
			return By.cssSelector(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.name(locator));
			return By.name(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.className(locator));
			return By.className(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.xpath("//a[contains(text(),'"
					+ locator + "')]")); // this is for objects without
											// linkText. for eg: Home button
			return By.xpath("//a[contains(text(),'" + locator + "')]");
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.linkText(locator));
			return By.linkText(locator);
		} catch (Throwable e) {
		}

		try {
			driver.findElement(By.partialLinkText(locator));
			return By.partialLinkText(locator);
		} catch (Throwable e) {
		}
		return null;
	}
	

	/*******************************************************************************************************************************************************************
	 * @Date Created      : 21/10/2014
	 * @author            : EGenji
	 * @param by          : locator with 'String' class return type
	 * @return            : Returns 'true' if element is not present in the web page else 'false'
	 * @throws Exception  : NoSuchElementException
	 * @purpose           : Checks whether element identified by the locator is not present
	 * ********************************************************************************************************************************************************************/

	public static boolean fIsElementNotPresent(String eLocator) throws Exception {
		try 
		{
			WebElement wblElement=fGetWebElement(eLocator);
			if (wblElement == null)
				return true;
			else
				return false;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created     : 21/10/2014
	 * @author           : EGenji
	 * @param by         : locator with 'String' class return type
	 * @return           : Returns 'true' if element is present in the web page else 'false'
	 * @throws Exception : NoSuchElementException
	 * @purpose          : Checks whether element identified by the locator is present
	 * ********************************************************************************************************************************************************************/

	public static boolean fIsElementPresent(String eLocator) throws Exception {
		try 
		{
			WebElement wblElement = fGetWebElement(eLocator);
			if (wblElement != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 07/04/2014
	 * @author : EGenji
	 * @return : Returns 'true' if alert is present else 'false'
	 * @throws : NoAlertPresentException
	 * @purpose : Checks whether alert is present or not
	 * ********************************************************************************************************************************************************************/

	public static boolean fIsAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 07/04/2014
	 * @author       : EGenji
	 * @return       : Returns text from the alert
	 * @purpose      : Retrieves text from the alert and close it
	 * ********************************************************************************************************************************************************************/

	public String fCloseAlertAndGetItsText()
	{
		try 
		{
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 07/04/2014
	 * @author : EGenji
	 * @param waitTime
	 *            : Timeout in seconds
	 * @param elmtLocator
	 *            : Element locator
	 * @throws Exception
	 * @throws : TimeoutException
	 * @purpose : Waits for the element identified by the locator until timeout
	 *          expires
	 * ********************************************************************************************************************************************************************/

	public static void fWaitForElementPresent(int waitTime, String elmtLocator)
			throws Exception {
		try {
			int i = 0;
			if (waitTime > 1000)
				waitTime = waitTime / 1000;

			for (i = 0; i < waitTime; i++) {
				By by = locatorToByObj(elmtLocator);

				if (by == null)
					Thread.sleep(1000);
				else
					break;

				System.out.println("Waiting for Element  '" + elmtLocator+ "' ");
				//CoreLib.LOGGER.info("Waiting for Element  '" + elmtLocator+ "' ");
			}

			if (i == waitTime)

			{
				CoreLib.LOGGER.error("Element  '" + elmtLocator
						+ "' =not found after " + waitTime + " seconds ");
				Assert.fail("Element  '" + elmtLocator + "' =not found after "
						+ waitTime + " seconds ");

			}

		} catch (Exception e) {
			CoreLib.LOGGER.error("Element  ' " + elmtLocator
					+ " ' =not found after " + waitTime + " seconds "
					+ e.getMessage());
			Assert.fail("Element  '" + elmtLocator + "' =not found after "
					+ waitTime + " seconds " + e.getMessage());
		}
	}
	
	public static void fWaitForElementPresent(WebDriver driver,int waitTime, String elmtLocator,String fieldName,Boolean flag)
			throws Exception {
		try {
			int i = 0;
			if (waitTime > 1000)
				waitTime = waitTime / 1000;

			for (i = 0; i < waitTime; i++) {
				By by = locatorToByObj(driver,elmtLocator);

				if (by == null)
					Thread.sleep(1000);
				else
					break;

				System.out.println("Waiting for Element  '" + elmtLocator+ "' ");
				//CoreLib.LOGGER.info("Waiting for Element  '" + elmtLocator+ "' ");
			}

			if (i == waitTime)

			{
				CoreLib.LOGGER.error("Element  '" + elmtLocator
						+ "' =not found after " + waitTime + " seconds ");
				Assert.fail("Element  '" + elmtLocator + "' =not found after "
						+ waitTime + " seconds ");

			}
			
			if(flag)
				DriverLib.fTakeScreenShot(driver, GlobalVars.testName, fieldName);

		} catch (Exception e) {
			CoreLib.LOGGER.error("Element  ' " + elmtLocator
					+ " ' =not found after " + waitTime + " seconds "
					+ e.getMessage());
			Assert.fail("Element  '" + elmtLocator + "' =not found after "
					+ waitTime + " seconds " + e.getMessage());
		}
	}
	
	public static void fWaitForElementPresent(WebDriver driver,int waitTime, String elmtLocator,String fieldName)
			throws Exception {
		try {
			int i = 0;
			if (waitTime > 1000)
				waitTime = waitTime / 1000;

			for (i = 0; i < waitTime; i++) {
				By by = locatorToByObj(driver,elmtLocator);

				if (by == null)
					Thread.sleep(1000);
				else
					break;

				System.out.println("Waiting for Element  '" + elmtLocator+ "' ");
				//CoreLib.LOGGER.info("Waiting for Element  '" + elmtLocator+ "' ");
			}

			if (i == waitTime)

			{
				CoreLib.LOGGER.error("Element  '" + elmtLocator
						+ "' =not found after " + waitTime + " seconds ");
				Assert.fail("Element  '" + elmtLocator + "' =not found after "
						+ waitTime + " seconds ");

			}
			
			

		} catch (Exception e) {
			CoreLib.LOGGER.error("Element  ' " + elmtLocator
					+ " ' =not found after " + waitTime + " seconds "
					+ e.getMessage());
			Assert.fail("Element  '" + elmtLocator + "' =not found after "
					+ waitTime + " seconds " + e.getMessage());
		}
	}

	public static void fWaitForElementPresent(WebDriver driver,int waitTime, String elmtLocator)
			throws Exception {
		try {
			int i = 0;
			if (waitTime > 1000)
				waitTime = waitTime / 1000;

			for (i = 0; i < waitTime; i++) {
				By by = locatorToByObj(driver,elmtLocator);

				if (by == null)
					Thread.sleep(1000);
				else
					break;

				System.out.println("Waiting for Element  '" + elmtLocator+ "' ");
				//CoreLib.LOGGER.info("Waiting for Element  '" + elmtLocator+ "' ");
			}

			if (i == waitTime)

			{
				CoreLib.LOGGER.error("Element  '" + elmtLocator
						+ "' =not found after " + waitTime + " seconds ");
				Assert.fail("Element  '" + elmtLocator + "' =not found after "
						+ waitTime + " seconds ");

			}
			
			

		} catch (Exception e) {
			CoreLib.LOGGER.error("Element  ' " + elmtLocator
					+ " ' =not found after " + waitTime + " seconds "
					+ e.getMessage());
			Assert.fail("Element  '" + elmtLocator + "' =not found after "
					+ waitTime + " seconds " + e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 14/10/2014
	 * @author : EGenji
	 * @param waitTime
	 *            : Timeout in seconds
	 * @param elmtLocator
	 *            : Element locator
	 * @throws Exception
	 * @throws : TimeoutException
	 * @purpose : Waits for the element not present identified by the locator
	 * ********************************************************************************************************************************************************************/

	public static void fVerifyElementNotPresent(String elmtLocator)
			throws Exception {
		try {
			if (DriverLib.fIsElementNotPresent(elmtLocator))
				CoreLib.LOGGER
						.info("'"
								+ elmtLocator
								+ "' Locator Not found in the Web Page. Thats Expected...");
			else {
				CoreLib.LOGGER.error(elmtLocator
						+ " fVerifyElementNotPresent() = Failed");
				Assert.fail("'"
						+ elmtLocator
						+ "' Locator found in the Web Page. Thats Not Expected...");
			}
		} catch (Exception timeoutException) {
			CoreLib.LOGGER.error("Element ' " + elmtLocator + " ' = found"
					+ timeoutException.getMessage());
			Assert.fail("Element ' " + elmtLocator + "' = found"
					+ timeoutException.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 14/10/2014
	 * @author : EGenji
	 * @param waitTime
	 *            : Timeout in seconds
	 * @param elmtLocator
	 *            : Element locator
	 * @throws Exception
	 * @throws : TimeoutException
	 * @purpose : verifies for the element present identified by the locator
	 * ********************************************************************************************************************************************************************/

	public static void fVerifyElementPresent(String elmtLocator)
			throws Exception {
		try {

			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			boolean flag = DriverLib.fIsElementPresent(elmtLocator);
			Assert.assertTrue(
					flag,
					"'"
							+ elmtLocator
							+ " Not Present in the Web Page. Please Check the Locator..");
			CoreLib.LOGGER.info("Element Verified At the Locator '"
					+ elmtLocator + "'");

		} catch (Exception timeoutException) {
			CoreLib.LOGGER.error("Element ' " + elmtLocator
					+ " '  not found in the Web Page."
					+ timeoutException.getMessage());
			Assert.fail("Element ' " + elmtLocator
					+ " '  not found in the Web Page."
					+ timeoutException.getMessage());
		}
	}

	public static void fVerifyElementPresent(WebDriver driver,String elmtLocator,String fieldName,Boolean falg) throws Exception {
		try 
		{

			DriverLib.fWaitForElementPresent(driver,GlobalVars.waitTime, elmtLocator);
			boolean flag = DriverLib.fIsElementPresent(elmtLocator);
			Assert.assertTrue(flag,"'"+ fieldName+ "' Not Present in the Web Page. Please Check the Locator..'"+elmtLocator+"'");
			CoreLib.LOGGER.info(""+fieldName+"' Element found in the Page.");

		} 
		catch (Exception timeoutException)
		{
			CoreLib.LOGGER.error("Element ' " + elmtLocator+ " '  not found in the Web Page."+ timeoutException.getMessage());
			Assert.fail("Element ' " + elmtLocator+ " '  not found in the Web Page."+ timeoutException.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 07/04/2014
	 * @author : EGenji
	 * @param waitTime
	 *            : Timeout in seconds
	 * @param eLocator
	 *            : Element locator
	 * @param text
	 *            : Expected text
	 * @throws : TimeoutException
	 * @purpose : Verifies whether actual matches with the expected text
	 *          identified by the locator
	 * ********************************************************************************************************************************************************************/

	public static void fWaitForText(int waitTime, String eLocator, String vText)
			throws Exception {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver,
					waitTime);
			WebElement webElement = DriverLib.fGetWebElement(eLocator);
			// By byValue=locatorToByObj(eLocator);

			if (webElement != null)
				webDriverWait.until(ExpectedConditions
						.textToBePresentInElement(webElement, vText));
			else
				Assert.fail("Unable to Identify the Obejct '" + eLocator + "'");

		} catch (TimeoutException timeoutException) {
			CoreLib.LOGGER.error("Element  ' " + eLocator
					+ " ' text not found after " + waitTime + " seconds ");
			Assert.fail("Element  ' " + eLocator + " ' text not found after "
					+ waitTime + " seconds " + timeoutException.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 17/10/2014
	 * @author : EGenji
	 * @param waitTime
	 *            : Timeout in seconds
	 * @param eLocator
	 *            : Element locator
	 * @param vValue
	 *            : Expected value
	 * @throws : TimeoutException
	 * @purpose : Verifies whether actual matches with the expected value
	 *          identified by the locator
	 * ********************************************************************************************************************************************************************/

	public static void fWaitForValue(int waitTime, String eLocator,
			String vValue) throws Exception {
		try {
			WebElement webElement = DriverLib.fGetWebElement(eLocator);

			if (webElement != null) {
				for (int iSec = 0; iSec <= waitTime; iSec++)
					if (webElement.getAttribute("value").equals(vValue))
						return;

			} else
				Assert.fail("Unable to Identify the Obejct '" + eLocator + "'");

		} catch (TimeoutException timeoutException) {
			CoreLib.LOGGER.error("Element  ' " + eLocator
					+ " ' value not found after " + waitTime + " seconds ");
			Assert.fail("Element  ' " + eLocator + " ' value not found after "
					+ waitTime + " seconds " + timeoutException);
		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fWaitForTitleTextPresent(int waitTime, String vText)
			throws Exception {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver,
					waitTime);
			webDriverWait = new WebDriverWait(driver, waitTime);
			webDriverWait.until(ExpectedConditions.titleIs(vText));

		} catch (TimeoutException timeoutException) {
			CoreLib.LOGGER.error("Text not found after " + waitTime
					+ " seconds.");
			Assert.fail("Text not found after " + waitTime + " seconds."
					+ timeoutException);
		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fVerifyText(String eLocator, String vExpectedText)
			throws Exception {
		String elActualText = null;
		try {

			fWaitForElementPresent(GlobalVars.waitTime, eLocator);
			WebElement el = fGetWebElement(eLocator);
			elActualText = el.getText().trim();

			CoreLib.LOGGER.info("'" + eLocator + "' Actual   Value '"
					+ elActualText + "'");
			CoreLib.LOGGER.info("'" + eLocator + "' Expected Value '"
					+ vExpectedText + "'");

			Assert.assertEquals(elActualText, vExpectedText,
					"Mis Match the values of the Locator..'" + eLocator + "'");

			CoreLib.LOGGER.info("Value Verified  '" + eLocator
					+ "' Value Verified with  '" + vExpectedText + "'");

		} catch (Exception e) {
			CoreLib.LOGGER.error("'" + eLocator
					+ "' Verify Text Failed..Actual Text ," + elActualText
					+ "' Expected Text  '" + vExpectedText + "'");
			Assert.fail("'" + eLocator + "' Verify Text Failed..Actual Text ,"
					+ elActualText + "' Expected Text  '" + vExpectedText + "'");
		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fVerifyNotText(String eLocator, String vExpectedText)
			throws Exception {
		String elActualText = null;
		try {
			WebElement el = fGetWebElement(eLocator);
			elActualText = el.getText().trim();

			CoreLib.LOGGER.info("'" + eLocator + "' Actual   Value '"
					+ elActualText + "'");
			CoreLib.LOGGER.info("'" + eLocator + "' Expected Value '"
					+ vExpectedText + "'");

			Assert.assertNotEquals(elActualText, vExpectedText);

			CoreLib.LOGGER.info("Value Verified  '" + eLocator
					+ "' Value Verified with  '" + vExpectedText + "'");

		} catch (Exception e) {
			CoreLib.LOGGER.error("'" + eLocator
					+ "' Verify Text Failed..Actual Text ," + elActualText
					+ "' Expected Text  '" + vExpectedText + "'");
			Assert.fail("'" + eLocator + "' Verify Text Failed..Actual Text ,"
					+ elActualText + "' Expected Text  '" + vExpectedText + "'");
		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fVerifyContainsText(String eLocator, String vExpectedText)
			throws Exception {
		String elActualText = null;
		try {
			WebElement el = fGetWebElement(eLocator);
			elActualText = el.getText().trim();
			Boolean flag = elActualText.contains(vExpectedText);

			CoreLib.LOGGER.info("'" + eLocator + "' Actual   Value '"
					+ elActualText + "'");
			CoreLib.LOGGER.info("'" + eLocator + "' Expected Value '"
					+ vExpectedText + "'");

			Assert.assertTrue(flag);
			CoreLib.LOGGER.info("Value Verified  '" + vExpectedText
					+ "' Value Contains At the Lcoator '" + eLocator + "'");
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("'" + eLocator
							+ "' Verify Contains Text Failed..Actual Text ,"
							+ elActualText + "' Expected Text  '"
							+ vExpectedText + "'");
			Assert.fail("'" + eLocator
					+ "' Verify Contains Text Failed..Actual Text ,"
					+ elActualText + "' Expected Text  '" + vExpectedText + "'");
		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/
	public static void fVerifyValue(String eLocator, String vExpectedValue)
			throws Exception {
		try {
			String elActualValue = null;
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eLocator);
			WebElement el = fGetWebElement(eLocator);
			if (el == null)
				Assert.fail("Unable to find the Element '" + eLocator + "'");
			else
				elActualValue = el.getAttribute("value").trim();

			CoreLib.LOGGER.info("'" + eLocator + "' Actual   Value ::"
					+ elActualValue);
			CoreLib.LOGGER.info("'" + eLocator + "' Expected Value ::"
					+ vExpectedValue);

			Assert.assertEquals(elActualValue, vExpectedValue,
					"Value Mis Matched at the Locator '" + eLocator + "'");

			CoreLib.LOGGER.info("Value Verified  At the Locator '" + eLocator
					+ "' with Value '" + vExpectedValue + "'");

		} catch (TimeoutException timeoutException) {

			CoreLib.LOGGER
					.error("Error occured while verifying the value of the Locator '"
							+ eLocator
							+ "' with value '"
							+ vExpectedValue
							+ "'");
			Assert.fail("Error occured while verifying the value of the Locator '"
					+ eLocator + "' with value '" + vExpectedValue + "'");
		}

	}

	public static void fVerifyContainsValue(String eLocator,
			String vExpectedValue) throws Exception {
		try {
			String elActualValue = null;
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eLocator);
			WebElement el = fGetWebElement(eLocator);
			if (el == null)
				Assert.fail("Unable to find the Element '" + eLocator + "'");
			else
				elActualValue = el.getAttribute("value").trim();

			CoreLib.LOGGER.info("'" + eLocator + "' Actual   Value ::"
					+ elActualValue);
			CoreLib.LOGGER.info("'" + eLocator + "' Expected Value ::"
					+ vExpectedValue);

			Boolean flag = elActualValue.contains(vExpectedValue);
			Assert.assertTrue(flag);
			CoreLib.LOGGER.info("Contains Value Verified  At the Locator '"
					+ eLocator + "' with Value '" + vExpectedValue + "'");

		} catch (TimeoutException timeoutException) {

			CoreLib.LOGGER
					.error("Error occured while verifying the contains value of the Locator '"
							+ eLocator
							+ "' with value '"
							+ vExpectedValue
							+ "'");
			Assert.fail("Error occured while verifying the contains value of the Locator '"
					+ eLocator + "' with value '" + vExpectedValue + "'");
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 07/04/2014
	 * @author : EGenji
	 * @param eLocator
	 *            : Element locator
	 * @param vExpectedText
	 *            : Expected text
	 * @throws : TimeoutException
	 * @purpose : Verifies whether actual contains expected text identified by
	 *          the locator
	 * ********************************************************************************************************************************************************************/

	/*
	 * public static boolean fVerifyContainsText(String eLocator,String
	 * vExpectedText) throws Exception { String elActualText=null; boolean
	 * bStatus; try{ WebElement el=fGetWebElement( eLocator);
	 * elActualText=el.getText().trim(); bStatus =
	 * elActualText.contains(vExpectedText); if(bStatus)return true; else return
	 * false; } catch(Exception e){
	 * CoreLib.fWriteLogMessages("'"+eLocator+"' Verify Text Failed..Actual Text ,"
	 * +elActualText+"' Expected Text  '"+vExpectedText+"'");
	 * fail("'"+eLocator+"' Verify Text Failed..Actual Text ,"
	 * +elActualText+"' Expected Text  '"+vExpectedText+"'"); } return true; }
	 */
	/*******************************************************************************************************************************************************************
	 * @Date Created : 24/07/2014
	 * @author :
	 * @Purpose :
	 * @Updated by :
	 * @Date Updated :
	 * ********************************************************************************************************************************************************************/

	public static void fInputText(WebDriver driver,String eLocator, String vExpectedText,String fieldName,Boolean flag)
			throws Exception {
		try {

			fWaitForElementPresent(driver,GlobalVars.waitTime, eLocator,fieldName);
			WebElement el = fGetWebElement(driver,eLocator);

			if (el.isEnabled()) 
			{
				el.clear();
				el.sendKeys(vExpectedText);
			}

			CoreLib.LOGGER.info("'" + vExpectedText+ "' Value entered into the '"+fieldName+"' Text Box.");
			
			if(flag)
				DriverLib.fTakeScreenShot(driver, GlobalVars.testName, fieldName);
		
		}
		catch (Exception e) 
		{
			CoreLib.LOGGER.error("Error occured while entering the text into Element  ' "+ fieldName + " '");
			Assert.fail("Error occured while entering the text into Element  ' "+ fieldName + " '");
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 24/07/2014
	 * @author :
	 * @Purpose :
	 * @Updated by :
	 * @Date Updated :
	 * ********************************************************************************************************************************************************************/

	public static void fTypeText(String eLocator, String vExpectedText)
			throws Exception {
		try {
			fWaitForElementPresent(GlobalVars.waitTime, eLocator);
			// Selenium sel = new
			// WebDriverBackedSelenium(driver,driver.getCurrentUrl());
			// sel.type(eLocator, vExpectedText);

			CoreLib.LOGGER.info("'" + vExpectedText
					+ "' Type the Text into Input Box at the Locator '"
					+ eLocator + "'");
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while entering the value into textbox using Backed Selenium");
			Assert.fail("Error occured while entering the value into textbox using Backed Selenium"
					+ e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 27/04/2014
	 * @author : EGenji
	 * @param eButtonID
	 *            : Locator of confirmation button from .xml file.
	 * @param tConfirmMessage
	 *            : Confirmation message from .xml file.
	 * @purpose : Click on a button with confirmation message.
	 * @throws : AssertionError
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	/*
	 * public static void bClickButtonWithConfirm(String eButtonID,String
	 * tConfirmMessage) throws Exception{
	 * 
	 * 
	 * String vActualConfirmMsg=null;
	 * 
	 * try {
	 * 
	 * String eConfirmText=CoreLib.fGetNodeText(GlobalVars.locOfCtrlPrms+
	 * "MVUK_GlobElements.xml", "GlobElements", "EConfirmText"); String
	 * eContinue
	 * =CoreLib.fGetNodeText(GlobalVars.locOfCtrlPrms+"MVUK_GlobElements.xml",
	 * "GlobElements", "EContinue");
	 * 
	 * DriverUtils.fClickAndWait(eButtonID);
	 * 
	 * Alert alert = driver.switchTo().alert();
	 * 
	 * vActualConfirmMsg=alert.getText();
	 * CoreLib.LOGGER.info(" Actual Confirmation Message  ::"
	 * +vActualConfirmMsg);
	 * CoreLib.LOGGER.info(" Expected Confirmation Message  ::"
	 * +tConfirmMessage); Assert.assertEquals(vActualConfirmMsg,
	 * tConfirmMessage," Mis Matched the Confirmation Message"); alert.accept();
	 * //Thread.sleep(3000);
	 * 
	 * String FormID =DriverUtils.fGetText(eConfirmText); GlobalVars.vFormID =
	 * AppUtils.extractDigits(FormID);
	 * 
	 * 
	 * 
	 * if(DriverUtils.isElementPresent(eContinue))
	 * DriverUtils.fClickAndWait(eContinue);
	 * 
	 * 
	 * Thread.sleep(5000);
	 * 
	 * }catch(AssertionError ae){ CoreLib.LOGGER.error(
	 * "Error Occured verifying the Confirm Message  Actual Confirm Message  '"
	 * +vActualConfirmMsg
	 * +"'  and Expected Confirm Message  "+vActualConfirmMsg); Assert.fail(
	 * "Error Occured verifying the Confirm Message  Actual Confirm Message  '"
	 * +vActualConfirmMsg
	 * +"'  and Expected Confirm Message  "+vActualConfirmMsg); }catch
	 * (Exception e) {
	 * CoreLib.LOGGER.error("bClickButtonWithConfirm() = Failed ....>"
	 * +e.getMessage());
	 * Assert.fail("bClickButtonWithConfirm() = Failed ....>"+e.getMessage()); }
	 * }
	 */

	/*******************************************************************************************************************************************************************
	 * @Date Created : 27/04/2014
	 * @author : EGenji
	 * @param eButtonID
	 *            : Locator of confirmation button from .xml file.
	 * @param tConfirmMessage
	 *            : Confirmation message from .xml file.
	 * @purpose : Click on a button with confirmation message.
	 * @throws : AssertionError
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void bClickButtonWithConfirm(String eButtonID,
			String tConfirmMessage, String ModCode) throws Exception {
		try {
			DriverLib.fGetWebElement(eButtonID).isEnabled();
			String winHandleBefore = driver.getWindowHandle();
			DriverLib.fGetWebElement(eButtonID).click();
			Alert alert = driver.switchTo().alert();
			String vActualConfirmMsg = alert.getText();
			Thread.sleep(1000);
			if (tConfirmMessage.equals(vActualConfirmMsg)) {
				alert.accept();
				CoreLib.LOGGER.info("Confirm message  '" + tConfirmMessage
						+ "' = is verified. ");
				CoreLib.LOGGER.info("bClickButtonWithConfirm() = Passed ....>");
			} else {
				CoreLib.LOGGER
						.error("bClickButtonWithConfirm() = Failed ....>");
				Assert.fail("Actual confirm message is '" + vActualConfirmMsg
						+ "'." + "Expected confirm message is '"
						+ tConfirmMessage + "'.");
			}

			driver.switchTo().window(winHandleBefore);
			Thread.sleep(3000);

		} catch (AssertionError ae) {
			CoreLib.LOGGER.error("Confirm message  '" + tConfirmMessage
					+ "' = is not presented. " + ae);
			throw new AssertionError("Confirm message '" + tConfirmMessage
					+ "' is not presented." + ae);
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("bClickButtonWithConfirm() = Failed ....>" + e);
			Assert.fail("bClickButtonWithConfirm() = Failed ....>" + e);
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 27/04/2014
	 * @author : EGenji
	 * @param eButtonID
	 *            : Locator of confirmation button from .xml file.
	 * @param tConfirmMessage
	 *            : Confirmation message from .xml file.
	 * @purpose : Click on a button with no confirmation message and store Form
	 *          ID from the redirected page.
	 * @throws : AssertionError
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	/*
	 * public static void bClickButtonWithNoConfirmation(String eButtonID)
	 * throws Exception{ try {
	 * DriverUtils.fGetWebElement(eButtonID).isEnabled(); String winHandleBefore
	 * = driver.getWindowHandle();
	 * DriverUtils.fGetWebElement(eButtonID).click();
	 * 
	 * driver.switchTo().window(winHandleBefore); Thread.sleep(3000);
	 * String FormID = driver.findElement(By.xpath(
	 * "//div[@id='moduleHeading']//div[@class='moduleText']")).getText();
	 * System.out.println("FormID Bfr Extraction: "+FormID); GlobalVars.vFormID
	 * = AppUtils.extractDigits(FormID);
	 * System.out.println("FormID Aftr Extraction:"+FormID); WebElement
	 * elContinue
	 * =DriverUtils.fGetWebElementOrNull(CoreLib.fGetNodeText(GlobalVars
	 * .locOfCtrlPrms+"MVUK_GlobElements.xml", "GlobElements", "EContinue")); if
	 * (elContinue!=null) elContinue.click(); Thread.sleep(5000);
	 * 
	 * }catch (Exception e) {
	 * CoreLib.LOGGER.error("bClickButtonAndStoreFormID() = Failed ....>"+e);
	 * Assert.fail("bClickButtonAndStoreFormID() = Failed ....>"+e); } }
	 */
	/*******************************************************************************************************************************************************************
	 * @Date Created : 27/04/2014
	 * @author : EGenji
	 * @param eButtonID
	 *            : Locator of the element from .xml file.
	 * @purpose : Click on an element.
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fClickElement(String eButtonID) {
		try {

			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eButtonID);
			WebElement element = DriverLib.fGetWebElement(eButtonID);
			element.isEnabled();
			/*
			 * if(GlobalVars.browser.equalsIgnoreCase("IE"))
			 * element.sendKeys(Keys.RETURN); else
			 */
			element.click();
			Thread.sleep(2000);
			CoreLib.LOGGER.info("Clicked at the Locator '" + eButtonID + "'");
		} catch (Exception e) {
			CoreLib.LOGGER.error("fClickElement() = Failed ....>'" + eButtonID
					+ "'");
			Assert.fail("fClickElement() = Failed ....>'" + eButtonID + "'"
					+ e.getMessage());
		}
	}
	
	public static void fClickElement(WebDriver driver,String eButtonID,String fieldName,Boolean flag) 
	{
		try 
		{

			DriverLib.fWaitForElementPresent(driver,GlobalVars.waitTime, eButtonID);
			WebElement element = DriverLib.fGetWebElement(eButtonID);
			element.click();
			Thread.sleep(2000);
			CoreLib.LOGGER.info("Clicked on the '" + fieldName + "'");
			
			if(flag)
				DriverLib.fTakeScreenShot(driver, GlobalVars.testName, fieldName);
			
		} 
		catch (Exception e) 
		{
			CoreLib.LOGGER.error("Error occured while Clicking on the '"+fieldName+"' with '"+eButtonID+"'");
			Assert.fail("Error occured while Clicking on the '"+fieldName+"' with '"+eButtonID+"'");
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 31/12/2014
	 * @author : EGenji
	 * @param eButtonID
	 *            : WebElement
	 * @purpose : Click on an element.
	 * @throws : Exception Errorw
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void bClickElement(WebElement eButtonID) throws Exception {
		try {
			eButtonID.isEnabled();
			eButtonID.click();
			Thread.sleep(6000);
			CoreLib.LOGGER
					.info("Clicked at the WebElement '" + eButtonID + "'");
		} catch (Exception e) {
			CoreLib.LOGGER.error("fClickElement() = Failed ....>'" + eButtonID
					+ "'");
			Assert.fail("fClickElement() = Failed ....>'" + eButtonID + "'"
					+ e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 12/11/2014
	 * @author : EGenji
	 * @param eButtonID
	 *            : Locator of the element from .xml file.
	 * @purpose : Checks element identified by the locator
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void bCheckElement(String eButtonID) throws Exception {
		try {

			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eButtonID);
			WebElement locBy = DriverLib.fGetWebElement(eButtonID);
			if (locBy.isSelected() == false && locBy.isEnabled()) {
				locBy.click();
			}
			Thread.sleep(500);

			// Assert.assertTrue(DriverUtils.fGetWebElement(eButtonID).isSelected());
			// CoreLib.fWriteLogMessages("bCheckElement() = Passed ....>");
			CoreLib.LOGGER.info("Check Element at the Locator '" + eButtonID
					+ "'");

		} catch (Exception e) {
			CoreLib.LOGGER.error("bCheckElement() = Failed ....>'" + eButtonID
					+ "'");
			Assert.fail("bCheckElement() = Failed ....>'" + eButtonID + "'");
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 12/11/2014
	 * @author : EGenji
	 * @param eButtonID
	 *            : Locator of the element from .xml file.
	 * @purpose : Checks element identified by the locator
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void bUnCheckElement(String eButtonID) throws Exception {
		try {

			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eButtonID);
			WebElement locBy = DriverLib.fGetWebElement(eButtonID);
			if (locBy.isSelected() == true && locBy.isEnabled()) {
				locBy.click();
			}
			Thread.sleep(500);

			// Assert.assertTrue(DriverUtils.fGetWebElement(eButtonID).isSelected());
			// CoreLib.fWriteLogMessages("bCheckElement() = Passed ....>");
			CoreLib.LOGGER.info(" Un Check Element at the Locator '"
					+ eButtonID + "'");

		} catch (Exception e) {
			CoreLib.LOGGER.error("bUnCheckElement() = Failed ....>'"
					+ eButtonID + "'");
			Assert.fail("bUnCheckElement() = Failed ....>'" + eButtonID + "'");
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 09/12/2014
	 * @author : EGenji
	 * @param url
	 *            : the locator of the object
	 * @Purpose : Blur event fired on the Locator
	 * @Updated by :
	 * @Date Updated :
	 ********************************************************************************************************************************************************************/

	public static void bFireEvent(String eLocator) {
		try {
			WebElement element = fGetWebElement(eLocator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0]..blur();", element);

			CoreLib.LOGGER.error("Fire Event on the Locator  '" + eLocator
					+ "'");
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error Occured while executing the Fire event on the Locator '"
							+ eLocator + "' " + e.getMessage());
			Assert.fail("Error Occured while executing the Fire event on the Locator '"
					+ eLocator + "' " + e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 05/01/2015
	 * @author : EGenji
	 * @param url
	 *            : the locator of the object
	 * @Purpose : Verify the The Enabled Property of the Locator
	 * @Updated by :
	 * @Date Updated :
	 ********************************************************************************************************************************************************************/
	public static void fVerifyEnabled(String eLocator) {
		try {
			WebElement el = fGetWebElement(eLocator);
			Boolean flag = el.isEnabled();
			Assert.assertTrue(flag,
					"Mis Matched the Enabled Property Value of the Locator '"
							+ eLocator + "'");
			CoreLib.LOGGER.info("Verified The Enabled property of the Locator'"
					+ eLocator + "' ");
		} catch (NoSuchElementException nsee) {
			CoreLib.LOGGER
					.error("Error occured while verifying the Enabled Property of the Locator ' "
							+ eLocator + "'");
			Assert.fail("Error occured while verifying the Enabled Property of the Locator ' "
					+ eLocator + "'");
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 05/01/2015
	 * @author : EGenji
	 * @param url
	 *            : the locator of the object
	 * @Purpose : Verify the The Enabled Property of the Locator
	 * @Updated by :
	 * @Date Updated :
	 ********************************************************************************************************************************************************************/
	public static void fVerifyNotEnabled(String eLocator) {
		try {
			WebElement el = fGetWebElement(eLocator);
			Boolean flag = el.isEnabled();
			Assert.assertFalse(flag,
					"Mis Matched the Enabled Property Value of the Locator '"
							+ eLocator + "'");
			CoreLib.LOGGER.info("Verified The Enabled property of the Locator'"
					+ eLocator + "' ");
		} catch (NoSuchElementException nsee) {
			CoreLib.LOGGER
					.error("Error occured while verifying the Enabled Property of the Locator ' "
							+ eLocator + "'");
			Assert.fail("Error occured while verifying the Enabled Property of the Locator ' "
					+ eLocator + "'");
		}

	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static boolean isEditable(String eLocator) throws Exception {
		try {
			WebElement el = fGetWebElement(eLocator);
			fWaitForElementPresent(3, eLocator);
			switch (GlobalVars.browser) {

			case "FF":
				if (el.getAttribute("readonly") != "")
					return true;
				if ((el.getAttribute("disabled") != "disabled" || el
						.getAttribute("disabled").equals(false)))
					return true;
				break;

			case "IE":
				if (el.getAttribute("readonly") != "readonly")
					return true;
				break;
			}
		} catch (NoSuchElementException nsee) {
			CoreLib.LOGGER.error("Element  not found. ");
			Assert.fail("Element not found. " + nsee.getMessage());
		}
		return false;
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static boolean fIsRadioBtOrCheckBxSelected(WebElement webElement)
			throws Exception {
		try {
			return webElement.isSelected();
		} catch (NoSuchElementException nsee) {
			CoreLib.LOGGER.error("Element  not found. " + webElement);
			Assert.fail("Element not found. " + nsee);
			return false;
		}

	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fClickAndWait(WebElement webElement) throws Exception {
		try {
			webElement.click();
			Thread.sleep(500);
			CoreLib.LOGGER.info("Clicked on the Web Element  '" + webElement
					+ "'");

		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Clicking on the Element  ..."
							+ e.getMessage());
			Assert.fail("Error occured while Clicking on the Element  ..."
					+ e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created      : 09/07/2014
	 * @author            : EGenji
	 * @param elmtLocator : Locator of the element
	 * @purpose           : Click and wait at the Locator
	 * @throws            : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fClickAndWait(WebDriver driver,String eLocator,String fieldName,Boolean flag) throws Exception {
		try
		{
			DriverLib.fWaitForElementPresent(driver,GlobalVars.waitTime, eLocator,fieldName);
			WebElement webElement = fGetWebElement(driver,eLocator);
			if (webElement != null)
				webElement.click();
			else
				Assert.fail("Unable to Identify the Locator of '" + fieldName + "' with '"+eLocator+"'");
			Thread.sleep(1000);
			CoreLib.LOGGER.info("Clicked on the Locator  '" + fieldName + "'");
			
			if(flag)
				DriverLib.fTakeScreenShot(driver, GlobalVars.testName, fieldName);
		} 
		catch (Exception e) 
		{
			CoreLib.LOGGER.error("Error occured while Clicking on the Element  ..."+ fieldName + "' with '"+eLocator+"'" + e.getMessage());
			Assert.fail("Error occured while Clicking on the Element  ..."+ fieldName + "' with '"+eLocator+"'"+ e.getMessage());
		}
	}
	
	public static void fClickAndWait(WebDriver driver,String eLocator,String fieldName) throws Exception {
		try
		{
			DriverLib.fWaitForElementPresent(driver,GlobalVars.waitTime, eLocator,fieldName);
			WebElement webElement = fGetWebElement(driver,eLocator);
			if (webElement != null)
				webElement.click();
			else
				Assert.fail("Unable to Identify the Locator of '" + fieldName + "' with '"+eLocator+"'");
			Thread.sleep(1000);
			CoreLib.LOGGER.info("Clicked on the Locator  '" + fieldName + "'");
			
			
		} 
		catch (Exception e) 
		{
			CoreLib.LOGGER.error("Error occured while Clicking on the Element  ..."+ fieldName + "' with '"+eLocator+"'" + e.getMessage());
			Assert.fail("Error occured while Clicking on the Element  ..."+ fieldName + "' with '"+eLocator+"'"+ e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 09/07/2014
	 * @author : EGenji
	 * @param elmtLocator
	 *            : Locator of the element from .xml file.
	 * @purpose : Click on an Enter key of the Key Board.
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fClickEnter(String elmtLocator) throws Exception {
		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			WebElement ele = fGetWebElement(elmtLocator);

			if (ele != null) {
				Thread.sleep(1000);
				ele.sendKeys(Keys.RETURN);
				Thread.sleep(2000);
			} else
				Assert.fail("Unable to Identify the Object '" + elmtLocator
						+ "'");

			CoreLib.LOGGER.info("Clicked Enter Key at Locator '" + elmtLocator
					+ "  ");

		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Clicking Enter on the Element  ' "
							+ elmtLocator + " '" + e.getMessage());
			Assert.fail("Error occured while Clicking Enter on the Element ' "
					+ elmtLocator + " " + e.getMessage());
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 09/07/2014
	 * @author : EGenji
	 * @param elmtLocator
	 *            : Locator of the element from .xml file.
	 * @purpose : Click on an Tab key of the Key Board.
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fClickTab(String elmtLocator) throws Exception {
		try {

			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			WebElement ele = fGetWebElement(elmtLocator);
			if (ele != null) {
				Thread.sleep(1000);
				ele.sendKeys(Keys.TAB);
				Thread.sleep(1000);
			} else
				Assert.fail("Unable to Identify the Object '" + elmtLocator
						+ "'");

			CoreLib.LOGGER.info("Clicked Tab Key at Locator '" + elmtLocator
					+ "'");

		} catch (Exception e) {
			CoreLib.LOGGER.error("Unable to  Click on tab key   ' "
					+ elmtLocator + "'" + e.getMessage());
			Assert.fail("Unable to  Click on tab key   ' " + elmtLocator + "'"
					+ e.getMessage());

		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 09/07/2014
	 * @author : EGenji
	 * @param elmtLocator
	 *            : Locator of the element from .xml file.
	 * @purpose : Click on an Tab key of the Key Board.
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fClickTab() throws Exception {
		try {

			Actions builder = new Actions(driver);
			builder.sendKeys(Keys.TAB).build().perform();
			Thread.sleep(1000);
			System.out.println("Tab1");

			CoreLib.LOGGER.info("Clicked Tab Key...");

		} catch (Exception e) {
			CoreLib.LOGGER.error("Unable to  Click on tab key ....."
					+ e.getMessage());
			Assert.fail("Unable to  Click on tab key ......" + e.getMessage());

		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 09/07/2014
	 * @author : EGenji
	 * @param elmtLocator
	 *            : Locator of the element from .xml file.
	 * @purpose : Click on an Sapce Bar key of the Key Board.
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fClickSpaceBar(String elmtLocator) throws Exception {
		try {

			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			WebElement ele = fGetWebElement(elmtLocator);
			if (ele != null) {
				Thread.sleep(1000);
				ele.sendKeys(Keys.SPACE);
				Thread.sleep(1000);

				CoreLib.LOGGER.info("Clicked Sapce Bar Key at Locator '"
						+ elmtLocator + "'");
			} else
				Assert.fail("Unable to Identify the Object '" + elmtLocator
						+ "'");

		} catch (Exception e) {
			CoreLib.LOGGER.error("Unable to  Click on Sapce Bar key   ' "
					+ elmtLocator + "'" + e.getMessage());
			Assert.fail("Unable to  Click on Sapce Bar key   ' " + elmtLocator
					+ "'" + e.getMessage());

		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 09/07/2014
	 * @author : EGenji
	 * @param elmtLocator
	 *            : Locator of the element from .xml file.
	 * @purpose : Switching the focus of the driver from browser to Frame
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fSwitchToFrame(String elmtFrame) throws Exception {
		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtFrame);
			driver.switchTo().frame(fGetWebElement(elmtFrame));
			Thread.sleep(500);

		} catch (NoSuchElementException nsee) {
			CoreLib.LOGGER.error("Element  ' " + elmtFrame + " ' not found");
			Assert.fail("Element  ' " + elmtFrame + " ' not found" + nsee);

		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Swithing from browser to  Frame ' "
							+ elmtFrame + " '");
			Assert.fail("Error occured while Swithing to Frame ' " + elmtFrame
					+ " '" + e.getMessage());
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 02/16/2014
	 * @author : EGenji
	 * @param elmtLocator
	 *            : Locator of the element from .xml file.
	 * @purpose : Switching the focus of the driver from browser to Frame
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fClickADownArrow(String eLocator) throws Exception {
		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eLocator);
			WebElement ele = fGetWebElement(eLocator);
			if (ele != null) {
				Thread.sleep(1000);
				ele.sendKeys(Keys.ARROW_DOWN);
				Thread.sleep(1000);

				CoreLib.LOGGER.info("Clicked Down Arrow Key at Locator '"
						+ eLocator + "'");
			} else
				Assert.fail("Unable to Identify the Object '" + eLocator + "'");

		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while executing the Click Down Arrow Script ' "
							+ eLocator + " '");
			Assert.fail("Error occured while executing the Click Down Arrow Script  ' "
					+ eLocator + " '" + e.getMessage());
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 18/09/2014
	 * @author : EGenji @ @param elmtLocator : Locator of the element from .xml
	 *         file.
	 * @purpose : Switching the focus of the driver from Frame to browser
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	public static void fSwitchFromFrame() {
		try {
			driver.switchTo().defaultContent();
			Thread.sleep(500);
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Swithing from frame to Browser ");
			Assert.fail("Error occured while Swithing from frame to Browser "
					+ e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 19/09/2014
	 * @author : EGenji
	 * @throws elmtLocator
	 *             : Locator of the element from .xml file.
	 * @purpose : for double click on the web element
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	public static void fDoubleClick(String elmtLocator) throws Exception {
		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			WebElement webElement = DriverLib.fGetWebElement(elmtLocator);
			Actions action = new Actions(driver);
			action.moveToElement(webElement).doubleClick().build().perform();
			CoreLib.LOGGER.info("Double Clicked at the Locator '" + elmtLocator
					+ "'");

		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Double Click on the Element '"
							+ elmtLocator + "'");
			Assert.fail("Error occured while Double Click on the Element '"
					+ elmtLocator + "'");
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 19/09/2014
	 * @author : EGenji
	 * @throws elmtLocator
	 *             : Locator of the element from .xml file.
	 * @purpose : for click on the web element using Java Script
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fJSClick(String elmtLocator) throws Exception {
		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			String mouseDown = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mousedown\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";

			((JavascriptExecutor) driver).executeScript(mouseDown,
					fGetWebElement(elmtLocator));

			String mouseUp = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseup\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";

			((JavascriptExecutor) driver).executeScript(mouseUp,
					fGetWebElement(elmtLocator));

			CoreLib.LOGGER
					.info("JS Click At the Locator '" + elmtLocator + "'");
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Click on the Element Using Java Script '"
							+ elmtLocator + "'");
			Assert.fail("Error occured while Click on the Element Using Java Script '"
					+ elmtLocator + "'");
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 19/09/2014
	 * @author : EGenji
	 * @throws elmtLocator
	 *             : Locator of the element from .xml file.
	 * @purpose : for mousedown on the web element using Java Script
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fMouseDown(String elmtLocator) throws Exception {
		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			String mouseDown = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mousedown\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";

			((JavascriptExecutor) driver).executeScript(mouseDown,
					fGetWebElement(elmtLocator));

			CoreLib.LOGGER.info("Mouse Down at the Locator'" + elmtLocator
					+ "' ");
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while mousedown on the Element Using Java Script '"
							+ elmtLocator + "'");
			Assert.fail("Error occured while mousedown on the Element Using Java Script '"
					+ elmtLocator + "'");
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 19/09/2014
	 * @author : EGenji
	 * @throws elmtLocator
	 *             : Locator of the element from .xml file.
	 * @purpose : for mousedown on the web element using Java Script
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fMouseUp(String elmtLocator) throws Exception {
		try {
			String mouseUp = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseup\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";

			((JavascriptExecutor) driver).executeScript(mouseUp,
					fGetWebElement(elmtLocator));

			CoreLib.LOGGER.info(" Mouse Up at the Locator '" + elmtLocator
					+ "'");
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while mouseup on the Element Using Java Script '"
							+ elmtLocator + "'");
			Assert.fail("Error occured while mouseup on the Element Using Java Script '"
					+ elmtLocator + "'");
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 19/09/2014
	 * @author : EGenji
	 * @throws elmtLocator
	 *             : Locator of the element from .xml file.
	 * @purpose : for double click on the web element using Java Script
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fJSDoubleClick(String elmtLocator) throws Exception {
		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			String dblclick = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"dblclick\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";

			((JavascriptExecutor) driver).executeScript(dblclick,
					fGetWebElement(elmtLocator));

			CoreLib.LOGGER.info("JS Double Click at the Locator'" + elmtLocator
					+ "' ");

		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Double Click on the Element Using Java Script '"
							+ elmtLocator + "'");
			Assert.fail("Error occured while Double Click on the Element Using Java Script '"
					+ elmtLocator + "'");

		}
	}

	public static String fGetCurrentUrl() {
		String currentUrl = null;

		try {
			currentUrl = driver.getCurrentUrl();
			CoreLib.LOGGER.info("Current URL  ::" + currentUrl);
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while executing the getCurrent URl method ..."
							+ e.getMessage());
			Assert.fail("Error occured while executing the getCurrent URl method ..."
					+ e.getMessage());

		}

		return currentUrl;
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 24/09/2014
	 * @author : EGenji
	 * @throws elmtLocator
	 *             : Locator of the web element
	 * @throws value
	 *             : value to be entered in the web element
	 * @purpose : for click on the web element and enter the value into the web
	 *          element
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void bClickAndType(String elmtLocator, String value)
			throws Exception {

		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			//DriverLib.fClickAndWait(elmtLocator);
			Thread.sleep(3000);
			DriverLib.fGetWebElement(elmtLocator).clear();
			//DriverLib.fInputText(elmtLocator, value);
			CoreLib.LOGGER.info("'" + elmtLocator
					+ "' Clicked.. and Typed The Value '" + value);

		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Click and Type on the web Element '"
							+ elmtLocator + "' and Value '" + value);
			Assert.fail("Error occured while Click and Type on the Web Element.. '"
					+ elmtLocator + "'");

		}
	}

	public static void fSelectFrameAndDoubleClick(String eFrame,
			String elmtLocator, String tLaocator) throws Exception {
		try {
			Thread.sleep(500);
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eFrame);
			DriverLib.fSwitchToFrame(eFrame);
			DriverLib.fVerifyText(elmtLocator, tLaocator);

			/*
			 * if(GlobalVars.browser.equalsIgnoreCase("IE"))
			 * DriverUtils.bClickElement(elmtLocator); else
			 */
			DriverLib.fJSDoubleClick(elmtLocator);

			DriverLib.fSwitchFromFrame();
			CoreLib.LOGGER.info("'" + elmtLocator
					+ "' fSelectFrameAndClick() = Passed");
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Selecting the frame and Click on the Element Using Java Script '"
							+ elmtLocator + "'");
			Assert.fail("Error occured while Selecting the frame and Click on the Element Using Java Script "
					+ elmtLocator + "'");
		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static WebElement fGetWebElement(String elmtLocator) {
		WebElement webElement = null;
		try {

			By by = locatorToByObj(elmtLocator);

			if (by != null)
				webElement = driver
						.findElement(locatorToByObj(elmtLocator));

			return webElement;

		} catch (Exception e) {
			CoreLib.LOGGER.error("Element  ' " + elmtLocator
					+ " ' =not found after " + GlobalVars.waitTime
					+ " seconds ");
			Assert.fail("Element  ' " + elmtLocator + " ' =not found after "
					+ GlobalVars.waitTime + " seconds " + e);

		}
		return null;
	}
	
	public static WebElement fGetWebElement(WebDriver driver,String elmtLocator) {
		WebElement webElement = null;
		try {

			By by = locatorToByObj(driver,elmtLocator);
			
			System.out.println("By Value ::"+by);

			if (by != null)
				webElement = driver.findElement(by);
			

		} 
		catch (Exception e)
		{
			CoreLib.LOGGER.error("Element  ' " + elmtLocator+ " ' =not found after " + GlobalVars.waitTime+ " seconds ");
			Assert.fail("Element  ' " + elmtLocator + " ' =not found after "+ GlobalVars.waitTime + " seconds " + e);

		}
		return webElement;
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 20/12/2014
	 * @author : EGenji
	 * @throws elmtLocator
	 *             : Locator of the web element
	 * @purpose : for getting text from the web element
	 * @returns : The Text of the Web Element
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	public static String fGetText(String elmtLocator) {
		String strTextValue = null;
		try {
			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, elmtLocator);
			WebElement webElement = fGetWebElement(elmtLocator);

			if (webElement != null)
				strTextValue = webElement.getText();
			else
				Assert.fail("Unable to Identify the Locator '" + elmtLocator
						+ "'");

			CoreLib.LOGGER.info("'" + elmtLocator + "' Text Value  ::"
					+ strTextValue);
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while getting text from the Locator '"
							+ elmtLocator + "'    .." + e.getMessage());
			Assert.fail("Error occured while getting text from the Locator '"
					+ elmtLocator + "'    .." + e.getMessage());
		}

		return strTextValue;
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 20/12/2014
	 * @author : EGenji
	 * @throws elmtLocator
	 *             : Locator of the web element
	 * @purpose : for getting value from the web element
	 * @returns : The value of the Web Element
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static String fGetValue(String elmtLocator) {
		String strTextValue = null;
		try {
			WebElement webElement = fGetWebElement(elmtLocator);

			if (webElement != null)
				strTextValue = webElement.getAttribute("value");
			else
				Assert.fail("Unable to Identify the Locator '" + elmtLocator
						+ "'");

			CoreLib.LOGGER.error("'" + elmtLocator + "' Text Value  ::"
					+ strTextValue);
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while getting value from the Locator '"
							+ elmtLocator + "'    .." + e.getMessage());
			Assert.fail("Error occured while getting text value the Locator '"
					+ elmtLocator + "'    .." + e.getMessage());
		}

		return strTextValue;
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static WebElement fGetWebElementOrNull(String elmtLocator)
			throws Exception {
		WebElement webElement = null;
		try {
			for (int i = 1; i <= 8; i++) {

				webElement = driver
						.findElement(locatorToByObj(elmtLocator));
				if (webElement != null)
					break;
			}
			return webElement;

		} catch (TimeoutException timeoutException) {
			CoreLib.LOGGER.error("Element  ' " + elmtLocator
					+ " ' =not found after " + GlobalVars.waitTime
					+ " seconds ");
			return webElement;

		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fSelectDropDownItem(String eListBox,
			String vLabelOrIndexOrValue, String vBy) throws Exception {
		try {

			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eListBox);
			WebElement elDropDownItem = DriverLib.fGetWebElement(eListBox);

			if (elDropDownItem != null) {
				Select comboBox = new Select(elDropDownItem);
				switch (vBy) {
				case "text":
					comboBox.selectByVisibleText(vLabelOrIndexOrValue);
					break;
				case "index":
					comboBox.selectByIndex(Integer
							.parseInt(vLabelOrIndexOrValue));
					break;
				case "value":
					comboBox.selectByValue(vLabelOrIndexOrValue);
					break;
				}
			} else
				Assert.fail("Unable to identify the Object '" + eListBox + "'");
			Thread.sleep(500);
			CoreLib.LOGGER.info("'" + vLabelOrIndexOrValue
					+ "' value selected from the Dropdown  '" + eListBox
					+ "'  ");

		} catch (Exception e) {
			CoreLib.LOGGER.info("Error occured while selecting the Value  '"
					+ vLabelOrIndexOrValue + "' from the Dropdown  '"
					+ eListBox + "'  " + e.getMessage());
			Assert.fail("Error occured while selecting the Value  '"
					+ vLabelOrIndexOrValue + "' from the Dropdown  '"
					+ eListBox + "'  " + e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fAssertSelectedLabelByAttribute(String eListBox,
			String tExpectedLabel, String attribute) throws Exception {
		try {

			DriverLib.fWaitForElementPresent(GlobalVars.waitTime, eListBox);
			WebElement elGoTo = DriverLib.fGetWebElement(eListBox);
			Select comboBox = new Select(elGoTo);
			String actualLabel;
			if (attribute != "text")
				actualLabel = comboBox.getFirstSelectedOption().getAttribute(
						"value");
			else
				actualLabel = comboBox.getFirstSelectedOption().getText();

			CoreLib.LOGGER.info("'" + eListBox + "'   Selected Value ::"
					+ actualLabel);
			CoreLib.LOGGER.info("'" + eListBox + "'   Expected Value ::"
					+ tExpectedLabel);

			Assert.assertEquals(actualLabel, tExpectedLabel, "The '" + eListBox
					+ "' Selected value Mis Matched");
			CoreLib.LOGGER.info("Verified the Selected value of '" + eListBox
					+ "'   Acutal value " + actualLabel + "Expected Value ::"
					+ tExpectedLabel);

		} catch (AssertionError assertionError) {

			CoreLib.LOGGER.error("Expected label " + tExpectedLabel
					+ " not selected");
			Assert.fail("Expected label " + tExpectedLabel + " not selected "
					+ assertionError);

		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 01/12/2014
	 * @author : EGenji
	 * @param eListBox
	 *            : Dropdown Element
	 * @purpose : Returns list of elements in dropdown
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static List<String> fGetSelectedOptions(String eListBox)
			throws Exception {
		List<WebElement> options = null;
		List<String> listValues = new ArrayList<String>();
		String listVal = null;
		try {

			WebElement elGoTo = DriverLib.fGetWebElement(eListBox);
			Select comboBox = new Select(elGoTo);
			options = comboBox.getOptions();
			System.out.println("sizeInDriverUtils:" + options.size());
			for (WebElement option : options) {
				listVal = option.getText();
				System.out.println("listVal1:" + listVal);
				listValues.add(listVal);
			}

		} catch (Exception e) {

			CoreLib.LOGGER.error("List elements are not present");
			Assert.fail("List elements are not present");

		}
		return listValues;
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/
	public static List<String> fVerifySelectOptions(String eListBox,
			String value) throws Exception {
		List<String> listValues = new ArrayList<String>();
		try {
			listValues = fGetSelectedOptions(eListBox);

			if (listValues.contains(value))
				CoreLib.LOGGER.info("'" + value
						+ "' Verified in the Drop down '" + eListBox + "'");
			else
				CoreLib.LOGGER.error("'" + value
						+ "' Not found in the Drop down '" + eListBox + "'");
		} catch (Exception e) {

			CoreLib.LOGGER.error("List elements are not present");
			Assert.fail("List elements are not present");
		}
		return listValues;
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fIsSelected(String velLocator) throws Exception {
		try {
			Assert.assertTrue(fGetWebElement(velLocator).isSelected());
			CoreLib.LOGGER.info("Element  '" + velLocator + "' = selected. ");
		} catch (Exception ae) {
			CoreLib.LOGGER.error("Element  '" + velLocator
					+ "' = not selected. " + ae);
			Assert.fail("Element '" + velLocator + "' is not selected." + ae);
		}

	}

	public static void fVerifySelected(String velLocator) throws Exception {
		try {
			Assert.assertTrue(fGetWebElement(velLocator).isSelected());
			CoreLib.LOGGER.info("Element  '" + velLocator + "' = selected. ");
		} catch (Exception ae) {
			CoreLib.LOGGER.error("Element  '" + velLocator
					+ "' = not selected. " + ae);
			Assert.fail("Element '" + velLocator + "' is not selected." + ae);
		}

	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fIsNotSelected(String velLocator) throws Exception {
		try {
			Assert.assertFalse(fGetWebElement(velLocator).isSelected());
			CoreLib.LOGGER.info("Element  '" + velLocator
					+ "' =  not selected. ");
		} catch (Exception ae) {
			CoreLib.LOGGER.error("Element  '" + velLocator + "' =  selected. "
					+ ae);
			Assert.fail("Element '" + velLocator + "' is selected." + ae);
		}

	}

	public static void fVerifyNotSelected(String velLocator) throws Exception {
		try {
			Assert.assertFalse(fGetWebElement(velLocator).isSelected());
			CoreLib.LOGGER.info("Element  '" + velLocator
					+ "' =  not selected. ");
		} catch (Exception ae) {
			CoreLib.LOGGER.error("Element  '" + velLocator + "' =  selected. "
					+ ae);
			Assert.fail("Element '" + velLocator + "' is selected." + ae);
		}

	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fVerifyConfirmation(String tConfirmMessage)
			throws Exception {
		try {
			Alert alert = driver.switchTo().alert();
			String vActualConfirmMsg = alert.getText();
			alert.accept();
			if (tConfirmMessage != vActualConfirmMsg) {
				CoreLib.LOGGER.info("Confirm message  '" + tConfirmMessage
						+ "' = is not presented. ");
				Assert.assertNotEquals(vActualConfirmMsg, tConfirmMessage,
						"Actual confirm message " + vActualConfirmMsg
								+ " is not equals to expected "
								+ tConfirmMessage);
			} else {
				CoreLib.LOGGER.info("Confirm message  '" + tConfirmMessage
						+ "' = is  presented. ");
			}

		} catch (Exception ae) {
			CoreLib.LOGGER.error("Confirm message  '" + tConfirmMessage
					+ "' = is not presented. " + ae);
			Assert.fail("Confirm message '" + tConfirmMessage
					+ "' is not presented." + ae);

		}
	}

	/*******************************************************************************************************************************************************************

	 ********************************************************************************************************************************************************************/

	public static void fSelectRadioButton(String velLocator) throws Exception {

		try {

			WebElement element = DriverLib.fGetWebElement(velLocator);

			if (!element.isSelected())
				element.click();
			Thread.sleep(200);
			// Assert.assertTrue(DriverUtils.fGetWebElement(velLocator).isSelected());

			CoreLib.LOGGER
					.info("Radio Button  '" + velLocator + "'  Selected ");
		} catch (Exception ae) {
			CoreLib.LOGGER
					.error("Error occured while selecting the Radio Button  '"
							+ velLocator + "'  " + ae.getMessage());
			Assert.fail("Error occured while selecting the Radio Button  '"
					+ velLocator + "'  " + ae.getMessage());
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 21/10/2014
	 * @author : EGenji
	 * @throws strText
	 *             : Text to be verified.
	 * @purpose : to Verify the given text is present in the web page or not
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/

	public static void fVerifyTextPresent(String strText) {
		try {
			String strPageSource = driver.getPageSource();
			Boolean flag = strPageSource.contains(strText);
			Assert.assertTrue(flag, "'" + strText
					+ " Text Not Present in the Web Page");
			CoreLib.LOGGER.info(" Veififed the Text Persent in the Web Page '"
					+ strText + "'");
		} catch (Exception e) {
			CoreLib.LOGGER.info("'" + strText
					+ "' Text Present in the Web Page" + e.getMessage());
			Assert.fail("'" + strText + "' Text Not Present in the Web Page"
					+ e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 21/10/2014
	 * @author : EGenji
	 * @throws strText
	 *             : Text to be verified.
	 * @purpose : to Verify the given text is not present in the web page or not
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	public static void fVerifyTextNotPresent(String strText) {
		try {
			String strPageSource = driver.getPageSource();
			Boolean flag = strPageSource.contains(strText);
			Assert.assertFalse(flag, "'" + strText
					+ "' Text Present in the Web Page");
			CoreLib.LOGGER
					.info(" Veififed the Text Not Persent in the Web Page '"
							+ strText + "'");

		} catch (Exception e) {
			CoreLib.LOGGER.error("'" + strText
					+ "' Text Not Present in the Web Page" + e.getMessage());
			Assert.fail("'" + strText + "' Text Present in the Web Page"
					+ e.getMessage());
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 10/11/2014
	 * @author : EGenji
	 * @purpose : Verifies given attribute matches with the expected value
	 * @param vAttribute
	 *            : Attribute name
	 * @param vExpectedVal
	 *            : expected value
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	public static boolean fVerifyAttributeValue(String eLocator,
			String vAttribute, String vExpectedVal) {
		try {
			String vActualVal = DriverLib.fGetWebElement(eLocator)
					.getAttribute(vAttribute);
			if (vActualVal.equals(vExpectedVal)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 24/10/2014
	 * @author : EGenji
	 * @throws strText
	 *             : The title of the Window to be moved.
	 * @purpose : Switches the focus to the window specified
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	public static void fSelectWindow(String windowTitle) {
		try {
			WebDriver popup = null;
			Set<String> windowHandles = driver.getWindowHandles();
			CoreLib.LOGGER.info("No Of Windows ::" + windowHandles.size());

			for (int i = 0; i < windowHandles.size(); i++) {
				popup = driver.switchTo().window(
						windowHandles.toArray()[i].toString());
				CoreLib.LOGGER.info("Ttile ::" + popup.getTitle() + "URL ::"
						+ popup.getCurrentUrl());
				if (popup.getTitle().equals(windowTitle)
						|| popup.getCurrentUrl().equals(windowTitle))
					break;
			}

			CoreLib.LOGGER.info("Window Selected Successfully with Title '"
					+ windowTitle + "'");

		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while swtiching to Window with title as '"
							+ windowTitle + "' " + e.getMessage());
			Assert.fail("Error occured while swtiching to Window with title as '"
					+ windowTitle + "' " + e.getMessage());
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 24/10/2014
	 * @author : EGenji
	 * @throws strText
	 *             : The title/url of the Window to be moved.
	 * @purpose : close the window and Switches the focus to the window
	 *          specified
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 ********************************************************************************************************************************************************************/
	public static void fClosetWindow(String windowTitle) {
		try {
			driver.close();
			fSelectWindow(windowTitle);
			CoreLib.LOGGER
					.info(" Colsed Window and slected the window with Title '"
							+ windowTitle + "'");
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while closing the Window and selectign  the window with title as '"
							+ windowTitle + "' " + e.getMessage());
			Assert.fail("Error occured while closing the Window and selectign  the window with title as '"
					+ windowTitle + "' " + e.getMessage());
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 06/11/2014
	 * @author : EGenji
	 * @throws eTableLocator
	 *             : The locator of the table
	 * @throws sRow
	 *             : The starting row number
	 * @throws colNumber
	 *             : The Column number where the value presented
	 * @throws value
	 *             : The text,if you want to search in the table
	 * @purpose : click on the table row when the particular value matched
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 * 
	 ********************************************************************************************************************************************************************/
	public static void fClickOnTableRow(String eTableLocator, int sRow,
			int colNumber, String value) {

		try {
			int rowcount = driver.findElements(
					By.xpath(eTableLocator + "//tr")).size();
			CoreLib.LOGGER.info("No Of rows in the Table  = " + rowcount);

			if (rowcount != 0) {

				for (int i = sRow; i <= rowcount; i++) {

					String loc = eTableLocator + "//tr[" + i + "]//td ["
							+ colNumber + "]";
					String actual = driver
							.findElement(By.xpath(loc)).getText();

					boolean res = actual.contains(value);

					if (res) {

						CoreLib.LOGGER.info("Expected value found at row " + i);
						loc = eTableLocator + "//tr[" + i + "]//td["
								+ colNumber + "]/*";
						driver.findElement(By.xpath(loc)).click();

						break;
					} else
						CoreLib.LOGGER.info("Column Value ::" + actual);
				}
			} else {
				String temp = " No rows found for table " + eTableLocator;
				CoreLib.LOGGER.info(temp);
				System.out.println(temp);
				return;
			}

		} catch (Exception e) {
			System.out
					.println("Error occured while clicking on the Web Table '"
							+ eTableLocator + " At Col Number '" + colNumber
							+ " and Value '" + value + "" + e.getMessage());
			Assert.fail("Error occured while clicking on the Web Table '"
					+ eTableLocator + " At Col Number '" + colNumber
					+ " and Value '" + value + "" + e.getMessage());
		}

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 06/11/2014
	 * @author : EGenji
	 * @throws eTableLocator
	 *             : The locator of the table
	 * @throws sRow
	 *             : The starting row number
	 * @throws colNumber
	 *             : The Column number where the value presented
	 * @throws knownName
	 *             : To provide employee related text as search criteria for
	 *             searching in the table and clicks if the value matches with
	 *             the list
	 * @throws empName
	 *             : Provide Actual Employee Name and checks if it matches with
	 *             knownName, if matches it clicks the value in empName
	 * @purpose : click on the table row when the particular value matched
	 * @throws : Exception Error
	 * @Date Updated :
	 * @Updated by :
	 * 
	 ********************************************************************************************************************************************************************/
	public static void fClickEmpNameFromTable(String eTableLocator, int sRow,
			int colNumber, String knownName, String empName) {
		String empNameLoc;
		try {
			int rowcount = driver.findElements(
					By.xpath(eTableLocator + "//tr")).size();
			CoreLib.LOGGER.info("No Of rows in the Table  = " + rowcount);

			if (rowcount != 0) {
				for (int i = sRow; i <= rowcount; i++) {

					String loc = eTableLocator + "//tr[" + i + "]//td ["
							+ colNumber + "]/a";
					String actual = driver
							.findElement(By.xpath(loc)).getText();

					if (!actual.contains(knownName)) {
						boolean res = actual.contains(empName);
						if (res) {
							CoreLib.LOGGER.info("Expected value found at row "
									+ i);
							empNameLoc = "//a[text()='" + empName + "']";
							driver.findElement(By.xpath(empNameLoc))
									.click();
							break;
						} else
							CoreLib.LOGGER.info("Column Value ::" + actual);
					} else {
						empNameLoc = "//a[text()='" + knownName + "']";
						driver.findElement(By.xpath(empNameLoc))
								.click();
						break;

					}
				}
			} else {
				String temp = " No rows found for table " + eTableLocator;
				CoreLib.LOGGER.info(temp);
				return;
			}
		} catch (Exception e) {
			System.out
					.println("Error occured while clicking on the Web Table '"
							+ eTableLocator + " At Col Number '" + colNumber
							+ " and Value '" + empName + "" + e.getMessage());
			Assert.fail("Error occured while clicking on the Web Table '"
					+ eTableLocator + " At Col Number '" + colNumber
					+ " and Value '" + empName + "" + e.getMessage());
		}

	}
	
	
	
	public static void fClose()
	{
		try
		{
			driver.close();
		}
		catch(Exception e)
		{
			System.out.println("Error Occured while Closing the Browser ... "+ e.getMessage());
			CoreLib.LOGGER.error("Error Occured while Closing the Browser ... "+ e.getMessage());
		}
	}
	

	/*******************************************************************************************************************************************************************
	 * @Date Created       : 17/09/2014
	 * @author             : EGenji
	 * @param appName      : Name of the application
	 * @param testName     : Name of the Test
	 * @throws             : Exception error
	 * @purpose            : This is function is used to take the screenshot (ie appName_testName_DDMMYY_HHMMSS)
	 * ********************************************************************************************************************************************************************/
	public static String fTakeScreenShot(String testName, String pageName) {

		String strScreenshotName = null;
		try {
			GlobalVars.strScreenshotPath = "";
			
			String path=".//Reports";
			CoreLib.createDir(path);
			
			String strEnvDir = path +"//"+ ConfigDetails.appName;
			CoreLib.createDir(strEnvDir);
			
			String strEnv = strEnvDir +"//"+ ConfigDetails.release;
			CoreLib.createDir(strEnvDir);
			
			path=strEnv+"//ScreenShots";
			CoreLib.createDir(path);
			
		/*	String name = path+"\\" + ConfigDetails.appName;
			CoreLib.createDir(name);*/

			path = path + "\\" + testName;
			CoreLib.createDir(path);

			path = path + "\\" + scrShotDir;
			CoreLib.createDir(path);
			
			strScreenshotName = path + "\\" + testName + "_" + pageName + "_"+ scrShot.format(new Date()) + ".png";
			File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			File sreenshotFile = new File(strScreenshotName);
			FileUtils.copyFile(f, sreenshotFile);

			strScreenshotName = sreenshotFile.getCanonicalPath();
		} 
		catch (Exception e) 
		{
			System.out.println("Error Occured while taking the Screenshot... "+ e.getMessage());
			CoreLib.LOGGER.error("Error Occured while taking the Screenshot... "+ e.getMessage());
		}

		// strScreenshotName = ". Screen Shot : " + strScreenshotName;

		return strScreenshotName;
	}

	public static String fTakeScreenShot(WebDriver driver,String testName, String pageName) {

		String strScreenshotName = null;
		try {
			
			
			  if(ConfigDetails.isScreenshot.equalsIgnoreCase("true"))
			  {
				    GlobalVars.strScreenshotPath = "";
					
					String path=".\\ScreenShots";
					CoreLib.createDir(path);
					
					String name = path+"\\" + ConfigDetails.appName;
					CoreLib.createDir(name);

					name = name + "\\" + testName;
					CoreLib.createDir(name);

					name = name + "\\" + scrShotDir;
					CoreLib.createDir(name);
					
					strScreenshotName = name + "\\" + testName + "_" + pageName + "_"+ scrShot.format(new Date()) + ".png";
					File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

					File sreenshotFile = new File(strScreenshotName);
					FileUtils.copyFile(f, sreenshotFile);

					strScreenshotName = sreenshotFile.getCanonicalPath();
			  }
			
		} 
		catch (Exception e) 
		{
			System.out.println("Error Occured while taking the Screenshot... "+ e.getMessage());
			CoreLib.LOGGER.error("Error Occured while taking the Screenshot... "+ e.getMessage());
		}

		// strScreenshotName = ". Screen Shot : " + strScreenshotName;

		return strScreenshotName;
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created  : 17/09/2014
	 * @author        : EGenji
	 * @param eLoc    : Location of the element to be provided.
	 * @throws        :
	 * @purpose       : This is function is used to get Xpath count of the particular element.
	 ********************************************************************************************************************************************************************/
	public static int fGetXpathCount(String eLoc) {
		int xPathCount = 0;
		try
		{

			xPathCount = driver.findElements(locatorToByObj(eLoc)).size();
			
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			Assert.fail("Error occured while executing the Get XPath Count Script...."+ e.getMessage());
		}

		return xPathCount;

	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 14/11/2014
	 * @author : EGenji
	 * @param objListOfEles
	 *            : List object
	 * @param sValue
	 *            : item in the list need to be returned if it exists
	 * @throws :
	 * @purpose : Returns value in the list if the expected value contains in
	 *          the list of items
	 * ********************************************************************************************************************************************************************/

	public static String getValueFromList(List<String> objListOfEles,
			String sValue) {
		for (String ele : objListOfEles) {
			if (ele.contains(sValue)) {
				return ele;
			}
		}
		return null;
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 12/12/2014
	 * @author : EGenji
	 * @param vTableLoc
	 *            : Table Locator
	 * @param vColNum
	 *            : Column number
	 * @throws Exception
	 * @throws :
	 * @purpose : Returns list of column values specified under given column
	 *          number. eg:to retrieve 2nd column values in a table then pass
	 *          vColNum as 2
	 * ********************************************************************************************************************************************************************/

	public static List<String> fGetTableColumnValues(String vTableLoc,
			int vColNum) throws Exception {
		List<String> colVals = new ArrayList<String>();
		String colVal = null;
		String vRowlLoc = vTableLoc + "//tbody/tr";
		int rowCnt = driver.findElements(By.xpath(vRowlLoc)).size();
		System.out.println("rowCnt:" + rowCnt);
		for (int rowNo = 1; rowNo <= rowCnt; rowNo++) {
			String vLoc = vTableLoc + "//tbody/tr[" + rowNo + "]/td[" + vColNum
					+ "]";
			System.out.println("vLoc:" + vLoc);
			colVal = DriverLib.fGetWebElement(vLoc).getText();
			System.out.println("colVal:" + colVal);
			colVals.add(colVal);
		}
		return colVals;
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 12/12/2014
	 * @author : EGenji
	 * @param <E>
	 * @param vExpectedList
	 *            : Expected list
	 * @throws Exception
	 * @throws :
	 * @purpose : Verifies list is in sorted order or not.
	 * ********************************************************************************************************************************************************************/

	public static List<String> fSortList(List<String> vList) throws Exception {
		Collections.sort(vList);
		return vList;
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created : 15/12/2014
	 * @author : EGenji
	 * @param vTableLoc
	 *            : Table Locator
	 * @param vColNum
	 *            : Column number
	 * @throws Exception
	 * @throws :
	 * @purpose : Returns list of column values specified under given column
	 *          number. eg:to retrieve 2nd column values in a table then pass
	 *          vColNum as 2
	 * ********************************************************************************************************************************************************************/

	public static int fGetTableColNum(String vTableLoc, String vExpectedColName)
			throws Exception {
		// List<String> colVals = new ArrayList<String>();
		String vActualColName = null;
		int colNum = 0;
		String vHeadLoc = vTableLoc + "//thead/tr/th";
		int colNameCnt = driver.findElements(By.xpath(vHeadLoc))
				.size();
		for (colNum = 1; colNum <= colNameCnt; colNum++) {
			String vLoc = vTableLoc + "//thead/tr[1]/th[" + colNum + "]";
			System.out.println("vTableLoc:" + vLoc);
			vActualColName = DriverLib.fGetWebElement(vLoc).getText();
			System.out.println("vActualColName:" + vActualColName);
			if (vActualColName.equals(vExpectedColName)) {
				return colNum;
			}
		}
		return colNum;
	}

	/*******************************************************************************************************************************************************************
	 * @Date Created     : 16/12/2014
	 * @author           : EGenji
	 * @param vTableLoc  : Table Locator
	 * @param vRowNum    : Row number
	 * @param vColNum    : Column number
	 * @throws Exception
	 * @purpose : Returns cell data for the specified cell location with row
	 *          number and column number eg: to get cell data for 2nd row 3rd
	 *          column pass rowNum as '2' ,columnNum as '3'
	 * ********************************************************************************************************************************************************************/

	public static String fGetTableCellData(String vTableLoc, int vRowNum,int vColNum) throws Exception {

		String vExpectedTxt = null;
		String vLoc = vTableLoc + "//tbody/tr[" + vRowNum + "]/td[" + vColNum+ "]";
		vExpectedTxt = DriverLib.fGetWebElement(vLoc).getText();
		return vExpectedTxt;
	}

	

	/*******************************************************************************************************************************************************************
	 * @Date Created : 15/12/2014
	 * @author : EGenji
	 * @param vActualList
	 *            : Actual List
	 * @param vExpectedList
	 *            : Expected List
	 * @throws Exception
	 * @ReturnType : Integer
	 * @purpose : Returns 0 if two lists are equal otherwise return -1
	 * ********************************************************************************************************************************************************************/

	public static int fCompareTwoLists(List<String> vActualList,
			List<String> vExpectedList) throws Exception {
		if (vActualList.size() == vExpectedList.size()) {
			for (int iActCounter = 0; iActCounter < vActualList.size(); iActCounter++) {
				for (int iExpctCounter = 0; iExpctCounter < vExpectedList
						.size(); iExpctCounter++) {
					if (vActualList.get(iActCounter) != vExpectedList
							.get(iExpctCounter)) {
						return -1;
					}
				}
			}
		} else {
			System.out.println("Lists are not equal");
			return -1;
		}
		return 0;
	}

	public static void fJavaScrptExecutorClick(String eLocator) {
		try {
			WebElement element = fGetWebElement(eLocator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			CoreLib.LOGGER.error("Error occured while clicking on the Element"
					+ eLocator + "using Java Script Executor" + e.getMessage());
			Assert.fail("Error occured while clicking on the Element"
					+ eLocator + "using Java Script Executor" + e.getMessage());
		}
	}

	public static final String fGenerateString(int length) {
		StringBuffer output = new StringBuffer();
		String characterSet = "";
		characterSet = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < length; i++) {
			double index = Math.random() * characterSet.length();
			output.append(characterSet.charAt((int) index));

		}

		System.out.println("The random number generated is" + output);
		return output.toString();

	}

	public static final String fNumbers(int length) {
		StringBuffer output = new StringBuffer();
		String characterSet = "";
		characterSet = "1234567890";

		for (int i = 0; i < length; i++) {
			double index = Math.random() * characterSet.length();
			output.append(characterSet.charAt((int) index));

		}

		System.out.println("The random number generated is" + output);
		return output.toString();

	}

	public static void fVerifyComboBoxValues(String eLocator, String listValues) {
		try {
			String[] arr1 = listValues.split("#");
			System.out.println("test Data= " + arr1[0]);
			String[] options = getSelectedOptions(eLocator);
			int optionsSize = options.length;
			System.out.println("Test Data Size: " + arr1.length
					+ " OptionsSize: " + optionsSize);
			if (arr1.length == optionsSize) {
				for (int i = 0; i < optionsSize; i++) {
					// System.out.println("Option = " + options[i]);
					// System.out.println("Test Data = " + arr1[i]);
					try {
						Assert.assertEquals(options[i], arr1[i], "'" + arr1[i]
								+ "' Not Found in the Combo Box '" + eLocator
								+ "'");
					} catch (Throwable e) {
						CoreLib.LOGGER
								.error("Options mismatch with expected result in drop down");
						Assert.fail("Options mismatch with expected result in drop down");
					}
				}
			} else {
				int count = 0;
				for (int j = 0; j < arr1.length; j++) {
					for (int i = 0; i < optionsSize; i++) {
						// System.out.println("Option = " + options[i]);
						// System.out.println("Test Data = " + arr1[j]);
						if (arr1[j].equalsIgnoreCase(options[i])) {
							count++;
							System.out
									.println("'"
											+ arr1[j]
											+ "' Test data found in options index at:: "
											+ count);
							break;
						}
					}
				}
				if (count > 0 && count == arr1.length) {
					CoreLib.LOGGER.info("Values Verified in the Combobox ....'"
							+ eLocator + "'");
				} else {
					CoreLib.LOGGER
							.error("ComboBox values does not Match with expected result . ");
					Assert.fail("ComboBox values does not Match with expected result . ");
				}
			}
		} catch (Exception e) {
			CoreLib.LOGGER
					.error("Error occured while Verifying the Combo Box Values............"
							+ e.getMessage());
			Assert.fail("Error occured while Verifying the Combo Box Values............"
					+ e.getMessage());
		}
	}

	public static String[] getSelectedOptions(String locator) {
		Select select = new Select(fGetWebElement(locator));
		String[] selectOptions = new String[select.getOptions().size()];
		// System.out.println("Options Size ::"+select.getOptions().size());
		List<WebElement> listelements = select.getOptions();
		for (int i = 0; i < selectOptions.length; i++) {
			// System.out.println("Option Value"+i+"  "+listelements.get(i).getText());
			selectOptions[i] = listelements.get(i).getText();
		}
		return selectOptions;
	}
	

}

/*******************************************************************************************************************************************************************
 * End of DriverUtils.
 ********************************************************************************************************************************************************************/

