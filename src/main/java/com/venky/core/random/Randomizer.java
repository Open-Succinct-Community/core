package com.venky.core.random;

public class Randomizer {
	/**
	 * @param min
	 * @param max
	 * @return A random number between min and max, inclusive of both the end points.
	 */
	public static int getRandomNumber(final int min, final int max){
		return (int)(min + Math.random() * (max - min + 1)); 
	}
}
