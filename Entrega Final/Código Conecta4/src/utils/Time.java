package utils;

public class Time 
{
	private int minutes;
	private int sec;
	private boolean stop;
	private Time initTime;
	
	public Time(int min, int sec) {
		this.minutes = min;
		this.sec = sec;
		this.stop = false;
		
		initTime = new Time();
		initTime.minutes = min;
		initTime.sec = sec;
		initTime.stop = false;
	}
	
	public Time() {
		minutes = 0;
		sec = 0;
		stop = false;
	}
	
	public int getMinutes() {
		return minutes;
	}

	public int getSec() {
		return sec;
	}
	
	public void diminish()
	{
		if(sec > 0) sec--;
		else if(sec == 0 && minutes > 0) {
			sec = 59;
			minutes--;
		}
		if(sec == 0 && minutes == 0)
			stop = true;		
	}
	
	public void increase() {
		if (sec < 59)
			sec++;
		else {
			sec = 0;
			minutes++;
		}
	}
	
	public boolean stopped() {
		return stop;
	}
	
	public void restart() {
		minutes = initTime.minutes;
		sec = initTime.sec;
		stop = false;
	}
}
