package com.venky.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IgnoreCaseList extends AbstractIgnoreCaseCollection implements List<String>,Cloneable{
	private List<String> inner =null;
	public IgnoreCaseList(){
		this(true);
	}
	public IgnoreCaseList(boolean allowDuplicates){
		this(allowDuplicates,null);
	}
	public IgnoreCaseList(Collection<String> c){
		this(true,c);
	}
	public IgnoreCaseList(boolean allowDuplicates,Collection<String> initial){
		if (allowDuplicates){
			inner = new ArrayList<String>();
		}else {
			inner = new SequenceSet<String>();
		}
		if (initial != null){
			inner.addAll(initial);
		}
	}
	public int size() {
		return inner.size();
	}
	public boolean isEmpty() {
		return inner.isEmpty();
	}
	public boolean contains(Object o) {
		return inner.contains(ucase(o));
	}
	public Iterator<String> iterator() {
		return inner.iterator();
	}
	public Object[] toArray() {
		return inner.toArray();
	}
	public <T> T[] toArray(T[] a) {
		return inner.toArray(a);
	}
	public boolean add(String e) {
		return inner.add(ucase(e));
	}
	public boolean remove(Object o) {
		return inner.remove(ucase(o));
	}
	public boolean addAll(int index, Collection<? extends String> c) {
		boolean modified = false;
		int i = index;
		for (String s:c){
			add(i,s);
			modified = true;
			i++;
		}
		return modified;
	}

	public void clear() {
		inner.clear();
	}
	public boolean equals(Object o) {
		return inner.equals(o);
	}
	public int hashCode() {
		return inner.hashCode();
	}
	public String get(int index) {
		return inner.get(index);
	}
	public String set(int index, String element) {
		return inner.set(index, ucase(element));
	}
	public void add(int index, String element) {
		inner.add(index, ucase(element));
	}
	public String remove(int index) {
		return inner.remove(index);
	}
	public int indexOf(Object o) {
		return inner.indexOf(ucase(o));
	}
	public int lastIndexOf(Object o) {
		return inner.lastIndexOf(ucase(o));
	}
	public ListIterator<String> listIterator() {
		return inner.listIterator();
	}
	public ListIterator<String> listIterator(int index) {
		return inner.listIterator(index);
	}
	public List<String> subList(int fromIndex, int toIndex) {
		return inner.subList(fromIndex, toIndex);
	}
	
	@SuppressWarnings("unchecked")
	public Object clone() {
		try {
			IgnoreCaseList clone = (IgnoreCaseList)super.clone();
			if (inner instanceof ArrayList){
				clone.inner = (List<String>)(((ArrayList<String>) inner).clone());
			}else {
				clone.inner = (List<String>)(((SequenceSet<String>) inner).clone());
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	public String toString() {
		return inner.toString();
	}
	

}
