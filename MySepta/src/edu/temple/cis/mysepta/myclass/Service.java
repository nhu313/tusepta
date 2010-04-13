/**
 * 
 */
package edu.temple.cis.mysepta.myclass;

/**
 * @author Yu Liang
 *
 */
public class Service {

	private long id;
	private String shortName;
	private String longName;
	private String color;
	
	public Service(long id, String shortName, String longName, String color) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.color = color;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}
	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return longName;
	}
}
