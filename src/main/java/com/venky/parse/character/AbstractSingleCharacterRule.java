package com.venky.parse.character;

import com.venky.parse.Rule;


public abstract class AbstractSingleCharacterRule extends Rule {
	
	@Override
	public boolean match(String input, int offset) {
		if (offset >= input.length()) {
			return false;
		}
		char c = input.charAt(offset);
		boolean ret = match(c);
		if (ret){
			setMatch(new Element(this,String.valueOf(c)));
		}
		return ret;
	}
	protected abstract boolean match(char c);

}
