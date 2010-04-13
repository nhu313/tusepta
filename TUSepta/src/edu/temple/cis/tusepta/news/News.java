/**
 * 
 */
package edu.temple.cis.mysepta.news;

import java.sql.Time;
import java.util.Date;

/**
 * @author Yu Liang 
 *
 */
public class News {

	private Date date;
	private Time time;
	private String content;
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.content;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
