package com.venky.geo;


public class GeoDistance {
	public static int distanceKms(double lat1, double lng1, double lat2, double lng2 ){
		return (int)(6378 * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1)));
	}
}
