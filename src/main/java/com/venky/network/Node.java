/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.network;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author venky
 */
public class Node {
    public Node(){
    }

    private Map<String,Object> attributes; 
    
    public final <V> void setAttribute(String name, V value){
        attributes.put(name, value);
    }
    @SuppressWarnings("unchecked")
	public final <V> V getAttribute(String name){
        return (V)attributes.get(name);
    }
    
    private Set<Edge> edges = new HashSet<Edge>();

    public Set<Edge> getEdges() {
        return edges;
    }
    
    protected void addEdge(Edge e){
        if (!e.getNodesConnected().contains(this.getId())){
            throw new RuntimeException("Edge " + e.toString() + " does not connect with node." + id );
        }
        edges.add(e);
    }
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
