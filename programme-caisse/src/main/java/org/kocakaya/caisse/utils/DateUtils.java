package org.kocakaya.caisse.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static Calendar now = Calendar.getInstance();
    
    private static DecimalFormat mFormat = new DecimalFormat("00");

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static int yearOfCurrentDate() {
	return now.get(Calendar.YEAR);
    }

    public static String monthOfCurrentDateWithStandardStartIndex() {
	int indexMonth = now.get(Calendar.MONTH);
	String month = mFormat.format(indexMonth);
	return month;
    }
    
    public static String monthOfCurrentDateWithIncrementedStartedIndex(){
	int month = Integer.parseInt(monthOfCurrentDateWithStandardStartIndex()) + 1;
	String result = mFormat.format(month);
	return result;
    }

    public static int dayOfCurrentDate() {
	return now.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatDateWithFrenchFormat(Date date) {
	return dateFormat.format(date);
    }

    public static Date stringToDate(String str) {
	try {
	    return dateFormat.parse(str);
	} catch (ParseException e) {
	    return null;
	}
    }
    
    public static Date calendarToDate(Calendar cal){
	return cal.getTime();
    }
    
    public static Calendar dateToCalendar(Date date){
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	return cal;
    }   
}