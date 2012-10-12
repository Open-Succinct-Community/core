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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.venky.core.checkpoint.Mergeable;
import com.venky.core.io.ByteArrayInputStream;

/**
 *
 * @author venky
 */
public class ObjectUtil {
    public static boolean equals(Object o1, Object o2){
        return (o1 == o2) || (o1 != null && o1.equals(o2));
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
     * Creates a deep clone of the object passed by serializing and deserializing it.
     * @param v Object to clone. 
     * @return A deep clone of v on successful serializing and deserializing of v. On encounting any Serialization exception, v is return unmodified.    
     */
    @SuppressWarnings("unchecked")
	public static <V extends Serializable> V deepClone(V v){
    	if (v == null){
    		return null;
    	}
    	ObjectInputStream is =null; 
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(baos);
			os.writeObject(v);
			is = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
			return (V)is.readObject();
		} catch (Exception e) {
			if (is != null){
				try {
					is.close();
				} catch (IOException e1) {
					throw new RuntimeException(e1);
				}
			}
		}
		return v;
    }
    /**
     * 
     * Attempts to create a clone of the object passed by invoking the object's clone method via reflection. 
     * On unsuccessful attempt, returns v without any modification.  
     * @param v Object to clone. 
     * @return A reflective clone created by invoking v.clone() method if it exists.    
     */

    @SuppressWarnings("unchecked")
	public static <V extends Cloneable> V reflectiveClone(V v){
    	if (v == null){
    		return null;
    	}
		try {
			Method cloneMethod= v.getClass().getMethod("clone");
			return (V) cloneMethod.invoke(v);
		} catch (Exception e) {
			return v;
		} 
    }
    @SuppressWarnings("unchecked")
	public static <V> V clone(V v){
    	V ret = v;
    	if (v != null) {
    		if (v instanceof Serializable){
        		ret = (V)deepClone((Serializable)v);
    		}
        	if ((ret == v) && (v instanceof Cloneable)){
        		ret = (V)reflectiveClone((Cloneable)v);
        	}
    	}
		return ret;
	}
    public static <K,V> void cloneValues(Map<K,V> inMap){
    	for (K k : inMap.keySet()){
    		inMap.put(k, ObjectUtil.clone(inMap.get(k)));
    	}
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K,V> void mergeValues(Map<K,V> src, Map<K,V> target){
		if (src == null){
			throw new NullPointerException("Null parameter passed");
		}
		Iterator<K> ki = target.keySet().iterator();
		
		while (ki.hasNext()){
			K k = ki.next();
			if (!src.containsKey(k)){
				ki.remove();
			}
		}
		
		for (K k :src.keySet()){
			if (!target.containsKey(k)){
				target.put(k,src.get(k));
			}else {
				V targetV = target.get(k);
				V srcV = src.get(k);
				if (targetV != null && targetV instanceof Mergeable){
					((Mergeable) targetV).merge(srcV);
				}else  {
					target.put(k,srcV);
				}
			}
		}

    }
    
    public static <E> void cloneValues(Set<E> set){
    	List<E> list = new ArrayList<E>(set.size());
    	
    	for (E e:set){
    		list.add(ObjectUtil.clone(e));
    	}
    	set.clear();
    	set.addAll(list);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> void mergeValues(Set<E> src, Set<E> target){
		if (target == null || src == null ){
			throw new NullPointerException("Null parameter passed");
		}
		
		Iterator<E> i = target.iterator();
		while (i.hasNext()){
			E e = i.next();
			if (!src.contains(e)){
				i.remove();
			}
 		}
		Map<E,E> refMap = getRefMap(target);
		
		for (E s : src){
			if (!target.contains(s)){
				target.add(s);
			}else {
				E t = refMap.get(s);
				if (t instanceof Mergeable){
					((Mergeable) t).merge(s);
				}else{
					target.remove(t);
					target.add(s);
				}
			}
		}
    }
    
    private static <E> Map<E,E> getRefMap(Set<E> set){
    	Map<E,E> map = new HashMap<E, E>();
    	for (E e: set){
    		map.put(e, e);
    	}
    	return map;
    }
}
