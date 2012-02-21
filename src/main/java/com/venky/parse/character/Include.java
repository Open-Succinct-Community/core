package com.venky.parse.character;

import java.util.ArrayList;
import java.util.Collection;


public class Include extends AbstractSingleCharacterRule{

	Collection<Character> include = null;
	public Include(char... anyofthese){
		this.include = new ArrayList<Character>();
		for (int i = 0 ; i < anyofthese.length ; i ++){
			this.include.add(anyofthese[i]);
		}
	}
	
	public Include(Collection<Character> anyofthese){
		this.include = anyofthese;
	}

	@Override
	protected boolean match(char c) {
		return include.contains(c);
	}

}
