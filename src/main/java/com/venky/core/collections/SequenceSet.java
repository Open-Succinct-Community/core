package com.venky.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

import com.venky.core.util.ObjectUtil;

public class SequenceSet<E> implements Set<E> , Cloneable, List<E>{
	private ArrayList<E> list ;
	private HashMap<E,Integer> set ;
	
	public SequenceSet(){
		set = new HashMap<E,Integer>();
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
		return set.containsKey(o);
	}

	public Iterator<E> iterator() {
		return listIterator(0);
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public boolean add(E e) {
		if (!set.containsKey(e)){
			list.add(e);
			set.put(e, list.size()-1);
			return true;
		}
		return false;
	}

	public boolean remove(Object o) {
		Integer indexInList = set.remove(o);
		if (indexInList != null){
			list.remove(indexInList.intValue());
			resetIndexes(indexInList);
			return true;
		}
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		return set.keySet().containsAll(c);
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
		Iterator<E> i = iterator();
		boolean ret = false; 
		while (i.hasNext()){
			E e = i.next();
			if (!c.contains(e)){
				i.remove();
				ret = true;
			}
		}
		return ret;
	}

	public boolean removeAll(Collection<?> c) {
		Iterator<?> i = c.iterator();
		boolean modified = false;
		while (i.hasNext()){
			Object o = i.next();
			modified = remove(o) || modified;
		}
		return modified;
	}

	public void clear() {
		set.clear();
		list.clear();
	}
	
	@Override
	public Object clone(){
		try {
			@SuppressWarnings("unchecked")
			SequenceSet<E> set = (SequenceSet<E>)super.clone();
			set.list = new ArrayList<E>();
			set.set =  new HashMap<E,Integer>();
			for (int i = 0 ; i < size() ; i ++ ){
				E clone = ObjectUtil.clone(get(i));
				set.list.add(clone);
				set.set.put(clone, i);
			}
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
			ret = true; 
			add(index++ ,i.next());
		}
		return ret;
	}

	public E set(int index, E element) {
		E elementKnockedOut = get(index);
		Integer oldIndex = set.get(element);
		if (oldIndex != null){
			list.set(oldIndex,elementKnockedOut);
			set.put(elementKnockedOut, oldIndex);
		}else {
			set.remove(elementKnockedOut);
		}
		list.set(index, element);
		set.put(element, index);
		return elementKnockedOut;
	}

	public void add(int index, E element) {
		Integer idx = set.get(element);
		if (idx != null){
			if (idx.intValue() == index){
				return;
			}
			list.remove(idx.intValue());
		}
		list.add(index,element);
		if (idx == null){
			resetIndexes(index) ;
		}else if (idx < index){
			resetIndexes(idx,index);
		}else {
			resetIndexes(index,idx);
		}
	}
	private void resetIndexes(int from,int to){
		for (int i = from; i <= to ; i ++){
			set.put(list.get(i),i);
		}
	}
	private void resetIndexes(int from){
		resetIndexes(from,list.size()-1);
	}
	public E remove(int index) {
		E o = list.remove(index);
		set.remove(o);
		resetIndexes(index);
		return o;
	}

	public int lastIndexOf(Object o) {
		return indexOf(o);
	}
	
	private class Itr implements ListIterator<E> {
		int cursor;
		public Itr(int index){
			this.cursor = index;
		}
		@Override
		public boolean hasNext() {
			return cursor < list.size();
		}

		@Override
		public E next() {
			E v = get(cursor);
			cursor ++; 
			return v;
		}

		@Override
		public boolean hasPrevious() {
			return cursor > 0;
		}
		@Override
		public E previous() {
			int i = cursor - 1 ;
			try {
				E v = get(i);
				cursor = i  ;
				return v;
			}catch (IndexOutOfBoundsException ex){
				throw new NoSuchElementException();
			}
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		@Override
		public void remove() {
			cursor--;
			SequenceSet.this.remove(cursor);
		}

		@Override
		public void set(E e) {
			SequenceSet.this.set(cursor-1,e);
		}

		@Override
		public void add(E e) {
			SequenceSet.this.add(cursor,e);
			cursor ++ ;
		}
		
	}
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}

	public ListIterator<E> listIterator(int index) {
		return new Itr(0);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return list().subList(fromIndex, toIndex);
	}
}
