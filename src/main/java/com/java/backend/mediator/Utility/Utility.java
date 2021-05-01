package com.java.backend.mediator.Utility;

import java.security.SecureRandom;

public class Utility {

	private static final String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";	
	
	private static SecureRandom rnd = new SecureRandom();

	public static String randomString(int len){
	   StringBuilder sb = new StringBuilder(len);
	   for(int i = 0; i < len; i++)
	      sb.append(str.charAt(rnd.nextInt(str.length())));
	   return sb.toString();
	}
	
	public static boolean isBlank(String s) {
		return s.trim().length() == 0 ? true : false;
	}
	
	public static boolean isEmpty(String s) {
		return (s == null || s.length() == 0) ? true : false;
	}
}
