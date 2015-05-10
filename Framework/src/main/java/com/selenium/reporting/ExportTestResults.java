package com.selenium.reporting;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.selenium.core.CoreLib;
import com.selenium.global.ConfigDetails;
import com.selenium.global.GlobalVars;



/**
 * This is used to write the data into an Excel sheet
 */


public class ExportTestResults {

	private static SimpleDateFormat strReportFileName = new SimpleDateFormat("MMddyy_HHmmss");
	String scrShotDir = new SimpleDateFormat("MMddyy").format(new Date());
	
	HSSFWorkbook wb = new HSSFWorkbook();

	String fileName = null;
	String htmlFileName = null;
	

	FileOutputStream fileOut = null;
	InputStream inputStream = null;

	Calendar cal = Calendar.getInstance();
	public final String dateTime1 = "EEE MMM dd hh:mm:ss z yyyy";
	Properties props = new Properties();
	public String testResultPath;
	public String testHTMLResultPath;
	int headings = 0;
	static int summaryHeading=0;

	/**
	 * This method is to write the data into Excel Sheet(Test Results Header)
	 * 
	 * @param result
	 */
	public void exportExcelHeader(String strModCode) {
		try {

			if (headings == 0) {

				//String tModuleName=GlobalVars.strModName;
				
				//String timeStamp = strReportFileName.format(new Date());
				

				//fileName = ConfigDetails.release+"_Test Results_"+timeStamp+".xls";
				//htmlFileName = ConfigDetails.release+"_Test Results_"+timeStamp+".html";
				
				fileName = ConfigDetails.appName+"_"+ConfigDetails.release+"_Test Results.xls";
				htmlFileName = ConfigDetails.appName+"_"+ConfigDetails.release+"_Test Results.html";
				
			    String path=".//Reports";
			    CoreLib.createDir(path);
				
				testResultPath = path+"//"+ConfigDetails.appName;
				CoreLib.createDir(testResultPath);
				testResultPath = testResultPath+"//"+ConfigDetails.release;
				CoreLib.createDir(testResultPath);
				testResultPath=testResultPath+"//"+scrShotDir;
				CoreLib.createDir(testResultPath);
					
				
				
				testHTMLResultPath = testResultPath+ "//" + htmlFileName;
				testResultPath = testResultPath + "//"+ fileName;
						
				
				GlobalVars.testHTMLResultPath=testHTMLResultPath;
				GlobalVars.TestResultsPath=testResultPath;
				System.out.println("OUT FILE : " + testResultPath);

				wb = new HSSFWorkbook();
				wb.createSheet("Test Result");
				fileOut = new FileOutputStream(testResultPath);
				System.out.println("Test Result file is created");
				HSSFSheet sheet = wb.getSheetAt(0);
				sheet.setAutobreaks(false);

				HSSFRow row = sheet.createRow((short) 0);
				HSSFFont font = wb.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setFont(font);

				// Setting Headings in Test Results file
				row.createCell(0).setCellStyle(cellStyle);
				row.createCell(1).setCellStyle(cellStyle);
				row.createCell(2).setCellStyle(cellStyle);
				row.createCell(3).setCellStyle(cellStyle);
				row.createCell(4).setCellStyle(cellStyle);
				row.createCell(5).setCellStyle(cellStyle);
				row.createCell(6).setCellStyle(cellStyle);
				row.createCell(7).setCellStyle(cellStyle);

				row.createCell(0).setCellValue("SNo");
				row.createCell(1).setCellValue("Module Name");
				row.createCell(2).setCellValue("Test Case ID");
				row.createCell(3).setCellValue("Test Case Title");
				row.createCell(4).setCellValue("Result(P/F)");
				row.createCell(5).setCellValue("Error Message");
				row.createCell(6).setCellValue("Screenshot Path");
				row.createCell(7).setCellValue("Time Stamp");
				row.createCell(8).setCellValue("Time Taken (in Seconds)");
				
				headings = 1;
			}
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(" Unable to create headers in Excel report");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This is used to write the test case results into an Excel sheet after
	 * completing the each test case execution
	 * 
	 * @throws IOException
	 */
	public void exportExcelRows(List<String> result) {
		try {
			
			
		
			testResultPath=GlobalVars.TestResultsPath;
			
			System.out.println("testResultPath = " + testResultPath);
			
		
			
			inputStream = new FileInputStream(testResultPath);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			fileOut = new FileOutputStream(testResultPath);
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			HSSFSheet sheet = wb.getSheetAt(0);
			sheet.setAutobreaks(false);
			System.out.println("result.size() = " + result.size());
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			System.out.println("No of rows in sheet=" + rows);
			/*for (int ii = 0; ii < result.size(); ii++)
				System.out.println("Result = " + result.get(ii));
*/
			HSSFRow row = sheet.createRow(rows);
			
			//result.remove(5);
			
			for (int i = 0; i <=8; i++) {
				sheet.setColumnWidth(0, (short) (256 * 5));
				sheet.setColumnWidth(1, (short) (256 * 25));
				sheet.setColumnWidth(2, (short) (256 * 30));
				sheet.setColumnWidth(3, (short) (256 * 30));
				sheet.setColumnWidth(4, (short) (256 * 28));
				sheet.setColumnWidth(5, (short) (256 * 28));
				sheet.setColumnWidth(6, (short) (256 * 28));
				
				
					HSSFCell cell = row.createCell(i);
					System.out.println("..in export.." + result.get(i).toString());
					HSSFRichTextString str = new HSSFRichTextString(result.get(i).toString());
					cell.setCellValue(str);
				
				
				
			}
		
				
			wb.write(fileOut);
			fileOut.close();
		} catch(Exception e){
			System.out.println("Exception occured while experting results list to excel ");
			System.out.println(e.getMessage());
		} 
	}

	/**
	 * This is used to write the Test Summary into an Test Results Excel Sheet
	 * after completion of total test cases execution.
	 */
/*
	public void exportTestSummary(int total,int passed,int failed) {
		try {
			
			testResultPath=GlobalVars.TestResultsPath;
			inputStream = new FileInputStream(testResultPath);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			fileOut = new FileOutputStream(testResultPath);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			sheet.setAutobreaks(false);
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			for (int ii = 0; ii <=5; ii++) {

				HSSFRow row = sheet.createRow(rows + ii + 1);
				HSSFCell cell;
				HSSFRichTextString str;

				switch (ii) {
				case 0:
					cell = row.createCell(1);
					cell.setCellValue("Functional Area");
					cell = row.createCell(2);
					cell.setCellValue("Total");
					cell = row.createCell(3);
					cell.setCellValue("Passed");
					cell = row.createCell(4);
					cell.setCellValue("Failed");
					
					break;
				case 1:
					cell = row.createCell(1);
					cell.setCellValue("Rate Settings");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString(total));
					cell.setCellValue(str);
					cell = row.createCell(3);
					str = new HSSFRichTextString(Integer.toString(passed));
					cell.setCellValue(str);
					cell = row.createCell(4);
					str = new HSSFRichTextString(Integer.toString(failed));
					cell.setCellValue(str);
					break;
				case 2:
					cell = row.createCell(1);
					cell.setCellValue("Security");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString(total));
					cell.setCellValue(str);
					cell = row.createCell(3);
					str = new HSSFRichTextString(Integer.toString(passed));
					cell.setCellValue(str);
					cell = row.createCell(4);
					str = new HSSFRichTextString(Integer.toString(failed));
					cell.setCellValue(str);
					break;
				case 3:
					cell = row.createCell(1);
					cell.setCellValue("Member");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString(total));
					cell.setCellValue(str);
					cell = row.createCell(3);
					str = new HSSFRichTextString(Integer.toString(passed));
					cell.setCellValue(str);
					cell = row.createCell(4);
					str = new HSSFRichTextString(Integer.toString(failed));
					cell.setCellValue(str);
					break;
				case 4:
					cell = row.createCell(1);
					cell.setCellValue("Web Protal");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString(total));
					cell.setCellValue(str);
					cell = row.createCell(3);
					str = new HSSFRichTextString(Integer.toString(passed));
					cell.setCellValue(str);
					cell = row.createCell(4);
					str = new HSSFRichTextString(Integer.toString(failed));
					cell.setCellValue(str);
					break;
					
				case 5:
					cell = row.createCell(1);
					cell.setCellValue("Total");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString(total));
					cell.setCellValue(str);
					cell = row.createCell(3);
					str = new HSSFRichTextString(Integer.toString(passed));
					cell.setCellValue(str);
					cell = row.createCell(4);
					str = new HSSFRichTextString(Integer.toString(failed));
					cell.setCellValue(str);
					break;
				}
			}

			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println("Exception while exporting the testresults Summary");
			System.out.println(e.getMessage());
		} finally {
			try {
				fileOut.close();
			} catch (Exception e) {
				System.out.println("Exception while exporting the testresults Summary");
				System.out.println(e.getMessage());
			}
		}
	}

	*/
	
	public void exportTestSummary(int total,int passed,int failed) {
		try {
			
			testResultPath=GlobalVars.TestResultsPath;
			inputStream = new FileInputStream(testResultPath);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			fileOut = new FileOutputStream(testResultPath);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			sheet.setAutobreaks(false);
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			for (int ii = 0; ii <=4; ii++) {

				HSSFRow row = sheet.createRow(rows + ii + 1);
				HSSFCell cell;
				HSSFRichTextString str;

				switch (ii) {
				case 0:
					cell = row.createCell(1);
					cell.setCellValue("Browser Tested");
					cell = row.createCell(2);
					str = new HSSFRichTextString(GlobalVars.browser);
					cell.setCellValue(str);
					break;
				case 1:
					cell = row.createCell(1);
					cell.setCellValue("Total Cases Executed");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString(total));
					cell.setCellValue(str);
					break;
				case 2:
					cell = row.createCell(1);
					cell.setCellValue("Total Cases Passed");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString(passed));
					cell.setCellValue(str);
					break;
				case 3:
					cell = row.createCell(1);
					cell.setCellValue("Total Cases Failed");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString(failed));
					cell.setCellValue(str);
					break;
				case 4:
					cell = row.createCell(1);
					cell.setCellValue("Total Cases Skipped");
					cell = row.createCell(2);
					str = new HSSFRichTextString(Integer.toString((total-(passed+failed))));
					cell.setCellValue(str);
					break;
				}
			}

			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println("Exception while exporting the testresults Summary");
			System.out.println(e.getMessage());
		} finally {
			try {
				fileOut.close();
			} catch (Exception e) {
				System.out.println("Exception while exporting the testresults Summary");
				System.out.println(e.getMessage());
			}
		}
	}

	
	public void  executionSummaryReport(String modleName,int total ,int passed,int failed)
	{
		try
		{
		
			testResultPath=GlobalVars.TestResultsPath;
			System.out.println("Excel  ..."+testResultPath);
			
			if(summaryHeading==0)
			{
			
				
				InputStream inputStream1 = new FileInputStream(testResultPath);
				POIFSFileSystem fs = new POIFSFileSystem(inputStream1);
				FileOutputStream fileOut1 = new FileOutputStream(testResultPath);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				wb.createSheet("Test Summary");
				HSSFSheet sheet = wb.getSheetAt(1);
				System.out.println("Sheet Name ::"+sheet.getSheetName());
				
				
				HSSFRow row = sheet.createRow((short) 0);
				HSSFFont font = wb.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setFont(font);

				// Setting Headings in Test Results file
				row.createCell(0).setCellStyle(cellStyle);
				row.createCell(1).setCellStyle(cellStyle);
				row.createCell(2).setCellStyle(cellStyle);
				row.createCell(3).setCellStyle(cellStyle);
				row.createCell(4).setCellStyle(cellStyle);
				
				
				row.createCell(0).setCellValue("Module Name");
				row.createCell(1).setCellValue("Total ");
				row.createCell(2).setCellValue("Passed ");
				row.createCell(3).setCellValue("Failed ");
				row.createCell(4).setCellValue("skiped");
				
				
				
				
				wb.write(fileOut1);
				fileOut1.close();
				
				
				summaryHeading++;
			}
	
			

			InputStream inputStream2 = new FileInputStream(testResultPath);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream2);
			FileOutputStream fileOut2 = new FileOutputStream(testResultPath);
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			HSSFSheet sheet = wb.getSheetAt(1);
			sheet.setAutobreaks(false);
		
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			System.out.println("No of rows in sheet=" + rows);
			/*for (int ii = 0; ii < result.size(); ii++)
				System.out.println("Result = " + result.get(ii));
*/
			HSSFRow row = sheet.createRow(rows);
			
			
			for (int i = 0; i <=4; i++) {
				sheet.setColumnWidth(0, (short) (256 * 30));
				sheet.setColumnWidth(1, (short) (256 * 25));
				sheet.setColumnWidth(2, (short) (256 * 30));
				sheet.setColumnWidth(3, (short) (256 * 30));
				sheet.setColumnWidth(4, (short) (256 * 28));
				
				row.createCell(0).setCellValue(modleName);
				row.createCell(1).setCellValue(total);
				row.createCell(2).setCellValue(passed);
				row.createCell(3).setCellValue(failed);
				row.createCell(4).setCellValue((total -(passed+failed)));
				
				
				
				
				
			}
			
			wb.write(fileOut2);
			fileOut2.close();
			
			/*
			System.out.println("Module Name ::"+GlobalVars.strModName);
			System.out.println("Total ::"+total);
			System.out.println(" Passed ::"+passed);
			System.out.println(" Failed ::"+failed);*/
			
			
		}
		catch(Exception e)
		{
			System.out.println("Error occuredd while updating the execution summary report  into excel sheet...."+e.getMessage());
		}
	}
}
