package com.venky.parse.character;




public class CharRange extends AbstractSingleCharacterRule{
	private char low ; 
	private char hi;
	public CharRange(char low,char hi){
		super();
		this.low = low; 
		this.hi = hi;
	}


	@Override
	protected boolean match(char c) {
		if (low <= c && c <= hi){
			return true;
		}
		return false;
	}

}
