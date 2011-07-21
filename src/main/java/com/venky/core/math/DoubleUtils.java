package com.venky.core.math;


public class DoubleUtils {
	public static int compareTo(final double d1, final double d2) {
		final DoubleHolder bd1 = new DoubleHolder(d1);
		final DoubleHolder bd2 = new DoubleHolder(d2);
		return bd1.compareTo(bd2);
	}
	public static boolean equals(final double d1, final double d2) {
		return 0 == compareTo(d1, d2);
	}
	public static double min(final double d1, final double d2) {
		return compareTo(d1, d2) < 0 ? d1 : d2;
	}
	public static double max(final double d1, final double d2) {
		return compareTo(d1, d2) < 0 ? d2 : d1;
	}

}
