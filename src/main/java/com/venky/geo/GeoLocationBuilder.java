package com.venky.geo;


public interface GeoLocationBuilder<T extends GeoLocation> {
	public T create(final float lat, final float lng);
}
