package org.kocakaya.caisse.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.kocakaya.caisse.exception.CaisseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);
    
    public static String getMD5EncryptedPassword(char[] password) throws CaisseException{
	return getMD5EncryptedPassword(new String(password));
    }

    public static String getMD5EncryptedPassword(String password) throws CaisseException {
	LOGGER.debug("Password encryption with MD5 algorithm");
	byte[] encryptedPassword = null;
	String result = null;
	try {
	    byte[] passwordInBytes = password.getBytes();
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(passwordInBytes);
	    encryptedPassword = md.digest();
	    result = new String(encryptedPassword);
	} catch (NoSuchAlgorithmException e) {
	    throw new CaisseException(e);
	}
	return result;
    }
}
