package com.venky.parse.composite;

import com.venky.parse.Rule;

public class CharSequence extends Rule {
	private final String start; 
	public CharSequence(String start){
		this.start = start;
	}
	
	@Override
	public boolean match(String input, int offset) {
		int x = offset;
		int y = 0 ;
		if (offset + start.length() > input.length()){
			return false;
		}
		
		while (x < input.length() && y < start.length()){
			char cx = input.charAt(x); 
			char cy = start.charAt(y);
			if (cx != cy){
				break;
			}
			x++;
			y++;
		}
		if (y < start.length()){
			return false;
		}
		
		setMatch(new Element(this,start));
		
		return true;
	}
	
	
}
