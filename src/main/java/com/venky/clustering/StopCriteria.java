package com.venky.clustering;

import java.util.List;

public interface StopCriteria {
	public <T> boolean canStop(List<Cluster<T>> currentClusters);
}
