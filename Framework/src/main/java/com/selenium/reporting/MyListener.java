package com.selenium.reporting;


import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.selenium.global.ConfigDetails;

public class MyListener implements ITestListener, ISuiteListener {
	 
	ReportLib report=new ReportLib();
	@Override
 
	public void onStart(ISuite arg0) {

		ConfigDetails.fReadConfigDetails();
		ExportTestResults export = new ExportTestResults();
		export.exportExcelHeader(ConfigDetails.appName);
		
		
	}
 
	// This belongs to ISuiteListener and will execute, once the Suite is finished
 
	@Override
 
	public void onFinish(ISuite arg0) {
		
		report.fGenerateHTMLReport();
	}
 
	// This belongs to ITestListener and will execute only when the test is pass
 
	public void onTestSuccess(ITestResult arg0) {
		
		report.fGenerateExcelReport(arg0);
 
	}
 
	// This belongs to ITestListener and will execute only on the event of fail test
 
	public void onTestFailure(ITestResult arg0) {

		report.fGenerateExcelReport(arg0);

	}
 
	// This belongs to ITestListener and will execute before the main test start (@Test)
 
	public void onTestStart(ITestResult arg0) {
 
		//System.out.println("The execution of the main test starts now");
 
	}
 
	// This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped
 
	public void onTestSkipped(ITestResult arg0) {
		
		report.fGenerateExcelReport(arg0);
 
		}
 
	// This is just a piece of shit, ignore this
 
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
 
	}
 
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
 
}