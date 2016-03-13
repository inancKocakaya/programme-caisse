package org.kocakaya.caisse.utils;

import java.text.DecimalFormat;

public class DoubleUtils {

    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.##");

    public static Double stringToDouble(String s) {
	Double result = Double.parseDouble(s.replaceAll(",", "."));

	String newS = DECIMAL_FORMAT.format(result);

	return Double.parseDouble(newS.replaceAll(",", "."));
    }

    public static String doubleToString(Double d){
	return DECIMAL_FORMAT.format(d);
    }
}