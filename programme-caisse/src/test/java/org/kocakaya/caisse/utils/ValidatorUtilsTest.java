package org.kocakaya.caisse.utils;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class ValidatorUtilsTest {
    
    
    @Test
    public void testIsDouble(){
	Locale.setDefault(Locale.FRANCE);
	
	String s1 = "10";
	String s2 = "10,4";
	String s3 = "10,40";
	String s4 = "-25,15";
	String s5 = "";
	String s6 = "25a";
	
	assertEquals(true, ValidatorUtils.validateIsNumericWithFrenchFormat(s1));
	assertEquals(true, ValidatorUtils.validateIsNumericWithFrenchFormat(s2));
	assertEquals(true, ValidatorUtils.validateIsNumericWithFrenchFormat(s3));
	assertEquals(true, ValidatorUtils.validateIsNumericWithFrenchFormat(s4));
	assertEquals(false, ValidatorUtils.validateIsNumericWithFrenchFormat(s5));
	assertEquals(false, ValidatorUtils.validateIsNumericWithFrenchFormat(s6));
	
    }
}
