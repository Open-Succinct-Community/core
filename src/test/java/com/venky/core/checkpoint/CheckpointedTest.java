package com.venky.core.checkpoint;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.venky.core.util.Bucket;

public class CheckpointedTest {

	@Test
	public void test() {
		Checkpointed<MergeableMap<String, Bucket>> map = new Checkpointed<MergeableMap<String,Bucket>>(new MergeableMap<String, Bucket>());
		
		map.createCheckpoint();
		map.getCurrentValue().put("counter1", new Bucket(1));
		map.getCurrentValue().put("counter2", new Bucket(2));
		
		Checkpoint<MergeableMap<String,Bucket>> cp = map.createCheckpoint();
		map.getCurrentValue().get("counter2").increment();//3
		map.rollback(cp);
		
		map.commit();
		assertEquals(1,map.getCurrentValue().get("counter1").intValue());
		assertEquals(2,map.getCurrentValue().get("counter2").intValue());
	}
	
	@Test
	public void testBucket(){
		Checkpointed<Bucket> cb = new Checkpointed<Bucket>(new Bucket(5));
		cb.getCurrentValue().increment();
		cb.rollback();
		
		assertEquals(6, cb.getCurrentValue().intValue()); // As no check point set.
		
		cb.createCheckpoint();
		cb.getCurrentValue().increment(); // 7 
		
		Checkpoint<Bucket> cp = cb.createCheckpoint();
		cb.getCurrentValue().decrement(7); // 0
		assertEquals(0, cb.getCurrentValue().intValue());
		cb.rollback(cp);
		assertEquals(7, cb.getCurrentValue().intValue());
		
		
		
		
		
	}
}
