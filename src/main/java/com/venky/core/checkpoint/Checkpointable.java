package com.venky.core.checkpoint;

import java.io.Serializable;


public interface Checkpointable<M extends Serializable> {
	public M getCurrentValue();
	public Checkpoint<M> createCheckpoint();
}
