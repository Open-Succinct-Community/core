package com.venky.geo;


public interface GeoLocationBuilder<T extends GeoLocation> {
	public T create(float lat, float lng);
}
