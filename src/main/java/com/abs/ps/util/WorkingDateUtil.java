package com.abs.ps.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public final class WorkingDateUtil {
	private WorkingDateUtil() {}
	
	public static boolean isWorkingDay(Date date) {		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		if (calendar.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY
				&& calendar.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY) {
			return true;
		}
		return false;
	}
	
	public static boolean isHoliday(Date date, List<String> holidayDate) {
		String dateStr = DateHelper.convert2String(date, DateHelper.DATE_FORMATE);
		if (holidayDate.contains(dateStr)) {
			return true;
		}
		return false;
	}
	
	//holiday transfer to working date
	
	
	
}
