package com.venky.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class SequenceSet<E> implements Set<E> , Cloneable, List<E>{
	private ArrayList<E> list ;
	private HashSet<E> set ;
	
	public SequenceSet(){
		set = new HashSet<E>();
		list = new ArrayList<E>();
	}

	public List<E> list() {
		return Collections.unmodifiableList(list);
	}
	
	public E first(){
		return list.get(0);
	}
	
	public int indexOf(Object o){
		return list.indexOf(o);
	}
	
	public E get(int index){
		return list.get(index);
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
	
	@Override
	public Object clone(){
		try {
			SequenceSet<E> set = (SequenceSet<E>)super.clone();
			set.list = (ArrayList)this.list.clone();
			set.set = (HashSet)this.set.clone();
			set.addAll(this);
			return set;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString(){
		return list.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return set.equals(o);
	}

	@Override
	public int hashCode(){
		return set.hashCode();
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		Iterator<? extends E> i = c.iterator();
		boolean ret = false;
		while (i.hasNext()){
			ret = add(i.next()) || ret;
		}
		return ret;
	}

	public E set(int index, E element) {
		E old = remove(index);
		add(index,element);
		return old;
	}

	public void add(int index, E element) {
		if (contains(element)){
			int idx = list.indexOf(element);
			list.remove(idx);
			list.add(index,element);
		}else {
			set.add(element);
			list.add(index,element);
		}
		
	}

	public E remove(int index) {
		E o = get(index);
		remove(o);
		return o;
	}

	public int lastIndexOf(Object o) {
		return indexOf(o);
	}

	public ListIterator<E> listIterator() {
		return Collections.unmodifiableList(list).listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return Collections.unmodifiableList(list).listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return Collections.unmodifiableList(list).subList(fromIndex, toIndex);
	}
}
