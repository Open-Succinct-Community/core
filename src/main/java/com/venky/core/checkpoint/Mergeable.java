package com.venky.core.checkpoint;


public interface Mergeable<T>{
	public void merge(T another);
}
