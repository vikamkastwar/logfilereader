package com.logFileReader.model;

import java.util.Objects;

/**
 * <pre>
 * 	Model class having attribute based on sample records 
 *  {id="scsmbstgrb", state="FINISHED", "timestamp":1491377495213, "host":12345, type="APPLICATION_LOG"}
 * </pre>.
 *
 * @author  : Vikram Kastwar
 * @version : 1.0
 */

public class Event {
	
	private String id;
	private String state;
	private double timestamp;
	private String type;
	private String host;
	private double duration;
	private boolean isLongDuration;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return the timestamp
	 */
	public double getTimestamp() {
		return timestamp;
	}
	
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	
	/**
	 * Checking whether durations is more than 4 ms or not
	 * 
	 * @return the isLongDuration
	 */
	public boolean isLongDuration() {
		isLongDuration = duration > 4 ? true : false;
		return isLongDuration;
	}
	
	/**
	 * @param isLongDuration the isLongDuration to set
	 */
	public void setLongDuration(boolean isLongDuration) {
		this.isLongDuration = isLongDuration;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Event other = (Event) obj;
		return Objects.equals(id, other.id);
	}
}
