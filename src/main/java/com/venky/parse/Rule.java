package com.venky.parse;

import java.util.ArrayList;
import java.util.List;



public abstract class Rule implements Cloneable{
	public Rule(){
		
	}
	
	private Element match = null; 
	
	
	public Element getMatch() {
		return match;
	}
	public void setMatch(Element match) {
		this.match = match;
	}
	public Rule createClone(){
		return (Rule)clone();
	}
	public Object clone() {
		Rule c = null;
		try {
			assert match == null;
			c = (Rule)super.clone();
			
		} catch (CloneNotSupportedException e) {
			//
			throw new RuntimeException(e);
		}
		return c;
	}
	
	public boolean match(String input){
		return match(input,0);
	}
	public abstract boolean match(String input , int offset);
	
	public static class Element{
		private StringBuilder text = new StringBuilder(); 
		private Rule rule;
		private List<Element> children = new ArrayList<Rule.Element>();
		
		public Element(Rule rule){
			this(rule,null);
		}
		public Element(Rule rule, String text){
			this.rule = rule;
			if (text != null){
				this.text.append(text);
			}
		}
		public String getText() {
			return text.toString();
		}
		public Rule getRule() {
			return rule;
		}
		
		public List<Element> getChildren() {
			return children;
		}
		
		public int numChildren(){
			return children.size();
		}
		public void add(Element child){
			children.add(child);
			text.append(child.getText());
		}
		
		public int length(){ 
			return text.length();
		}
		
		public void walk(ElementVisitor visitor){
			walk(visitor,0);
		}
		
		protected void walk(ElementVisitor visitor, int level){
			visitor.visit(this,level);
			for (Element child: getChildren()){
				child.walk(visitor,level+1);
			}
		}
	}
	
	public static interface ElementVisitor {
		public void visit(Element e,int level);
	}
}
