package com.venky.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class Encryptor {
	public static String encrypt(String key) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		digest.reset();
		
		digest.update(key.getBytes());
		
		byte[] bytes = digest.digest();
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0 ; i< bytes.length ; i++ ){
			String hex = Integer.toHexString(bytes[i]);
			if (hex.length() == 1){
				hex = "0"+hex;
			}
			hex = hex.substring(hex.length()-2);
			builder.append(hex);
		}
		Logger.getLogger(Encryptor.class.getName()).fine("Encrypted:" + key + " to " + builder.toString());
		return builder.toString();
	}
}
