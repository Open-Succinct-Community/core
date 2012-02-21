package com.venky.parse.composite;

import com.venky.parse.Rule;


public class Any extends AbstractCompositeRule {
	public Any(Rule... rules ){
		super(rules);
	}
	@Override
	public boolean match(String input, int offset) {
		for (int i = 0 ; i < rules.length ; i ++ ){
			if (rules[i].match(input, offset)){
				Element match = new Element(this);
				match.add(rules[i].getMatch());
				setMatch(match);
				return true; 
			}
		}
		return false;
	}

	public Object clone(){
		Any any = (Any)super.clone();
		for (int i = 0 ; i < rules.length ; i ++){
			any.rules[i] = (Rule)rules[i].clone();
		}
		
		return any;
	}
	
}
