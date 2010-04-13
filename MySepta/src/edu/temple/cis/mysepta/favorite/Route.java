/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

/**
 * @author Yu Liang
 *
 */
public class Route {
	private int route_id;
	private String name;
	private String service_id;
	private boolean isFavorite;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the service_id
	 */
	public String getService_id() {
		return service_id;
	}

	/**
	 * @param serviceId the service_id to set
	 */
	public void setService_id(String serviceId) {
		service_id = serviceId;
	}

	/**
	 * @return the isFavorite
	 */
	public boolean isFavorite() {
		return isFavorite;
	}

	/**
	 * @param isFavorite the isFavorite to set
	 */
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	
}
