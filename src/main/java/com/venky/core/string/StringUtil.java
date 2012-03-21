/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.string;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.Stack;

/**
 *
 * @author venky
 */
public class StringUtil {
    public static boolean equals(String s1, String s2){
        if (s1 == null){
            return s2 == null; 
        }else {
            return valueOf(s1).equals(valueOf(s2));
        }
    }
    public static String valueOf(Object o){ 
    	return String.valueOf(o == null? "" : o).trim();
    }
    public static String camelize(String underscorizedString) {
        return camelize(underscorizedString,true);
    }
    public static String camelize(String underscorizedString,boolean capitalizeFirstChar) {
        return Inflector.camelize(underscorizedString,capitalizeFirstChar);
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
			 }
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				r.close();
			}catch(IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return builder.toString();
    }
    
    public static byte[] readBytes(InputStream in){
    	if (in == null){
    		return new byte[]{};
    	}
    	ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
    	
    	byte[] buffer = new byte[1024];
		 try {
			 int numbytesRead = 0;
			 while ((numbytesRead = in.read(buffer)) > 0){
				 out.write(buffer,0,numbytesRead);
			 }
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				in.close();
			}catch(IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return out.toByteArray();
   }
   
   
    
    public static String toWords(int number){
    	return new NumberToWordsConverter(number).toString();
    }
    
    public static String format(String template, Properties properties){
    	StringBuilder formattedString = new StringBuilder();
		char[] chars = template.toCharArray();
		Stack<StringBuilder> variables = new Stack<StringBuilder>();
		
		for (int i = 0 ; i <chars.length ; i++){
			if (chars[i] == '$'){
				if (chars[i+1] == '{'){
					variables.push(new StringBuilder());
					i+=2;
				}
			}
			if (variables.isEmpty()){
				formattedString.append(chars[i]);
			}else if (chars[i] != '}' ){
				variables.peek().append(chars[i]);
			}else {
				StringBuilder variable = variables.pop();
				if (!variables.isEmpty()){
					if (properties.containsKey(variable.toString())){
						variables.peek().append(properties.get(variable.toString()));
					}else {
						variables.peek().append("${"+variable.toString() +"}");
					}
				}else {
					if (properties.containsKey(variable.toString())){
						formattedString.append(properties.get(variable.toString()));
					}else{
						formattedString.append("${"+variable.toString() +"}");
					}
				}
			}
		}
		while (!variables.isEmpty()){
			formattedString.append("${"+variables.remove(0));
		}
		return formattedString.toString();
    }
}
