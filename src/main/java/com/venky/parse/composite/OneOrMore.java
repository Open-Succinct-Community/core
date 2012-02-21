package com.venky.parse.composite;

import com.venky.parse.Rule;

public class OneOrMore extends Multiple{

	public OneOrMore(Rule ruleTemplate) {
		super(ruleTemplate,1);
	}

}
