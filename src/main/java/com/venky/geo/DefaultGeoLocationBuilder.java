package com.venky.geo;


public class DefaultGeoLocationBuilder implements GeoLocationBuilder<GeoLocation>{

	@Override
	public GeoLocation create(final float lat, final float lng) {
		return new GeoLocation() {
			private Float latitude = lat; 
			private Float longitude = lng;
			
			@Override
			public void setLongitude(Float longitude) {
				this.longitude = longitude;
			}
			
			@Override
			public void setLatitude(Float latitude) {
				this.latitude = latitude;
			}
			
			@Override
			public Float getLongitude() {
				return longitude;
			}
			
			@Override
			public Float getLatitude() {
				return latitude;
			}
			@Override
			public String toString(){
	        	return "(lat,lng):(" + lat + "," + lng + ")";
	        }
		};
	}

}
