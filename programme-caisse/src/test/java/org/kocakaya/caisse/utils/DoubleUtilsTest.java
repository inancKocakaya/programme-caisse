package org.kocakaya.caisse.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DoubleUtilsTest {

    @Test
    public void testValue() {
       String s = "1500,266";
       Double d = 1500.27;
       assertEquals(d, DoubleUtils.stringToDouble(s));
    }
}
