package com.venky.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class ClusterTest implements Metric<Double>, CenterFinder<Double> {
	
	@Test
	public void test() {
		Clusterer<Double> oneDimClusterer = new Clusterer<Double>(this, this);
		List<Double> points = new ArrayList<Double>();
		List<Double> p123 = new ArrayList<Double>();
		p123.add(1.0);
		p123.add(2.0);
		p123.add(3.0);
		points.addAll(p123);
		
		List<Double> p10111213 = new ArrayList<Double>();
		p10111213.add(10.0);
		p10111213.add(11.0);
		p10111213.add(12.0);
		p10111213.add(13.0);
		points.addAll(p10111213);
		
		List<Double> p232425 = new ArrayList<Double>();
		p232425.add(23.0);
		p232425.add(24.0);
		p232425.add(25.0);
		points.addAll(p232425);
		
		List<Double> pointsOver100 = new ArrayList<Double>();
		pointsOver100.add(101.0);
		pointsOver100.add(103.0);
		pointsOver100.add(107.0);
		points.addAll(pointsOver100);
		
		List<Cluster<Double>> clusters = oneDimClusterer.cluster(points, 4);
		Assert.assertTrue("Cluster 0 expected to contain p123" , clusters.get(0).getPoints().containsAll(p123));
		Assert.assertTrue("Cluster 1 expected to contain p10111213" ,  clusters.get(1).getPoints().containsAll(p10111213));
		Assert.assertTrue("Cluster 2 expected to contain p232425" , clusters.get(2).getPoints().containsAll(p232425));
		Assert.assertTrue("Cluster 3 expected to contain pointsOver100" , clusters.get(3).getPoints().containsAll(pointsOver100));
		
		List<Double> pointsLessThan100 = new ArrayList<Double>();
		pointsLessThan100.addAll(p123);
		pointsLessThan100.addAll(p10111213);
		pointsLessThan100.addAll(p232425);
		
		clusters = oneDimClusterer.cluster(points, 2);
		Assert.assertTrue("Cluster 0 expected to contain pointsLessThan100" , clusters.get(0).getPoints().containsAll(pointsLessThan100));
		Assert.assertTrue("Cluster 1 expected to contain pointsOver100" , clusters.get(1).getPoints().containsAll(pointsOver100));
		
		
		
	}


	@Override
	public Double center(Collection<Double> points) {
		double value = 0 ;
		int n = 0 ;
		for (Double p : points){
			value += p;
			n ++ ;
		}
		return (value/n);
	}


	@Override
	public Double center(Double oldCenter, int numOldPoints, Double newPoint) {
		if (oldCenter == null){
			return newPoint;
		}
		return ((oldCenter * numOldPoints) + newPoint) / (numOldPoints + 1);
	}


	@Override
	public double distance(Double p1, Double p2) {
		return Math.abs(p1 - p2);
	}

}
