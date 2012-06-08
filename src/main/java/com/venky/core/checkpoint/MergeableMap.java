package com.venky.core.checkpoint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.venky.core.util.ObjectUtil;

public class MergeableMap <K ,V> extends HashMap<K, V> implements Mergeable<Map<K, V>>{

	private static final long serialVersionUID = -7236530815567656228L;

	public MergeableMap() {
		super();
	}

	public MergeableMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public MergeableMap(int initialCapacity) {
		super(initialCapacity);
	}

	public MergeableMap(Map<? extends K, ? extends V> m) {
		super(m);
	}

	public void merge(Map<K, V> another) {
		if (another == null){
			throw new NullPointerException("Null parameter passed");
		}
		Iterator<K> ki = keySet().iterator();
		
		while (ki.hasNext()){
			K k = ki.next();
			if (!another.containsKey(k)){
				ki.remove();
			}
		}
		
		for (K k :another.keySet()){
			if (!containsKey(k)){
				put(k,another.get(k));
			}else {
				V v = get(k);
				V anotherV = another.get(k);
				if (!ObjectUtil.equals(v, anotherV)){
					if (v != null && v instanceof Mergeable){
						((Mergeable) v).merge(anotherV);
					}else  {
						put(k,anotherV);
					}
				}
			}
		}
		
	}
	
}
