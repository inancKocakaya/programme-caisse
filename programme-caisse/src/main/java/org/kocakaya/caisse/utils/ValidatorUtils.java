package org.kocakaya.caisse.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class ValidatorUtils {
    
    private ValidatorUtils(){
    }
    
    public static boolean validateIsNumericWithFrenchFormat(String s){
	return NumberUtils.isNumber(s.replace(",", "."));
    }
}