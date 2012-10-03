package com.venky.clustering;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.venky.clustering.euclidean.EuclideanMetric;
import com.venky.clustering.euclidean.EuclideanCenterFinder;
import com.venky.clustering.euclidean.EuclideanPoint;

public class ClusterEuclidean {
	@Test
	public void test(){
		Clusterer<EuclideanPoint> oneDimClusterer = new Clusterer<EuclideanPoint>(new EuclideanCenterFinder(),new EuclideanMetric());
		
		List<EuclideanPoint> points = new ArrayList<EuclideanPoint>();
		List<EuclideanPoint> p123 = new ArrayList<EuclideanPoint>();
		p123.add(new EuclideanPoint(1.0));
		p123.add(new EuclideanPoint(2.0));
		p123.add(new EuclideanPoint(3.0));
		points.addAll(p123);
		
		List<EuclideanPoint> p10111213 = new ArrayList<EuclideanPoint>();
		p10111213.add(new EuclideanPoint(10.0));
		p10111213.add(new EuclideanPoint(11.0));
		p10111213.add(new EuclideanPoint(12.0));
		p10111213.add(new EuclideanPoint(13.0));
		points.addAll(p10111213);
		
		List<EuclideanPoint> p232425 = new ArrayList<EuclideanPoint>();
		p232425.add(new EuclideanPoint(23.0));
		p232425.add(new EuclideanPoint(24.0));
		p232425.add(new EuclideanPoint(25.0));
		points.addAll(p232425);
		
		List<EuclideanPoint> pointsOver100 = new ArrayList<EuclideanPoint>();
		pointsOver100.add(new EuclideanPoint(101.0));
		pointsOver100.add(new EuclideanPoint(103.0));
		pointsOver100.add(new EuclideanPoint(107.0));
		points.addAll(pointsOver100);
		
		List<Cluster<EuclideanPoint>> clusters = oneDimClusterer.cluster(points, 4);
		Assert.assertTrue("Cluster 0 expected to contain p123" , clusters.get(0).getPoints().containsAll(p123));
		Assert.assertTrue("Cluster 1 expected to contain p10111213" ,  clusters.get(1).getPoints().containsAll(p10111213));
		Assert.assertTrue("Cluster 2 expected to contain p232425" , clusters.get(2).getPoints().containsAll(p232425));
		Assert.assertTrue("Cluster 3 expected to contain pointsOver100" , clusters.get(3).getPoints().containsAll(pointsOver100));
		
		List<EuclideanPoint> pointsLessThan100 = new ArrayList<EuclideanPoint>();
		pointsLessThan100.addAll(p123);
		pointsLessThan100.addAll(p10111213);
		pointsLessThan100.addAll(p232425);
		
		clusters = oneDimClusterer.cluster(points, 2);
		Assert.assertTrue("Cluster 0 expected to contain pointsLessThan100" , clusters.get(0).getPoints().containsAll(pointsLessThan100));
		Assert.assertTrue("Cluster 1 expected to contain pointsOver100" , clusters.get(1).getPoints().containsAll(pointsOver100));
		
		

	}
}
