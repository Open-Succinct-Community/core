package com.venky.clustering.euclidean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.venky.clustering.CenterFinder;
import com.venky.core.util.Bucket;

public class EuclideanCenterFinder implements CenterFinder<EuclideanPoint>{

	@Override
	public EuclideanPoint center(Collection<EuclideanPoint> points) {
		
		List<Bucket> coordinates = new ArrayList<Bucket>();
		
		for (EuclideanPoint p : points){
			int dim = p.coordinates.length;
			while (coordinates.size() < dim){
				coordinates.add(new Bucket());
			}
			for (int i = 0 ; i < dim ; i ++ ){
				coordinates.get(i).increment(p.coordinates[i]);
			}
		}
		
		return new EuclideanPoint(coordinates.toArray(new Bucket[]{}));
	}

	@Override
	public EuclideanPoint center(EuclideanPoint oldCenter, int numOldPoints, EuclideanPoint newPoint) {
		List<Bucket> coordinates = new ArrayList<Bucket>();
		
		int dim = Math.max(oldCenter.coordinates.length,newPoint.coordinates.length);
		
		for (int i = 0 ; i < dim ; i ++ ){
			while (coordinates.size() < dim){
				coordinates.add(new Bucket());
			}
			double c = ( oldCenter.coordinates[i]* numOldPoints + newPoint.coordinates[i] )/ ( numOldPoints + 1 ) ;
			coordinates.get(i).increment(c);
		}
		
		return new EuclideanPoint(coordinates.toArray(new Bucket[]{}));
	}

}
