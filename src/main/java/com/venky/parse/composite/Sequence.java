package com.venky.parse.composite;

import com.venky.parse.Rule;

public class Sequence extends AbstractCompositeRule{
	public Sequence(Rule... rules ){
		super(rules);
	}
	@Override
	public boolean match(String input, int offset) {
		boolean ret = true; 
		Element match = new Element(this);
		String tmpInput = input.substring(offset);
		
		for (int i = 0 ; i < rules.length ; i ++ ){
			if (!rules[i].match(tmpInput,match.length())){
				return false;
			}
			match.add(rules[i].getMatch());
		}
		setMatch(match);
		return ret;
	}
	
	
}
