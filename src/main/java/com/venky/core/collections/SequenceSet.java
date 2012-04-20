package com.venky.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SequenceSet<E> implements Set<E>{
	private ArrayList<E> list ;
	private HashSet<E> set ;
	
	public SequenceSet(){
		set = new HashSet<E>();
		list = new ArrayList<E>();
	}

	public E first(){
		return list.get(0);
	}
	
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
		return new Iterator<E>(){
			Iterator<E> li = list.iterator();
			E current = null;

			public boolean hasNext() {
				return li.hasNext();
			}

			public E next() {
				current = li.next();
				return current;
			}

			public void remove() {
				li.remove();
				set.remove(current);
			}
			
		};
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public boolean add(E e) {
		return set.add(e) && list.add(e);
	}

	public boolean remove(Object o) {
		return set.remove(o) && list.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		Iterator<? extends E> i = c.iterator();
		boolean ret = false;
		while (i.hasNext()){
			ret = add(i.next()) || ret ;
		}
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c) && list.retainAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c) && list.removeAll(c);
	}

	public void clear() {
		set.clear();
		list.clear();
	} 
}
