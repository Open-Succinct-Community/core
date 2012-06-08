package com.venky.core.checkpoint;

public class InvalidCheckpointException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5556403130838767932L;
	public InvalidCheckpointException(){
		super();
	}
	public InvalidCheckpointException(String message){
		super(message);
	}

}
