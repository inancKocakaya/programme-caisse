package org.kocakaya.caisse.utils;

import java.util.HashMap;
import java.util.Map;

public class MonthUtils {

    private static final Map<Integer, String> FR_MONTH_NAME_BY_INDEX = new HashMap<>();

    static {
	FR_MONTH_NAME_BY_INDEX.put(1, "Janvier");
	FR_MONTH_NAME_BY_INDEX.put(2, "Février");
	FR_MONTH_NAME_BY_INDEX.put(3, "Mars");
	FR_MONTH_NAME_BY_INDEX.put(4, "Avril");
	FR_MONTH_NAME_BY_INDEX.put(5, "Mai");
	FR_MONTH_NAME_BY_INDEX.put(6, "Juin");
	FR_MONTH_NAME_BY_INDEX.put(7, "Juillet");
	FR_MONTH_NAME_BY_INDEX.put(8, "Août");
	FR_MONTH_NAME_BY_INDEX.put(9, "Septembre");
	FR_MONTH_NAME_BY_INDEX.put(10, "Octobre");
	FR_MONTH_NAME_BY_INDEX.put(11, "Novembre");
	FR_MONTH_NAME_BY_INDEX.put(12, "Décembre");
    }

    private MonthUtils(){
    }
    
    public static String getFrenchMonthNameByIndex(int monthIndex) {
	return FR_MONTH_NAME_BY_INDEX.get(monthIndex);
    }
}
