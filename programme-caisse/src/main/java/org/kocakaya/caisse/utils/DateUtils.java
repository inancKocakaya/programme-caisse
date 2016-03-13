package org.kocakaya.caisse.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static Calendar NOW = Calendar.getInstance();

    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00");

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static int yearOfCurrentDate() {
	return NOW.get(Calendar.YEAR);
    }

    public static String monthOfCurrentDateWithStandardStartIndex() {
	int indexMonth = NOW.get(Calendar.MONTH);
	String month = DECIMAL_FORMAT.format(indexMonth);
	return month;
    }

    public static String monthOfCurrentDateWithIncrementedStartedIndex() {
	int month = Integer.parseInt(monthOfCurrentDateWithStandardStartIndex()) + 1;
	String result = DECIMAL_FORMAT.format(month);
	return result;
    }

    public static int dayOfCurrentDate() {
	return NOW.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatDateWithFrenchFormat(Date date) {
	return DATE_FORMAT.format(date);
    }

    public static Date stringToDate(String str) {
	try {
	    return DATE_FORMAT.parse(str);
	} catch (ParseException e) {
	    return null;
	}
    }

    public static Date calendarToDate(Calendar cal) {
	return cal.getTime();
    }

    public static Calendar dateToCalendar(Date date) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	return cal;
    }

    public static int year(Date date) {
	return dateToCalendar(date).get(Calendar.YEAR);
    }

    public static int month(Date date) {
	return dateToCalendar(date).get(Calendar.MONTH);
    }

    public static int day(Date date) {
	return dateToCalendar(date).get(Calendar.DAY_OF_MONTH);
    }
}