package com.venky.parse.character;



public class NotCharRange extends AbstractSingleCharacterRule{
	private char low;
	private char hi;
	
	public NotCharRange(char low, char hi) {
		this.low = low;
		this.hi = hi;
	}

	@Override
	protected boolean match(char c) {
		if (c < low || c > hi){
			return true;
		}else {
			return false;
		}
	}

}
