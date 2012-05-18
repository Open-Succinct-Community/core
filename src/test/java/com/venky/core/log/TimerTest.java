package com.venky.core.log;

import junit.framework.Assert;

import org.junit.Test;

import com.venky.core.log.TimerStatistics.Timer;

public class TimerTest {

	@Test
	public void test() {
		Timer t = Timer.startTimer();
		longMethod(5,5);
		t.stop();
		TimerStatistics.dumpStatistics();
		Assert.assertTrue(TimerStatistics.getTimerStack().isEmpty());
		Assert.assertTrue(TimerStatistics.getTimerStatistics().isEmpty());
	}

	private void longMethod(long seconds,int maxDepth){
		Timer t = Timer.startTimer(); 
		try {
			if (maxDepth > 0){
				Timer sleep = Timer.startTimer();
				try {
					longMethod(seconds, maxDepth - 1);
				}finally{
					sleep.stop();
				}
			}else {
				Timer sleep = Timer.startTimer();
				try {
					Thread.sleep(seconds * 1000);
				}catch(InterruptedException ex){
					//
				}finally {
					sleep.stop();
				}
				
			}
		}finally {
			t.stop();
		}
	}
	
}
