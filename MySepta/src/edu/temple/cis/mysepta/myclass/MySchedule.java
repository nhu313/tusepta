package edu.temple.cis.mysepta.myclass;

import edu.temple.cis.mysepta.data.MySeptaDataHelper;

public class MySchedule {
	private String route;
	private String stop;
	private float time;
	private long serviceId;
	
	public MySchedule(long serviceId, String route, String stop, float time) {
		this.serviceId = serviceId;
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
	
	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		return route + " (" + stop + ")";
	}
}
