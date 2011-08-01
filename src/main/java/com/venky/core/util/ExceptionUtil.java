/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.util;

/**
 *
 * @author venky
 */
public class ExceptionUtil {
    public static Throwable getRootCause(Throwable in){
        Throwable out = in;
        while (out.getCause()!= null){
            out = out.getCause();
        }
        return out;

    }
}
