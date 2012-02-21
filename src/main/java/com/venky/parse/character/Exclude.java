package com.venky.parse.character;

import java.util.ArrayList;
import java.util.Collection;


public class Exclude extends AbstractSingleCharacterRule{

	Collection<Character> exclude = null;
	
	public Exclude(char... exclude){
		this.exclude = new ArrayList<Character>();
		for (int  i = 0 ; i < exclude.length ; i++){
			this.exclude.add(exclude[i]);
		}
	}
	public Exclude(Collection<Character> exclude){
		this.exclude = exclude;
	}
	@Override
	protected boolean match(char c) {
		return !this.exclude.contains(c);
	}

}
