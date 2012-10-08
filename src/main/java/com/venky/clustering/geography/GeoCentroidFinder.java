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
		Bucket x = new Bucket();
		Bucket y = new Bucket();
		Bucket z = new Bucket();
		for (L loc: points){
			double cosLat = Math.cos(loc.getLatitude()*Math.PI / 180.0);
			double sinLat = Math.sin(loc.getLatitude()*Math.PI / 180.0);
			double cosLng = Math.cos(loc.getLongitude()*Math.PI / 180.0);
			double sinLng = Math.sin(loc.getLongitude()*Math.PI / 180.0);
			
			x.increment(cosLat * cosLng);
			y.increment(cosLat * sinLng);
			z.increment(sinLat);
		}
		float lat = (float)(Math.asin(z.value()/points.size())*180.0/Math.PI);
		float lng = (float)(Math.atan(y.value() / x.value()) * 180.0/Math.PI);
		return builder.create(lat,lng);
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
