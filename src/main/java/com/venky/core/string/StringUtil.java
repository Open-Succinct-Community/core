/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.string;

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
    public static String titlize(String underscorizedString) {
        StringBuilder buff = new StringBuilder(underscorizedString.length());
        boolean toUpper = true;
        char[] cArray = underscorizedString.toLowerCase().toCharArray();
        for (int i = 0;i < cArray.length;i++) {
            char c = cArray[i];
            if (toUpper) {
                buff.append(Character.toUpperCase(c));
                toUpper = false;
            } else if (c == '_') {
                toUpper = true;
            } else {
                buff.append(c);
            }
        }
        return buff.toString();
    }
    
    public static String underscorize(String s){
        if (s == null || s.length() == 0 ){
            return s;
        }
        char[] cArray = s.toCharArray();
        StringBuilder buff = new StringBuilder();
        buff.append(cArray[0]);
        for (int i = 1 ; i < cArray.length ; i ++){
            if (cArray[i] <= 'Z' && cArray[i] >= 'A') {
                buff.append("_");
            }
            buff.append(Character.toUpperCase(cArray[i]));
        }
        return buff.toString();
    }
}
