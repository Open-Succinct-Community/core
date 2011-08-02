/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.util;

import java.util.Properties;

/**
 *
 * @author venky
 */
public class ObjectUtil {
    public static boolean equals(Object o1, Object o2){
        if (o1 == null){
            return o2 == null; 
        }else {
            return o1.equals(o2);
        }
    }
    
    public static boolean isVoid(Object o){
        if (o == null){
            return true;
        }else if (o instanceof String){
            String s = (String)o; 
            return (s.trim().length() == 0);
        }
        return false;
    }
    
    public static Properties createProperties(boolean lowercase, String... namevalues){
        Properties p = new Properties();
        if (namevalues != null && namevalues.length > 0){
            if (namevalues.length % 2 != 0){
                throw new RuntimeException("WrongNumberOfParameters");
            }
            for (int i = 0 ; i < namevalues.length ; i = i + 2){
                if (lowercase){
                    p.setProperty(namevalues[i].toLowerCase(), namevalues[i+1].toLowerCase());
                }else {
                    p.setProperty(namevalues[i], namevalues[i+1]);
                }
            }
        }
        return p;
    }
}
