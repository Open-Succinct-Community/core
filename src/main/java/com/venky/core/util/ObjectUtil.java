/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import com.venky.core.io.ByteArrayInputStream;

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
    
    /**
     * 
     * If v instanceof Serializable, them the object is serialized and deserialized to create deep clone.
     * else if v instanceof Cloneable then the object.clone method is invoked to create a clone.
     * else v 
     * @param v Object to clone. 
     * @return A clone of v if v is either Cloneable or Serializable else the input v is itself returned.   
     */
    public static <V extends Serializable> V deepClone(V v){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(baos);
			os.writeObject(v);
			ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
			return (V)is.readObject();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
    }
    public static <V> V clone(V v){
    	if (v == null){
    		return null;
    	}
    	V ret = v;
		if (v instanceof Serializable){
			try {
				return (V)deepClone((Serializable)v);
			}catch(Exception ex){
				// Try cloning.
			}
		}
		if (v instanceof Cloneable){
			Method clone;
			try {
				clone = v.getClass().getMethod("clone");
    			ret = (V) clone.invoke(v); 
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			} catch (RuntimeException e) {
				throw (e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(ExceptionUtil.getRootCause(e));
			} 
		}
		
		return ret;
	}
    
}
