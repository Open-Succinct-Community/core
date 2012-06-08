package com.venky.core.checkpoint;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MergeableSet<E extends Serializable> implements Set<E>, Mergeable<Set<E>> , Serializable{

	private final Set<E> set;
	public int size() {
		return set.size();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public boolean contains(Object o) {
		return set.contains(o);
	}

	public Iterator<E> iterator() {
		return set.iterator();
	}

	public Object[] toArray() {
		return set.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	public boolean add(E e) {
		return set.add(e);
	}

	public boolean remove(Object o) {
		return set.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	public void clear() {
		set.clear();
	}

	public boolean equals(Object o) {
		return set.equals(o);
	}

	public int hashCode() {
		return set.hashCode();
	}

	public MergeableSet(Set<E> set){
		this.set = set;
	}

	private static final long serialVersionUID = 8174625181602888314L;

	public void merge(Set<E> another) {
		if (another == null){
			throw new NullPointerException("Null parameter passed");
		}
		
		Iterator<E> i = iterator();
		while (i.hasNext()){
			E e = i.next();
			if (!another.contains(e)){
				i.remove();
			}
 		}
		for (E e : another){
			if (!contains(e)){
				add(e);
			}
		}
	}

	

}
