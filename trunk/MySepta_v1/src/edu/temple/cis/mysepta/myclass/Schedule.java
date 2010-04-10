package edu.temple.cis.mysepta.myclass;

public class Schedule {
	private long scheduleID;
	private long stopID;
	private double time;
	public Schedule(long scheduleID, long stopID, double time) {
		super();
		this.scheduleID = scheduleID;
		this.stopID = stopID;
		this.time = time;
	}
	public long getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(long scheduleID) {
		this.scheduleID = scheduleID;
	}
	public long getStopID() {
		return stopID;
	}
	public void setStopID(long stopID) {
		this.stopID = stopID;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return time + "";
	}

}
