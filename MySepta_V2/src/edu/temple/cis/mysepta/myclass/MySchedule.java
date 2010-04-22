package edu.temple.cis.mysepta.myclass;

public class MySchedule {
	private String route;
	private String stop;
	private float time;
	
	public MySchedule(String route, String stop, float time) {
		this.route = route;
		this.stop = stop;
		this.time = time;
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
	public void setTime(float time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return route + "(" + stop + ") " + time;
	}
}
