package com.mb.jpush.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断字符串是否非空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNullOrEmpty(String s) {
		if (null == s || "".equals(s) || "null".equals(s)) {
			return true;
		}
		return false;
	}

	
	public static boolean isNumber(String str){
		boolean flag = false;
		//判断正负数都可以
		//Pattern pattern = Pattern.compile("^[-]{0,1}[0-9]+$");
		Pattern pattern = Pattern.compile("^[1-9][0-9]*$");
		Matcher isNum = pattern.matcher(str);
		if(isNum.matches()){
			flag = true;
		}
		return flag;
	}
	
	public static String convertStreamToString(InputStream is) {      
        /*  
          * To convert the InputStream to String we use the BufferedReader.readLine()  
          * method. We iterate until the BufferedReader return null which means  
          * there's no more data to read. Each line will appended to a StringBuilder  
          * and returned as String.  
          */     
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
         StringBuilder sb = new StringBuilder();      
         String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {      
                 sb.append(line + "\n");      
             }      
         } catch (IOException e) {      
             e.printStackTrace();      
         } finally {      
            try {      
                 is.close();      
             } catch (IOException e) {      
                 e.printStackTrace();      
             }      
         }      
        return sb.toString();      
     }
}
