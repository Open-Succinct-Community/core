package com.venky.core.checkpoint;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.venky.core.util.ObjectUtil;

public class MergeableSet<E extends Serializable> implements Set<E>, Mergeable<Set<E>> , Serializable{

	private Set<E> set;
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
	public MergeableSet(){
		this(new HashSet<E>());
	}
	public MergeableSet(Set<E> set){
		this.set = set;
	}

	private static final long serialVersionUID = 8174625181602888314L;

	public void merge(Set<E> another) {
		ObjectUtil.mergeValues(another, this);
	}

	@SuppressWarnings("unchecked")
	public MergeableSet<E> clone(){
		try {
			MergeableSet<E> clone = (MergeableSet<E>) super.clone();
			clone.set = ObjectUtil.clone(set);
			if (clone.set != set){
				//Clone successful. Trying to clone deeper by cloning values.
				boolean isShallow = false;
				Iterator<E> i = clone.set.iterator();
				while (i.hasNext() && !isShallow){
					E e = i.next();
					if (e != null){
						for (E selfE: set){
							if (selfE == e){
								isShallow = true;
								break;
							}
						}
					}
				}
				if (isShallow){
					ObjectUtil.cloneValues(clone.set);
				}
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	

}
