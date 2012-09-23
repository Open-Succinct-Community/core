package com.venky.clustering.geography;

import com.venky.clustering.Metric;
import com.venky.geo.GeoDistance;
import com.venky.geo.GeoLocation;

public class GeoMetric<L extends GeoLocation> implements Metric<L>{

	@Override
	public double distance(GeoLocation p1, GeoLocation p2) {
		return GeoDistance.distanceKms(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
	}

}
