package org.kocakaya.caisse.utils;

import java.text.DecimalFormat;

public class DoubleUtils {

    private static DecimalFormat df = new DecimalFormat("##.##");

    public static Double stringToDouble(String s) {
	Double result = Double.parseDouble(s.replaceAll(",", "."));

	String newS = df.format(result);

	return Double.parseDouble(newS.replaceAll(",", "."));
    }

    public static String doubleToString(Double d){
	return df.format(d);
    }
}
