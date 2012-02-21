package com.venky.parse.composite;

import com.venky.parse.Rule;

public class ZeroOrMore extends Multiple{

	public ZeroOrMore(Rule ruleTemplate) {
		super(ruleTemplate,0);
	}

}
