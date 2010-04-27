package edu.temple.cis.mysepta.myclass;

import edu.temple.cis.mysepta.data.MySeptaDataHelper;

public class Schedule {
	private long scheduleID;
	private long stopID;
	private float time;
	public Schedule(long scheduleID, long stopID, float time) {
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
	public float getTime() {
		return time;
	}
	
	public void setTime(float time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return MySeptaDataHelper.time2String(time);
	}

}
