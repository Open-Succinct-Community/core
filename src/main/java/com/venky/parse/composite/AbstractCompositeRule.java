package com.venky.parse.composite;

import com.venky.parse.Rule;

public abstract class AbstractCompositeRule extends Rule {
	protected final Rule[] rules ;
	protected AbstractCompositeRule(Rule... rules ){
		this.rules = rules;
	}
	
	public Object clone(){
		AbstractCompositeRule cr = (AbstractCompositeRule)super.clone();
		for (int i = 0 ; i < rules.length ; i ++){
			cr.rules[i] = (Rule)rules[i].clone();
		}
		
		return cr;
	}
}
