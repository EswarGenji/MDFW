package com.gmail.common;

import com.selenium.core.CoreLib;
import com.selenium.global.GlobalVars;

public class GMailControls {
	

	
	public static class LoginPage
	{
		public static String filePath=GlobalVars.locOfPages+"//GMail//LoginPage.xml";
		public static String blockName="Login";
		
		public static final String EUserName=CoreLib.fGetNodeText(filePath, blockName, "EUserName");
		public static final String EPassword=CoreLib.fGetNodeText(filePath, blockName, "EPassword");
		public static final String ESignIn=CoreLib.fGetNodeText(filePath, blockName, "ESignIn");
		public static final String ENext=CoreLib.fGetNodeText(filePath, blockName, "ENext");
		
		
		
	}
	
	public static class LogoutPage
	{
		public static String filePath=GlobalVars.locOfPages+"//GMail//LogoutPage.xml";;
		public static String blockName="Logout";
		
		public static final String EAccountImage=CoreLib.fGetNodeText(filePath, blockName, "EAccountImage");
		public static final String ESignOut=CoreLib.fGetNodeText(filePath, blockName, "ESignOut");
		
		
		
		
	}
}
