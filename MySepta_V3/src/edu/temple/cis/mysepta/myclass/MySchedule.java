package edu.temple.cis.mysepta.myclass;

import edu.temple.cis.mysepta.data.MySeptaDataHelper;

public class MySchedule {
	private String route;
	private String stop;
	private float time;
	
	public MySchedule(String route, String stop, float time) {
		this.route = route;
		this.stop = stop;
		this.time = time;
	}
	
	public MySchedule(String route, String stop) {
		this.route = route;
		this.stop = stop;
	}

	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getStop() {
		return stop;
	}
	public void setStop(String stop) {
		this.stop = stop;
	}
	public float getTime() {
		return time;
	}
	
	public String getTimeString(){
		return MySeptaDataHelper.time2String(time);
	}
	
	public void setTime(float time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return route + " (" + stop + ")";
	}
}
