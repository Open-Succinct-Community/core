package com.venky.core.checkpoint;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.venky.core.util.ObjectUtil;

public class MergeableMap <K ,V> implements Serializable, Map<K,V> , Mergeable<Map<K, V>> {
	

	private static final long serialVersionUID = -7236530815567656228L;
	private Map<K,V> proxyMap;
	public MergeableMap(){
		this(new HashMap<K, V>());
	}
	public MergeableMap(Map<K,V> proxyMap){
		this.proxyMap = proxyMap;
	}
	
	@Override
	public MergeableMap<K, V> clone(){
		try {
			@SuppressWarnings("unchecked")
			MergeableMap<K, V> clone = (MergeableMap<K, V>) super.clone();
			clone.proxyMap = ObjectUtil.clone(proxyMap);
			if (clone.proxyMap != proxyMap){
				for (K k:proxyMap.keySet()){
					V v = proxyMap.get(k);
					if (v == null){
						continue;
					}
					V vInClone  = clone.proxyMap.get(k); 
					if (v != vInClone){
						// Value is already cloned. So skip this step entirely. 
						break;
					}else {
						//Shallow Cloned. .. Try to clone values.
						clone.proxyMap.put(k, ObjectUtil.clone(vInClone));
					}
				}
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Should have never happend!",e);
		}
	}

	public void merge(Map<K, V> another) {
		ObjectUtil.mergeValues(another, this);
	}
	public int size() {
		return proxyMap.size();
	}
	public boolean isEmpty() {
		return proxyMap.isEmpty();
	}
	public boolean containsKey(Object key) {
		return proxyMap.containsKey(key);
	}
	public boolean containsValue(Object value) {
		return proxyMap.containsValue(value);
	}
	public V get(Object key) {
		return proxyMap.get(key);
	}
	public V put(K key, V value) {
		return proxyMap.put(key,value);
	}
	public V remove(Object key) {
		return proxyMap.remove(key);
	}
	
	public void putAll(Map<? extends K, ? extends V> m) {
		proxyMap.putAll(m);
	}
	public void clear() {
		proxyMap.clear();
	}
	public Set<K> keySet() {
		return proxyMap.keySet();
	}
	public Collection<V> values() {
		return proxyMap.values();
	}
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return proxyMap.entrySet();
	}
	
}
