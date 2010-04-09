package edu.temple.cis.mysepta.data;

public class DayOfService {
	private long dayID;
	private int routeID;
	private String day;
	
	public DayOfService(long dayID, int routeID, String day) {
		this.dayID = dayID;
		this.routeID = routeID;
		this.day = day;
	}
	public long getDayID() {
		return dayID;
	}
	public void setDayID(long dayID) {
		this.dayID = dayID;
	}
	public int getRouteID() {
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
}
