package com.venky.core.checkpoint;


public interface Mergeable<T> extends Cloneable<T>{
	public void merge(T another);
	
	public static class MergeFailedException extends RuntimeException {
		private static final long serialVersionUID = 5861184850899947849L;
		public MergeFailedException(){
			super();
		}
		public MergeFailedException(String message){
			super(message);
		}
	}
}
