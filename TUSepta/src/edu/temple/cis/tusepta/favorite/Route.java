/**
 * 
 */
package edu.temple.cis.tusepta.favorite;

/**
 * @author Yu Liang
 *
 */
public class Route {
	private int route_id;
	private String route_short_name;
	private String route_long_name;
	private int route_type;
	private String route_url;
	/**
	 * @return the route_id
	 */
	public int getRoute_id() {
		return route_id;
	}
	/**
	 * @param routeId the route_id to set
	 */
	public void setRoute_id(int routeId) {
		route_id = routeId;
	}
	/**
	 * @return the route_short_name
	 */
	public String getRoute_short_name() {
		return route_short_name;
	}
	/**
	 * @param routeShortName the route_short_name to set
	 */
	public void setRoute_short_name(String routeShortName) {
		route_short_name = routeShortName;
	}
	/**
	 * @return the route_long_name
	 */
	public String getRoute_long_name() {
		return route_long_name;
	}
	/**
	 * @param routeLongName the route_long_name to set
	 */
	public void setRoute_long_name(String routeLongName) {
		route_long_name = routeLongName;
	}
	/**
	 * @return the route_type
	 */
	public int getRoute_type() {
		return route_type;
	}
	/**
	 * @param routeType the route_type to set
	 */
	public void setRoute_type(int routeType) {
		route_type = routeType;
	}
	/**
	 * @return the route_url
	 */
	public String getRoute_url() {
		return route_url;
	}
	/**
	 * @param routeUrl the route_url to set
	 */
	public void setRoute_url(String routeUrl) {
		route_url = routeUrl;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.route_short_name + " " + this.route_long_name;
	}
	
	
}
