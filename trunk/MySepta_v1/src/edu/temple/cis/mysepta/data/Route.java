package edu.temple.cis.mysepta.data;
public class Route {
	private int service_id;
	private int route_id;
	private String route;
	private String name;
	private String url;
	private boolean isFavorite;
		
	public Route(int serviceId, int routeId, String route, String name,
			String url) {
		service_id = serviceId;
		route_id = routeId;
		this.route = route;
		this.name = name;
		this.url = url;
	}
	
	public Route(){}
	
	public Route(int serviceId, int routeId, String route, String name,
			String url, boolean isFavorite) {
		service_id = serviceId;
		route_id = routeId;
		this.route = route;
		this.name = name;
		this.url = url;
		this.isFavorite = isFavorite;
	}

	public int getService_id() {
		return service_id;
	}
	public void setService_id(int serviceId) {
		service_id = serviceId;
	}
	public int getRoute_id() {
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
	public boolean isFavorite() {
		return isFavorite;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public String toString(){
		return route + " " + name;
	}
}
