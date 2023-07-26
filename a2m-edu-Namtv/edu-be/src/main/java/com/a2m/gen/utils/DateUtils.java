package com.a2m.gen.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat monthFormatter = new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy.MM.dd");
	public static SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat formatter4 = new SimpleDateFormat("yy.MM.dd");
	public static SimpleDateFormat formatter5 = new SimpleDateFormat("yyyyMMddHHmm");

	public static String convertDateToStringDB(Date date) {
		if(date != null) {
			return formatter.format(date);
		}
		return null;
	}

	public static String convertMonthToStringDB(Date date) {
		if(date != null) {
			return monthFormatter.format(date);
		}
		return null;
	}

	public static Date parse(String dateStr, String pattern) {
		if(dateStr != null) {
			return null;
		}

		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public static String convertDateToStringDB3(Date date) {
		if(date != null) {
			return formatter3.format(date);
		}
		return null;
	}
	
	public static LocalDate convertStringToLocalDate(String dateStr) {
		return LocalDate.parse(dateStr);
	}
	
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public static Date convertToDateViaInstant(LocalDate dateToConvert) {
	    return java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}
}	
