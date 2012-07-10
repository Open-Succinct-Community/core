/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author venky
 */
public class Network {
    public Network(){ 
        
    }
    
    final Map<Integer,Node> nodeMap = new HashMap<Integer, Node>();
    final Map<String,Edge> edgeMap = new HashMap<String, Edge>();

    public Node createNode(){
        Node node = new Node();
        synchronized (nodeMap){
            node.setId(nodeMap.size());
            nodeMap.put(node.getId(), node);
        }
        return node;
    }

    public Set<Integer> getNodes(){ 
        return nodeMap.keySet();
    }
    
    public Node getNode(int id){
        return nodeMap.get(id);
    }

    public Edge getEdge(int id1, int id2){
        return edgeMap.get(Edge.getKey(id1, id2));
    }
    
    public Edge createEdge(int id1 , int id2){
        String edgeKey = Edge.getKey(id1, id2);
        Edge e = edgeMap.get(edgeKey);
        if (e == null){
            synchronized(edgeMap){
                e = edgeMap.get(edgeKey);
                if (e == null){
                    e = new Edge();
                    e.connect(id1, id2);
                    edgeMap.put(edgeKey,e);
                    getNode(id1).addEdge(e);
                    getNode(id2).addEdge(e);
                }
            }
        }
        return e;
    }
    
    
    
}
