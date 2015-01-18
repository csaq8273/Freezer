package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * Created by BlackMark on 18/01/2015.
 */
@Entity
public class Location extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	String m_name;
	private double m_latitude;
	private double m_longitude;

	// Default constructor which sets the location to ( 0, 0 )
	public Location() {
		setLocation("", 0, 0);
	}

	// Constructor which sets the location to the specified parameters
	public Location(String name, double latitude, double longitude) {
		setLocation(name, latitude, longitude);
	}

	// Set Location data
	public void setLocation(String name, double latitude, double longitude) {
		m_name = name;
		m_latitude = latitude;
		m_longitude = longitude;
	}

	// Get Location data
	public String getName() {
		return m_name;
	}

	public double getLatitude() {
		return m_latitude;
	}

	public double getLongitude() {
		return m_longitude;
	}
}
