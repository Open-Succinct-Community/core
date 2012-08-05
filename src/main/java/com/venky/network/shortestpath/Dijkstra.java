/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.network.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.venky.network.Edge;
import com.venky.network.Network;
import com.venky.network.Node;

/**
 *
 * @author venky
 */
public class Dijkstra extends Network{
    public final String LENGTH = "length";
    public Path shortestPath(int from,int to){
        final Map<Integer,Integer> distanceHash = new HashMap<Integer, Integer>();
        final Map<Integer,Integer> previousHash = new HashMap<Integer, Integer>();
        
        List<Integer> path = new ArrayList<Integer>();
        //Node source = getNode(from);
        for (int nodeId :getNodes()){
            distanceHash.put(nodeId, Integer.MAX_VALUE);
            previousHash.put(nodeId, null);
        }
        distanceHash.put(from, 0);
        PriorityQueue<Integer> q = new PriorityQueue<Integer>(getNodes().size(),new Comparator<Integer>(){

            public int compare(Integer o1, Integer o2) {
                int ret = distanceHash.get(o1) - distanceHash.get(o2);
                if (ret == 0){
                    ret = o1  - o2; 
                }
                return ret;
            }
            
        });
        q.addAll(getNodes());
        
        while (!q.isEmpty()){
            Integer u = q.poll();
            if (distanceHash.get(u) == Integer.MAX_VALUE){
                break;
            }
            Node n = getNode(u);
            for (Edge e : n.getEdges()){
                Integer v = e.getConnectedNode(u);
                int edge_length = (Integer)e.getAttribute(LENGTH);
                int alt = distanceHash.get(u) + edge_length;
                if (distanceHash.get(v) > alt){
                    q.remove(v);
                    previousHash.put(v,u);
                    distanceHash.put(v,alt);
                    q.offer(v);
                }
            }
            
        }
        if (distanceHash.get(to) == Integer.MAX_VALUE) {
            throw new RuntimeException("Cannot reach destination from source");
        }
        
        int curr = to; 
        while (curr != from ){
            path.add(curr);
            curr = previousHash.get(curr);
        }
        path.add(from);
        Collections.reverse(path);
        
        return new Path(path);
    }
    
    
    public class Path { 
        List<Integer> nodeSequence =new ArrayList<Integer>();
        List<Edge> edgeSequence = new ArrayList<Edge>(); 
        int length = 0;
        public Path(List<Integer> nodeList){
            this.nodeSequence.addAll(nodeList);
            
            Iterator<Integer> nodeIterator = nodeSequence.iterator(); 
            int current = nodeIterator.next(); 
            while (nodeIterator.hasNext()){
                int next = nodeIterator.next(); 
                Edge e = getEdge(current, next);
                edgeSequence.add(e);
                int edgeLength = (Integer)e.getAttribute(LENGTH);
                length += edgeLength;
                current = next;
            }
        }
        
        public int getLength(){ 
            return length;
        }

        public List<Edge> getEdgeSequence() {
            return edgeSequence;
        }

        public List<Integer> getNodeSequence() {
            return nodeSequence;
        }
        
    }
}
