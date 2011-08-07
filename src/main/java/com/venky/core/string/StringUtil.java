/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author venky
 */
public class StringUtil {
    public static boolean equals(String s1, String s2){
        if (s1 == null){
            return s2 == null; 
        }else {
            return s1.equals(s2);
        }
    }
    public static String valueOf(Object o){ 
    	return String.valueOf(o == null? "" : o).trim();
    }
    public static String camelize(String underscorizedString) {
        return Inflector.camelize(underscorizedString);
    }
    
    public static String underscorize(String camel){
        return Inflector.underscore(camel);
    }
    
    public static String pluralize(String singular){
        return Inflector.pluralize(singular);
    }
    public static String singularize(String plural){
        return Inflector.singularize(plural);
    }
    
    public static String read(InputStream in){
    	return read(new BufferedReader(new InputStreamReader(in)));
    	
    }
    public static String read(Reader reader){
		 BufferedReader r = new BufferedReader(reader);
		 StringBuilder builder = new StringBuilder();
		 char[] buffer = new char[1024];
		 try {
			 int numCharsRead = 0;
			 while ((numCharsRead = r.read(buffer)) > 0){
				 builder.append(buffer,0,numCharsRead);
				 if (numCharsRead < buffer.length){
					 break;
				 }
			 }
			 r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
    }
}
