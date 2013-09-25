package com.example.demo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {

	
	public static String hash(String src) throws NoSuchAlgorithmException{
		MessageDigest sha1MD = MessageDigest.getInstance("SHA-1");
		try {
			sha1MD.update(src.getBytes("utf-8"), 0, src.length());
		} catch (UnsupportedEncodingException e) {
			sha1MD.update(src.getBytes(), 0, src.length());
		}
		
		return toHexString(sha1MD.digest());
	}
	

	private static String toHexString(byte [] hashs){
	    StringBuffer sBuffer = new StringBuffer();
	    for(byte hash : hashs){
	        sBuffer.append(Integer.toString(( hash & 0xFF ) + 0x100, 16).substring( 1 ));
	    }
	    return sBuffer.toString();
	}
	
	public static boolean isEmpty(String src){
		return null == src || "".equals(src.trim());
	}
}
