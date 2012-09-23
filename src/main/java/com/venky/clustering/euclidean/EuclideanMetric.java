package com.venky.clustering.euclidean;

import com.venky.clustering.Metric;

public class EuclideanMetric implements Metric<EuclideanPoint> {

	@Override
	public double distance(EuclideanPoint p1, EuclideanPoint p2) {
		int len = p1.coordinates.length;
		EuclideanPoint higherDimensionPoint = p2; 
		if (p2.coordinates.length < p1.coordinates.length){
			len = p2.coordinates.length;
			higherDimensionPoint = p1 ; 
		}
		double distance = 0; 
		for (int i = 0 ; i < len ; i ++ ){
			double coordiff =  (p1.coordinates[i] - p2.coordinates[i]);
			distance += (coordiff * coordiff)  ;
		}
		for (int i = len ; i < higherDimensionPoint.coordinates.length ; i ++ ){
			double coordiff = (higherDimensionPoint.coordinates[i] - 0);
			distance += (coordiff * coordiff);
		}
		return Math.sqrt(distance);
	}

}
