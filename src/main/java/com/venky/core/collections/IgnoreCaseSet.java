package com.venky.core.collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.venky.core.checkpoint.Mergeable;
import com.venky.core.util.ObjectUtil;

public class IgnoreCaseSet extends AbstractIgnoreCaseCollection implements SortedSet<String>,Cloneable, Mergeable<IgnoreCaseSet>{
	private TreeSet<String> inner = new TreeSet<String>();

	public boolean equals(Object o) {
		return inner.equals(o);
	}

	public int hashCode() {
		return inner.hashCode();
	}

	public Object[] toArray() {
		return inner.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return inner.toArray(a);
	}

	public Iterator<String> iterator() {
		return inner.iterator();
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

	public boolean add(String e) {
		return inner.add(ucase(e));
	}

	public boolean remove(Object o) {
		return inner.remove(ucase(o));
	}

	public void clear() {
		inner.clear();
	}

	@SuppressWarnings("unchecked")
	public IgnoreCaseSet clone() {
		IgnoreCaseSet clone;
		try {
			clone = (IgnoreCaseSet)super.clone();
			clone.inner = (TreeSet<String>)this.inner.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return inner.toString();
	}

	public Comparator<? super String> comparator() {
		return inner.comparator();
	}

	public SortedSet<String> subSet(String fromElement, String toElement) {
		return inner.subSet(ucase(fromElement), ucase(toElement));
	}

	public SortedSet<String> headSet(String toElement) {
		return inner.headSet(ucase(toElement));
	}

	public SortedSet<String> tailSet(String fromElement) {
		return inner.tailSet(ucase(fromElement));
	}

	public String first() {
		return inner.first();
	}

	public String last() {
		return inner.last();
	}

	public void merge(IgnoreCaseSet another) {
		ObjectUtil.mergeValues(another, this);
	}
}
