package com.venky.core.log;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import com.venky.core.string.StringUtil;
import com.venky.core.util.Bucket;


public class TimerStatistics {
	private final String context;
	private final Bucket callCount ;
	private final Bucket elapsedTime; 
	private TimerStatistics(String context){
		this.context = context;
		this.callCount = new Bucket();
		this.elapsedTime = new Bucket();
	}
	
	private static ThreadLocal<Map<String,TimerStatistics>> timerStatisticsInThread = new ThreadLocal<Map<String,TimerStatistics>>();
	static Map<String,TimerStatistics> getTimerStatistics(){
		Map<String,TimerStatistics> timerStatistics =  timerStatisticsInThread.get();
		if (timerStatistics == null){
			timerStatistics = new HashMap<String, TimerStatistics>();
			timerStatisticsInThread.set(timerStatistics);
		}
		return timerStatistics;
	}
	
	private static ThreadLocal<Stack> timerStackInThread = new ThreadLocal<Stack>();
	static Stack<Timer> getTimerStack(){
		Stack<Timer> timerStack = timerStackInThread.get();
		if (timerStack == null){
			timerStack = new Stack<TimerStatistics.Timer>();
			timerStackInThread.set(timerStack);
		}
		return timerStack;
	}
	
	public static class Timer {
		public static Timer startTimer(){
			return startTimer(null);
		}
		public static Timer startTimer(String ctx){
			return startTimer(ctx,false);
		}
		
		private static StackTraceElement getCaller(){
			StackTraceElement[] elements = new Exception().getStackTrace();
			for (StackTraceElement element: elements){
				if (!element.getClassName().equals(Timer.class.getName())){
					return element;
				}
			}
			throw new RuntimeException("Timer class cannot be timed!");
		}
		public static Timer startTimer(String ctx,boolean additive){
			String context = getCaller() + (ctx == null ? "" : ":"+ StringUtil.valueOf(ctx));
			Map<String,TimerStatistics> timerStatistics = getTimerStatistics();
			
			TimerStatistics ts = timerStatistics.get(context);
			if (ts == null){
				ts = new TimerStatistics(context);
				timerStatistics.put(context, ts);
			}
			
			Timer timer = new Timer(context,additive);
			timer.start();
			return timer;
		}

		private final String key; 
		private final boolean additive;
		public Timer(String key,boolean additive){
			this.key = key;
			this.additive = additive;
		}
		
		private Long start = null;
		private void start(){
			start = System.currentTimeMillis();
			Stack<Timer> timerStack = getTimerStack();
			if (!timerStack.isEmpty()){
				Timer previous = timerStack.peek();
				if (!previous.additive){
					previous.suspend();
				}
			}
			timerStack.push(this);
		}
		
		private boolean isPresentInTimerStack(){
			List<Timer> timerStack = getTimerStack();
			for (int i = timerStack.size()-1 ;i >=0 ; i --) {
				if (timerStack.get(i) == this){
					return true;
				}
			}
			return false;
		}
		public void stop(){
			TimerStatistics statistics = suspend();
			statistics.callCount.increment();
			Stack<Timer> timerStack = getTimerStack();
			Timer last  = null;

			if (isPresentInTimerStack()){
				do {
					last = timerStack.pop();
				}while (last != this);
			}
			
			if (this == last && !timerStack.isEmpty()){
				Timer previous = timerStack.peek();
				if (!previous.additive){
					previous.start();
				}
			}else if (!timerStack.isEmpty()){
				System.out.println("Clearing Stack!!");
				Iterator<Timer> i = timerStack.iterator();
				while (i.hasNext()){
					Timer timer = i.next();
					System.out.println(timer.key);
					i.remove();
				}
			}
		}
		private TimerStatistics suspend(){
			long now = System.currentTimeMillis();
			TimerStatistics statistics = getTimerStatistics().get(key);
			if (start != null){
				statistics.elapsedTime.increment(now - start);
				start = null;
			}
			return statistics;
		}
	}
	
	
	public String getStatistics(){
		StringBuilder b = new StringBuilder();
		if (callCount.intValue() > 0){
			b.append(context).append("|").append(callCount.intValue()).append("|").append(elapsedTime.value()).append("|").append(elapsedTime.value()/callCount.intValue());
		}
		return b.toString();
	}
	
	
	public static void dumpStatistics(){
		Map<String,TimerStatistics> timers = timerStatisticsInThread.get();
		Iterator<String> i = timers.keySet().iterator();
		SortedSet<TimerStatistics> out = new TreeSet<TimerStatistics>(new Comparator<TimerStatistics>() {

			public int compare(TimerStatistics o1, TimerStatistics o2) {
				return o2.elapsedTime.intValue() - o1.elapsedTime.intValue();
			}
		});
		while (i.hasNext()){
			String e = i.next();
			TimerStatistics t = timers.get(e);
			if (t.callCount.intValue() > 0 ){
				out.add(t);
			}
			i.remove();
		}
		for (TimerStatistics s:out){
			System.out.println(s.getStatistics());
		}
	}
	
}
