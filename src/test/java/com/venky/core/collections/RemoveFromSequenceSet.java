package com.venky.core.collections;

import java.util.Arrays;

import org.junit.Test;

public class RemoveFromSequenceSet {
	@Test
	public void test(){ 
		SequenceSet<String> s = new SequenceSet<String>();
		s.add("1");
		s.add("2");
		s.add("3");
		s.add("4");
		
		s.retainAll(Arrays.asList(new String[]{"1","5"}));
		System.out.println(s);
		
		
	}
}
