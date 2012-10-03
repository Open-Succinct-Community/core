package com.venky.core.collections;

import java.util.Collections;

import org.junit.Test;

public class SortSequenceSet {

	@Test
	public void test() {
		SequenceSet<Integer> s = new SequenceSet<Integer>();
		s.add(0,1);
		s.add(1,5);
		s.add(2,2);
		System.out.println(s);
		Collections.sort(s);
		System.out.println(s);
		s.add(2, 0);
		System.out.println(s);
		Collections.sort(s);
		System.out.println(s);
	}

}
