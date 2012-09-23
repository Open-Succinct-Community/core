package com.venky.clustering.geography;

import java.util.Collection;

import com.venky.clustering.CenterFinder;
import com.venky.core.util.Bucket;
import com.venky.geo.GeoLocation;
import com.venky.geo.GeoLocationBuilder;

public class GeoCentroidFinder<L extends GeoLocation> implements CenterFinder<L>{
	private GeoLocationBuilder<L> builder;
	public GeoCentroidFinder(GeoLocationBuilder<L> builder){
		this.builder = builder;
	}
	@Override
	public L center(Collection<L> points) {
		Bucket lat = new Bucket();
		Bucket lng = new Bucket();
		for (L loc: points){
			lat.increment(loc.getLatitude());
			lng.increment(loc.getLongitude());
		}
		return builder.create(lat.floatValue()/points.size(), lng.floatValue() / points.size());
	}

	@Override
	public L center(L oldCenter, int numOldPoints, L newPoint) {
		Bucket lat = new Bucket();
		Bucket lng = new Bucket();
		if (oldCenter != null){
			lat.increment(oldCenter.getLatitude() * numOldPoints);
			lng.increment(oldCenter.getLongitude() * numOldPoints);
		}
		lat.increment(newPoint.getLatitude());
		lng.increment(newPoint.getLongitude());
		return builder.create(lat.floatValue()/(numOldPoints + 1), lng.floatValue() / (numOldPoints + 1));
	}

}
