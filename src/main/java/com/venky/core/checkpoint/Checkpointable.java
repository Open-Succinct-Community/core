package com.venky.core.checkpoint;



public interface Checkpointable<M> {
	public M getCurrentValue();
	public Checkpoint<M> createCheckpoint();
}
