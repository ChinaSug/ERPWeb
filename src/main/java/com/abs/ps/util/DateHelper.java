package com.abs.ps.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public final class DateHelper {
	public final static String DATE_FORMATE = "yyyy-MM-dd";
	public final static String DATETIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
	private DateHelper() {}

	public static Date convert2Date(String date) {
		try {
			if (!StringHelper.isEmpty(date)) {
				 SimpleDateFormat formatter = new SimpleDateFormat(DateHelper.DATETIME_FORMATE);
				 return formatter.parse(date);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static Date convert2Date(String date, String pattern) {
		try {
			if (!StringHelper.isEmpty(date)) {
				 SimpleDateFormat formatter = new SimpleDateFormat(pattern);
				 return formatter.parse(date);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static String convert2String(Date date, String pattern) {
		if (StringHelper.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		try {
			if (date != null) {
				 SimpleDateFormat formatter = new SimpleDateFormat(pattern);
				 return formatter.format(date);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static Date caculate(Date date, int days) {
		 Calendar calendar = new GregorianCalendar(); 
	     calendar.setTime(date);
	     calendar.add(calendar.DATE, days);
	     return calendar.getTime();
	}
	
	public static Date addYears(Date date, int years) {
		if (date != null) {
			 Calendar calendar = new GregorianCalendar(); 
		     calendar.setTime(date);
		     calendar.add(calendar.YEAR, years);
		     return calendar.getTime();
		}
		return null;
	}
	
	public static Date getDate(int days) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		if (days != 0) {
			gc.add(GregorianCalendar.DAY_OF_MONTH, days);
		}
		return gc.getTime();
	}
	
	public static Date getDate(Date date, int days) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		if (days != 0) {
			gc.add(GregorianCalendar.DAY_OF_MONTH, days);
		}
		return gc.getTime();
	}
	
	public static Date createDateTime(Date date, int hour, int mins, int sec) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(GregorianCalendar.HOUR_OF_DAY, hour);
		gc.set(GregorianCalendar.MINUTE, mins);
		gc.set(GregorianCalendar.SECOND, sec);
		return gc.getTime();
	}
	
	/*
	 * if sourceDate > targetDate then return -1
	 * if sourceDate = targetDate then return 0
	 * if sourceDate < targetDate then return 1
	 */
	public static int compareDate(Date sourceDate, Date targetDate, String pattern) {
		int result = 0;
		if (sourceDate != null && targetDate != null) {
			String sourceDateStr = DateHelper.convert2String(sourceDate, DateHelper.DATE_FORMATE);
			String targetDateStr = DateHelper.convert2String(targetDate, DateHelper.DATE_FORMATE);
			sourceDate = DateHelper.convert2Date(sourceDateStr, DateHelper.DATE_FORMATE);
			targetDate = DateHelper.convert2Date(targetDateStr, DateHelper.DATE_FORMATE);
			result = sourceDate.compareTo(targetDate);			
		}
		return result;
	}
	
	public static int remainDate(Date sourceDate, Date targetDate, String pattern) {
		int result = 0;
		if (sourceDate != null && targetDate != null) {
			String sourceDateStr = DateHelper.convert2String(sourceDate, DateHelper.DATE_FORMATE);
			String targetDateStr = DateHelper.convert2String(targetDate, DateHelper.DATE_FORMATE);
			sourceDate = DateHelper.convert2Date(sourceDateStr, DateHelper.DATE_FORMATE);
			targetDate = DateHelper.convert2Date(targetDateStr, DateHelper.DATE_FORMATE);
			
			long intervalMilli = targetDate.getTime() - sourceDate.getTime();
			result = (int) (intervalMilli / (24 * 60 * 60 * 1000));
		}
		return result;
	}
	
	public static int remainHour(Date sourceDate, Date targetDate) {
		long intervalMilli = targetDate.getTime() - sourceDate.getTime();
		if (intervalMilli < 0) return 0;
		else return (int) (intervalMilli / (60 * 60 * 1000));
	}
	
	public static int remainMins(Date sourceDate, Date targetDate) {
		long intervalMilli = targetDate.getTime() - sourceDate.getTime();
		if (intervalMilli < 0) return 0;
		else return (int) (intervalMilli / (60 * 1000));
	}
	
	public static List<String> retrieveAppointDate() {
// centerDao.getOrganizationById(orgOid);
		List<String> appointDateList = new ArrayList<String>();

		boolean isWorkingDate = true;// org.getIsWorkingDay();
		int maxAppointDay = 5;//org.getMaxAppointDay();
		Date now = new Date();
		int count = 1;
		while (true) {
			if (maxAppointDay <= appointDateList.size()) {
				break;
			}
			Date appointDate = DateHelper.caculate(now, count);
			if (isWorkingDate && WorkingDateUtil.isWorkingDay(appointDate)) {
				appointDateList.add(DateHelper.convert2String(appointDate, DateHelper.DATE_FORMATE));
			}
			if (!isWorkingDate) {
				appointDateList.add(DateHelper.convert2String(appointDate, DateHelper.DATE_FORMATE));
			}
			count++;
		
		}
		return appointDateList;
	}
	
	
	public static String convert2DefinedFormat(Date date) {
		Calendar current = Calendar.getInstance();
		
		Calendar today = Calendar.getInstance();	
		
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar yesterday = Calendar.getInstance();
		
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		
		current.setTime(date);
		
		String time = convert2String(date, "yyyy-MM-dd HH:mm");
		
		if(current.after(today)){
			return "今天 "+time.split(" ")[1];
		}else if(current.before(today) && current.after(yesterday)){
			
			return "昨天 "+time.split(" ")[1];
		}else{
			return time;
		}
		
	}
	
	
	
	
	
	public static void main(String[] argu) {
//		List<String> list = DateHelper.retrieveAppointDate();
//		for (String str : list) {
//			System.out.println("str = " + str);
//		}
	
		String time = convert2DefinedFormat(new Date());
		System.out.println("time == " + time);
	}
	
	
	
	
//	public static void main(String[] argu) {
//		Date date = DateHelper.convert2Date("2016-08-31", DateHelper.DATE_FORMATE);
//		Date now = new Date();
//		System.out.println("date = " + DateHelper.remainDate(now, date, DateHelper.DATE_FORMATE));
//	}
}
