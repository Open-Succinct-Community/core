package com.venky.clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.venky.core.string.StringUtil;

public class Cluster<T> {
	private Clusterer<T> clusterer;
	private int id ;
    private static AtomicInteger fakeId = new AtomicInteger();

	public Cluster(Clusterer<T> clusterer){
		this.clusterer = clusterer;
		this.id = fakeId.addAndGet(1);
	}
	
	public int hashCode(){
		return id;
	}
	public String toString(){
		return StringUtil.valueOf(id);
	}
	
	public boolean equals(Cluster<T> cluster){
		return id == cluster.id;
	}
	
	private T centroid = null; 
	public T centroid(){
		if (centroid == null && clusterer.getCenterFinder() != null){
			centroid = clusterer.getCenterFinder().center(points);
		}
		return centroid;
	}
	
	
	private List<T> points = new ArrayList<T>();
	public void addPoint(T t){
		points.add(t);
		if (centroid != null){
			centroid = clusterer.getCenterFinder().center(centroid, points.size(), t);
		}
	}
	public double centroidDistance(T point){
		T centroid = centroid();
		if (centroid != null) { 
			return clusterer.getMetric().distance(centroid, point);
		}
		return Double.POSITIVE_INFINITY;
	}
	public double centroidDistance(Cluster<T> cluster){
		T centroid = centroid();
		if (centroid != null) { 
			return clusterer.getMetric().distance(centroid, cluster.centroid());
		}
		return Double.POSITIVE_INFINITY;
	}
	public Distance distance(T point){
		Distance distance = new Distance();
		distance.distanceFromCentroid = centroidDistance(point);
		
		distance.minDistance = Double.POSITIVE_INFINITY; 
		distance.maxDistance = Double.NEGATIVE_INFINITY;
		for (T p: points){
			double d = clusterer.getMetric().distance(p, point);
			if (d < distance.minDistance){
				distance.minDistance = d;
			}
			if (d > distance.maxDistance){
				distance.maxDistance = d;
			}
		}
		return distance;
	}
	public List<T> getPoints(){
		return points;
	}
	public Distance distance(Cluster<T> cluster){
		Distance distance = new Distance();
		distance.distanceFromCentroid = centroidDistance(cluster);
		distance.minDistance = Double.POSITIVE_INFINITY; 
		distance.maxDistance = Double.NEGATIVE_INFINITY;
		
		for (T aPointInThisCluster: points){
			for (T aPointInOtherCluster: cluster.getPoints()){
				double d = clusterer.getMetric().distance(aPointInThisCluster, aPointInOtherCluster);
				if (d < distance.minDistance){
					distance.minDistance = d;
				}
				if (d > distance.maxDistance){
					distance.maxDistance = d;
				}
			}
		}
		
		return distance;
	}
	
	public static class Distance {
		private double distanceFromCentroid; 
		private double minDistance; 
		private double maxDistance;
		
		public double getDistanceFromCentroid() {
			return this.distanceFromCentroid;
		}
		public double getMinDistance() {
			return this.minDistance;
		}
		public double getMaxDistance() {
			return this.maxDistance;
		}
		
	}
	
}
