package edu.temple.cis.mysepta.myclass;

public class DayOfService {
	private long dayID;
	private long routeID;
	private String day;

	public DayOfService(long dayID, long l, String day) {
		this.dayID = dayID;
		this.routeID = l;
		this.day = day;
	}
	public long getDayID() {
		return dayID;
	}
	public void setDayID(long dayID) {
		this.dayID = dayID;
	}
	public long getRouteID() {
		return routeID;
	}
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	@Override
	public String toString() {
		return  day;
	}
	
	
}
