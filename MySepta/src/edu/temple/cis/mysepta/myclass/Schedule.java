package edu.temple.cis.mysepta.myclass;

import java.io.Serializable;



public class Schedule implements Serializable{
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
		String ret = "";
		if (time >= 12.0){
			if (time >= 24.0){
				ret = round2(time - 12) + " AM";
			} else if (time < 13.0){
				ret = round2(time) + " PM";
			} else {
				ret = round2(time - 12) + " PM";
			}
			} else {
			ret = round2(time) + " AM";
		    }
		    return ret;
		
		
	}
	public static float round2(double t){
		return (float) (Math.round(100.0 * t)/100.0);
	}
	
 }


