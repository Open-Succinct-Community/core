package com.venky.clustering.geography;

import com.venky.clustering.Clusterer;
import com.venky.geo.GeoLocation;
import com.venky.geo.GeoLocationBuilder;

public class GeographicClusterer<L extends GeoLocation> extends Clusterer<L> {
	public GeographicClusterer(GeoLocationBuilder<L> builder) {
		this (new GeoCentroidFinder<L>(builder),builder);
	}
	public GeographicClusterer(GeoCentroidFinder<L> centroidFinder , GeoLocationBuilder<L> builder) {
		super(centroidFinder, new GeoMetric<L>());
	}
}
