package edu.temple.cis.mysepta.myclass;

public class FavStop {
	private long stopID;
	private String stopName;
	private String routeName;
	
	public FavStop(long stopID, String stopName, String routeName) {
		this.stopID = stopID;
		this.stopName = stopName;
		this.routeName = routeName;
	}
	
	public long getStopID() {
		return stopID;
	}
	public void setStopID(long stopID) {
		this.stopID = stopID;
	}
	public String getStopName() {
		return stopName;
	}
	public void setStopName(String stopName) {
		this.stopName = stopName;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	@Override
	public String toString() {
		return routeName + " (" + stopName + ")";
	}
}
