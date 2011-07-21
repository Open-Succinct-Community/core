/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.util;

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
}
