package edu.temple.cis.mysepta.myclass;

public class Stop {

	private long stopID;
	private long dayOfServiceID;
	private String stopName;
	private int isFavorite;
	
	private String extRouteName;
	
	public Stop(long stopID, long dayOfServiceID, String stopName, int isFavorite) {
		this.stopID = stopID;
		this.dayOfServiceID = dayOfServiceID;
		this.stopName = stopName;
		this.isFavorite = isFavorite;
	}
	public long getStopID() {
		return stopID;
	}
	public void setStopID(long stopID) {
		this.stopID = stopID;
	}
	public long getDayOfServiceID() {
		return dayOfServiceID;
	}
	public void setDayOfServiceID(long dayOfServiceID) {
		this.dayOfServiceID = dayOfServiceID;
	}
	public String getStopName() {
		return stopName;
	}
	public void setStopName(String stopName) {
		this.stopName = stopName;
	}
	public int getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}
	public String getExtRouteName() {
		return extRouteName;
	}
	public void setExtRouteName(String extRouteName) {
		this.extRouteName = extRouteName;
	}
	@Override
	public String toString() {
		return stopName;
	}
}
