package com.venky.core.math;

import java.math.BigDecimal;

public class DoubleHolder implements Comparable<DoubleHolder>{
	private transient BigDecimal held ;
	public DoubleHolder(final double held){
		this(held,8);
	}
	public DoubleHolder(final double held,final int scale){
		this.held = createBigDecimal(held,scale);
	}
	public int compareTo(final DoubleHolder other) {
		return held.compareTo(other.held); 
	}
	public BigDecimal getBigDecimal(final double d1,final int scale) {
		return createBigDecimal(d1, scale);
	}
	
	private BigDecimal createBigDecimal(final double d1, final int scale){
		BigDecimal bd1 = new BigDecimal(d1);
		bd1 = bd1.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return bd1;
	}
	public boolean equals(final Object o){
		if (!(o instanceof DoubleHolder)){
			return false; 
		}
		return compareTo(((DoubleHolder)o)) == 0;
	}
	public String toString(){
		return held.toString();
	}
	public int hashCode(){
		return held.hashCode();
	}
	public BigDecimal getHeldDouble(){
		return held;
	}
}
