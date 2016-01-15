package communication;

import java.util.concurrent.TimeUnit;

public class Timer{
	public boolean done = false;
	private long startTime;
	
	public static Timer start(){
		return new Timer();
	}
	
	public Timer(){
		reset();
	}
	
	public Timer reset(){
		startTime = System.currentTimeMillis();
		return this;
	}
	
	public long time(){
		long endTime = System.currentTimeMillis();
		long elapsedTime = (endTime-startTime)/1000;
//		System.out.println("Elapsed Time: " + elapsedTime);
		return elapsedTime;
	}
	
	public long time(TimeUnit unit){
		long time = unit.convert(time(), TimeUnit.SECONDS);
		System.out.println(time);
		return time;
	}
	
}
