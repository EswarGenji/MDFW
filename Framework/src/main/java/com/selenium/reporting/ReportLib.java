package com.selenium.reporting;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.testng.ITestResult;

import com.selenium.core.CoreLib;
import com.selenium.global.ConfigDetails;
import com.selenium.global.GlobalVars;
import com.selenium.webdriver.DriverLib;



public class ReportLib {
	
	static int total=0;
	static int failed=0;
	static int passed=0;
	static int sikped=0;
	
	static int mTotal=0;
	static int mFailed=0;
	static int mPassed=0;
	static int mSkiped=0;
   
	ExportTestResults export=new ExportTestResults();
	HashMap<String,String> tcDetails=new HashMap<String,String>();
	
	

	
public void fGenerateExcelReport(ITestResult result)
{
	
	//System.out.println(" fGenerateExcelReport ");
	
	try
	{
		
		String tcId="";
		String tcName="";
		CoreLib.LOGGER.info(result);
		CoreLib.LOGGER.info("Test Parameters Size  ::"+result.getParameters().length);
		
		
		if(result.getParameters().length==2)
		{
			 tcId=result.getParameters()[0].toString();
			 tcName=result.getParameters()[1].toString();
		}
		else
		{
			String methodName=result.getName();
			System.out.println("Method Name ::"+methodName);
			
			if(methodName.contains("_"))
			{
				tcId=methodName.split("_")[1];
				tcName=methodName.split("_")[2];
			}
			
		}
		//System.out.println("Total Before Increment ::"+total);
	
		total++;
		mTotal++;
		
		//System.out.println("Total After Increment ::"+total);
		List<String> tcExeDetails=new ArrayList<String>();
		 
		tcExeDetails.add(Integer.toString((total)));
		
		tcExeDetails.add(GlobalVars.strModName);
		tcExeDetails.add(tcId);
		tcExeDetails.add(tcName);
		
		String status=null;
		String screenshotPath=null;
		String errorMessage=null;
		
		switch (result.getStatus()) {
		 
		case ITestResult.SUCCESS:
 
				passed++;
				mPassed++;
				status = "Pass";
				errorMessage="";
				screenshotPath=DriverLib.fTakeScreenShot(GlobalVars.testName,"Passed");
	 
			break;
 
		case ITestResult.FAILURE:
 
				failed++;
				mFailed++;
				status = "Fail";
				errorMessage=CoreLib.fToString(result.getThrowable());
				
				if(errorMessage.length()>200)
					errorMessage=errorMessage.substring(0,200);
				//calling take a screenshot code method
				screenshotPath= DriverLib.fTakeScreenShot(GlobalVars.testName,"Failed");
			break;
 
		case ITestResult.SKIP:
 
			sikped++;
			mSkiped++;
			status = "Skip";
			errorMessage="";
			screenshotPath="";
			break;
 
		}
		
		
		tcExeDetails.add(status);
		tcExeDetails.add(errorMessage);
		tcExeDetails.add(screenshotPath);
		
		
		 CoreLib.LOGGER.info("######################################");
		 CoreLib.LOGGER.info(tcName+" Status :: "+status);
		 CoreLib.LOGGER.info("######################################");
		
		
		String timeTaken=Long.toString((result.getEndMillis()-result.getStartMillis())/1000);
		
		
		GlobalVars.totalTimeTaken=GlobalVars.totalTimeTaken+Integer.parseInt(timeTaken);
		
		
		System.out.println("Total Time  ::"+GlobalVars.totalTimeTaken);
	
		tcExeDetails.add((new Date()).toString());
		tcExeDetails.add(timeTaken);
		
		//System.out.println("Key Value :: Row"+total);
		
	     GlobalVars.testCasesResutls.put("Row"+total, tcExeDetails);
		
		export.exportExcelRows(tcExeDetails);
		
		//System.out.println("testCasesResutls  ::"+GlobalVars.testCasesResutls.size());
		
		
	}
	catch(Exception e)
	{
		System.out.println("Error occured while generating Excel Report the Module "+GlobalVars.strModName+ " "+e.getMessage());
	}
}
public void fGenerateHTMLReport()
    {
    	try
    	{
    		
    		System.out.println("testCasesResutls in HTML Report ::"+GlobalVars.testCasesResutls.size());
    		
    		TemplateGenerator template=new TemplateGenerator(GlobalVars.testCasesResutls);
   		
    		GlobalVars.testCasesResutls=new HashMap<String,List<String>>();
    		
    		//System.out.println("Total Test Cases ::"+testCasesResutls.size());
    		
    		export.exportTestSummary(total, passed, failed);
    		export.executionSummaryReport("Total",total, passed, failed);
    		
    		template.buildTemplate(total, passed, failed);
    		
    		MailReport mail=new MailReport();
    		if(ConfigDetails.isEmail.equalsIgnoreCase("true"))
    			mail.sendMailReport(total, passed, failed);
    			
    		
    	
        	
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error occured while generating the HTML report   for the Module "+GlobalVars.strModName+"  "+e.getMessage());
    	}
    }
	
public void fExecutionSummary()
{
	export.executionSummaryReport(GlobalVars.strModName,mTotal, mPassed, mFailed);
	
	mTotal=0;
	mPassed=0;
	mFailed=0;
	mSkiped=0;
}
}
