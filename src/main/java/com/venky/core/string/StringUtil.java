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
}
