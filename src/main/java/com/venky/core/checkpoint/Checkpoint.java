package com.venky.core.checkpoint;

public class Checkpoint<M> {
	private M checkpointedValue;  
	public Checkpoint(M m){
		this.checkpointedValue = m;
	}
	public M getValue(){
		return checkpointedValue;
	}
	public void setValue(M value){
		this.checkpointedValue = value;
	}
}
