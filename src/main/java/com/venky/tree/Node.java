package com.venky.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<M> {
	
	public Node(M data){
		this.data = data;
		this.parent = null;
	}
	private M data;
	private Node<M> parent; 
	
	private List<Node<M>> children = new ArrayList<Node<M>>();
	public List<Node<M>> getChildren(){
		return children;
	}
	
	public Node<M> find(M data){
		if (data.equals(data)){
			return this;
		}
		for (Node<M> child:getChildren()){
			Node<M> found = child.find(data);
			if (found != null){
				return found;
			}
		}
		return null;
	}
	
	
	public Node<M> add(M childChildData){
		Node<M> child = new Node<M>(childChildData);
		return add(child);
		
	}
	
	public Node<M> add(Node<M> child){
		child.parent = this;
		children.add(child);
		return child;
	}
	
	public Node<M> getParent(){
		return parent;
	}
	
	public boolean isRoot(){
		return parent == null;
	}
	
	public boolean isLeaf(){
		return children.isEmpty();
	}
	
	public M data(){
		return this.data;
	}
	
	public List<M> getLeaves(){
		List<M> leaves = new ArrayList<M>();
		loadLeaves(leaves);
		return leaves;
	}
	
	private void loadLeaves(List<M> leafData){
		if (isLeaf()){
			leafData.add(data());
		}
		for (Node<M> child:getChildren()){
			child.loadLeaves(leafData);
		}
		return ;
	}
	
}
