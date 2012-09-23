package com.venky.clustering.geography;

import com.venky.clustering.Clusterer;
import com.venky.geo.GeoLocation;
import com.venky.geo.GeoLocationBuilder;

public class GeographicClusterer<L extends GeoLocation> extends Clusterer<L> {
	public GeographicClusterer(GeoLocationBuilder<L> builder) {
		super(new GeoCentroidFinder<L>(builder), new GeoMetric<L>());
	}
}
