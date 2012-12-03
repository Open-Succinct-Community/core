package com.venky.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import static junit.framework.Assert.*;
import org.junit.Test;

public class CacheTest {

	@Test
	public void test() {
		RandomNumberCache cache = new RandomNumberCache(5,0.2);
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		
		for (int i = 1 ; i <= 6; i ++ ){
			map.put(i, cache.get(i));
			assertEquals(map.get(i), cache.get(i));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				//
			}
		}
		// first would have gone.
		for (int i = 6 ; i >= 2; i -- ){
			assertEquals(map.get(i), cache.get(i));
		}
		assertTrue("First Entry should have been replaced in cache!" ,map.get(1) != cache.get(1));
	}
	public static class RandomNumberCache extends Cache<Integer, Integer>{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2999714588055843718L;
		public RandomNumberCache(int maxEntries, double pruneFactor){
			super(maxEntries,pruneFactor);
		}
		static Random r = new Random();
		@Override
		protected Integer getValue(Integer k) {
			return r.nextInt();
		}
		
	}
}
