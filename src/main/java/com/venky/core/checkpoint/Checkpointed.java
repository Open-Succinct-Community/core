package com.venky.core.checkpoint;

import java.util.Stack;

import com.venky.core.util.ObjectUtil;

public class Checkpointed<M> implements Checkpointable<M>{
	private Stack<Checkpoint<M>> checkpoints = new Stack<Checkpoint<M>>();

	private M initial ;
	public Checkpointed(M m){
		initial = m;
	}

	public Checkpoint<M> createCheckpoint(){
		Checkpoint<M> cp = new Checkpoint<M>(ObjectUtil.clone(getCurrentValue()));
		checkpoints.push(cp);
		return cp;
	}
	
	
	public M getCurrentValue() {
		if (checkpoints.isEmpty()){
			return initial;
		}else {
			return checkpoints.peek().getValue();
		}
	}
	
	public void setValue(M value){ 
		if (checkpoints.isEmpty()){
			this.initial = value;
		}else {
			Checkpoint<M> cp = this.checkpoints.peek();
			cp.setValue(value);
		}
	}

	public void rollback(){
		rollback(null);
	}
	/**
	 * Rollback all check points including the passed check point. If the passed check point doesnot exist, then rollback to the initial state and throw {@link InvalidCheckpointException}. 
	 * @param cp
	 * @throws InvalidCheckpointException if the passed check point is not a valid checkpoint.  
	 */
	public void rollback(Checkpoint<M> cp){
		if (cp == null){
			checkpoints.clear();
		}else {
			while (!checkpoints.isEmpty()){
				Checkpoint<M> last = checkpoints.pop();
				if (last == cp){
					return;
				}
			}
			throw new InvalidCheckpointException();// Implicitly rollback has happened.
		}
	}
	
	/** 
	 * Commits the currentValue to the initial value passed while constructing this. If the initialValue was instanceof {@link Mergeable}, 
	 * initialValue.merge(currentValue) is called and all check points cleared. 
	 * 
	 */
	public void commit(){
		commit(null);
	}
	
	@SuppressWarnings("unchecked")
	public void commit(Checkpoint<M> cp){
		M finalValue = getCurrentValue();
		rollback(cp);
		M initialValue = getCurrentValue();
		if (initialValue != null && initialValue instanceof Mergeable){
			((Mergeable<M>)initialValue).merge(finalValue);
		}else {
			setValue(finalValue);
		}
	}

}
