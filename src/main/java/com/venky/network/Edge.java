/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.network;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author venky
 */
public class Edge {
    private Set<Integer> nodesConnected = new TreeSet<Integer>();
    private Map<String,Object> attributes = new TreeMap<String,Object>();
    
    public Edge(){
    }
    
    public static String getKey(int node1, int node2){
        Edge e = new Edge();
        e.connect(node1, node2);
        return e.toString();
    }
    
    public void connect(int node1,int node2){
        nodesConnected.add(node1);
        nodesConnected.add(node2);
    }
    public Set<Integer> getNodesConnected() {
        return nodesConnected;
    }
    
    public final <V> void setAttribute(String name, V value){
        attributes.put(name, value);
    }
    public final Object getAttribute(String name){
        return attributes.get(name);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (this.nodesConnected != other.nodesConnected && (this.nodesConnected == null || !this.nodesConnected.equals(other.nodesConnected))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = toString().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return this.nodesConnected.toString();
    }
    
                  
    public int getConnectedNode(int n1){
        assert nodesConnected.size() == 2 ;
        if (!nodesConnected.contains(n1)){
            throw new RuntimeException("Edge doesnot connect Node " + n1);
        }
        Iterator<Integer> i = nodesConnected.iterator();
        int other = -1;
        while (i.hasNext()){
            other = i.next() ; 
            if (other != n1){
                break;
            }
        }
        assert(other != -1);
        return other;
    }

}
