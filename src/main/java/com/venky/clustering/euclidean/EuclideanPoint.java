package com.venky.clustering.euclidean;

import java.util.Arrays;

public class EuclideanPoint {
	double [] coordinates ;
	
	public EuclideanPoint(Number... coordinates){
		this.coordinates = new double[coordinates.length];
		for (int i = 0 ; i < coordinates.length ; i ++ ){
			this.coordinates[i] = coordinates[i].doubleValue();
		}
	}
	public double[] getCoordinates(){ 
		return coordinates;
	}
	public double getCoordinate(int index){ 
		if (index >= coordinates.length || index < 0 ) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return coordinates[index];
	}

	@Override
	public String toString(){
		StringBuilder b = new StringBuilder(); 
		b.append("(");
		for (int i = 0 ; i < coordinates.length ; i ++ ){
			if (i > 0){
				b.append(",");
			}
			b.append(coordinates[i]);
		}
		b.append(")");
		return b.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(this.coordinates);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EuclideanPoint other = (EuclideanPoint) obj;
		if (!Arrays.equals(this.coordinates, other.coordinates))
			return false;
		return true;
	}
	
	

}
