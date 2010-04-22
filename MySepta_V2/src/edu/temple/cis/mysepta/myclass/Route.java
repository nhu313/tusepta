package edu.temple.cis.mysepta.myclass;
public class Route {
	private long service_id;
	private long route_id;
	private String route;
	private String name;
	private String url;
		
	public Route(long serviceID, long routeId, String route, String name,
			String url) {
		service_id = serviceID;
		route_id = routeId;
		this.route = route;
		this.name = name;
		this.url = url;
	}
	
	public Route(){}
	
	public Route(int routeId, int serviceId, String route, String name, String url) {
		service_id = serviceId;
		route_id = routeId;
		this.route = route;
		this.name = name;
		this.url = url;
	}

	public long getService_id() {
		return service_id;
	}
	public void setService_id(int serviceId) {
		service_id = serviceId;
	}
	public long getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int routeId) {
		route_id = routeId;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String toString(){
		return route + " " + name;
	}
}