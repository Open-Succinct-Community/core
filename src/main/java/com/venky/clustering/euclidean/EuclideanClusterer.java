package com.venky.clustering.euclidean;

import com.venky.clustering.Clusterer;

public class EuclideanClusterer extends Clusterer<EuclideanPoint>{

	public EuclideanClusterer() {
		super(new EuclideanCenterFinder(),new EuclideanMetric());
	}
 
}
