package com.venky.parse.composite;

import com.venky.parse.Rule;


public class Multiple extends Rule{
	private Rule ruleTemplate = null; 
	private int minOccurs = 0, maxOccurs = Integer.MAX_VALUE;
	public Multiple(Rule ruleTemplate){
		this(ruleTemplate,1);
	}
	public Multiple(Rule ruleTemplate, int minOccurs){
		this(ruleTemplate,minOccurs,Integer.MAX_VALUE);
	}
	public Multiple(Rule ruleTemplate, int minOccurs, int maxOccurs){
		assert minOccurs >= 0; 
		
		if (minOccurs > maxOccurs) {
			throw new IllegalArgumentException("minOccurs must be less than or equal to maxOccurs");
		}
		
		this.ruleTemplate = ruleTemplate;
		this.minOccurs = minOccurs;
		this.maxOccurs = maxOccurs;
	}
	@Override
	public boolean match(String input, int offset) {
		boolean ret = (minOccurs == 0 ? true : false); 
		
		String tmpInput = input.substring(offset);
		
		Element match = new Element(this);
		
		while (maxOccurs > 0 && match.length() < tmpInput.length()){
			Rule clone = (Rule)ruleTemplate.clone();
			if (clone.match(tmpInput, match.length())) {
				Element tmp = clone.getMatch();
				match.add(tmp);
				int numMatches = match.numChildren();
				if (!ret && numMatches >= minOccurs) {
					ret = true;
				}
				if (numMatches >= maxOccurs){
					break;
				}
			}else {
				break;
			}
			
		}
		if (ret){
			setMatch(match);
		}

		return ret;
	}

}
