package com.venky.clustering;

public interface Metric<T> {
	public double distance(T p1,T p2);
}
