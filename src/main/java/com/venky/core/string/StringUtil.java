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
    
}
