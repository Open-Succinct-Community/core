package com.venky.core.collections;

import java.util.TreeMap;

import com.venky.core.checkpoint.Mergeable;
import com.venky.core.util.ObjectUtil;

public class IgnoreCaseMap<V> extends TreeMap<String, V> implements Cloneable, Mergeable<IgnoreCaseMap<V>>{
	private static final long serialVersionUID = 5311588254312204361L;

	protected String ucase(Object other){
		return String.valueOf(other).toUpperCase();
	}

	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(ucase(key));
	}

	@Override
	public V get(Object key) {
		return super.get(ucase(key));
	}

	@Override
	public V put(String key, V value) {
		return super.put(ucase(key), value);
	}

	@Override
	public V remove(Object key) {
		return super.remove(ucase(key));
	}

	public IgnoreCaseMap<V> clone(){
		IgnoreCaseMap<V> clone = (IgnoreCaseMap<V>) super.clone();
		ObjectUtil.cloneValues(clone);
		return clone;
	}

	public void merge(IgnoreCaseMap<V> another) {
		ObjectUtil.mergeValues(another,this);
	}

}
