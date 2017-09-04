package com.abs.ps.util;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringHelper {
	private StringHelper(){}
	
	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return false;
		}
		return true;
	}
	
	public static String filterNullStr(String str) {
		if (isEmpty(str)) {
			return "";
		}
		return str;
	}
	
	public static String getNewString(String str) throws UnsupportedEncodingException {
		if (isEmpty(str)) return "";
        return new String(str.getBytes("ISO-8859-1"),"UTF-8");
    }
	
	public static String join(String join,String[] strAry){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.length;i++){
             if(i==(strAry.length-1)){
                 sb.append(strAry[i]);
             }else{
                 sb.append(strAry[i]).append(join);
             }
        }
        
        return new String(sb);
    }
	
	public static String join(String join,List strAry){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.size();i++){
             if(i==(strAry.size()-1)){
                 sb.append(strAry.get(i));
             }else{
                 sb.append(strAry.get(i)).append(join);
             }
        }
        
        return new String(sb);
    }
	
	public static int convert(String str) {
		if (!StringHelper.isEmpty(str)) {
			return Integer.parseInt(str);
		}
		return 0;
	}
	
	
	public static String formatNumber(int num) {
       NumberFormat formatter = NumberFormat.getNumberInstance();  
       formatter.setMinimumIntegerDigits(3);  
       formatter.setGroupingUsed(false);  
       return formatter.format(num);
	}
	
	
	public static boolean sqlValidate(String str) {  
		if (!StringHelper.isEmpty(str)) {
	        str = str.toLowerCase();
	        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +  
	                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +  
	                "table|from|grant|use|group_concat|column_name|" +  
	                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +  
	                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";
	        String[] badStrs = badStr.split("\\|");  
	        for (int i = 0; i < badStrs.length; i++) {  
	            if (str.indexOf(badStrs[i]) >= 0) {  
	                return true;  
	            }  
	        }  
		}
        return false;  
    } 
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static String randomGenerate() {
		String val = "";
		int length = 6;
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			//
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 
				val += String.valueOf(random.nextInt(10));
			}
		}
		
		return DateHelper.convert2String(new Date(), "yyyyMMddHHmmss") + val;
	}
	
	public static void main(String[] argu) {
		System.out.println(StringHelper.randomGenerate());
	}

}
