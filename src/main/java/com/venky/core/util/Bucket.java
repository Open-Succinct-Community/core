/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.util;

import java.io.Serializable;

import com.venky.core.checkpoint.Mergeable;

/**
 *
 * @author venky
 */
public class Bucket implements Cloneable, Serializable, Mergeable<Bucket>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3777328906346580670L;
	private double counter;
	
    public Bucket(){
        this(0.0);
    }
    public Bucket(double start){
        this.counter = start;
    }
    public void increment(){
        increment(1.0);
    }
    public void decrement(){ 
        increment(-1.0);
    }
    public void decrement(double by){
        increment(-1.0 * by);
    }
    public final void increment(double by){
        synchronized (this){
            counter += by; 
        }
    }
    public double value() {
        return counter;
    }
    public int intValue(){
    	return (int)counter;
    }

    @Override
    public String toString() {
        return "value: " + counter;
    }
    
    @Override
    public Bucket clone() {
        Object clone; 
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
        return (Bucket)clone;
    }
    
	public void merge(Bucket another) {
		this.counter = another.counter;
	}
	
           
}
