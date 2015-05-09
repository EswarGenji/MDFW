package com.selenium.reporting;

public class TimeConversion {
	
	
	
	public static String convertSecondsToHours(Long time)
	{
	  String formatTime=null;
	  try
	  {
		  int hrs=(int)(time/3600);
		  int mins=(int)(time%3600)/60;
		  int secs=(int)((time%3600)%60);
		  formatTime=hrs+" Hr "+mins+" Mins "+secs+" Secs";
		 
	  }
	  catch(Exception e)
	  {
		  System.out.println("Error occured while converting the Time from Seconds to Hours..."+e.getMessage());
	  }
	  return formatTime;
	  
	}
	/*public static void main(String[] args) {
		Long totalTime=25l;
		String formatTime=TimeConversion.convertSecondsToHours(totalTime);
		 System.out.println("Time "+formatTime);
	}
	*/
	
	
	

}
