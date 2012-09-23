package com.venky.clustering;

import java.util.Collection;

public interface CenterFinder<T> {
	public T center(Collection<T> points);
	public T center(T oldCenter, int numOldPoints, T newPoint);
}
