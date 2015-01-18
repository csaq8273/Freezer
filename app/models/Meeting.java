package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Meeting extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Skier m_partner;
	private Location m_location;
	private Date m_datetime;

	// Default constructor
	public Meeting() {
		setMeetingInfo(null, null, null);
	}

	// Setter
	public void setMeetingInfo(Skier partner, Location location, Date datetime) {
		m_partner = partner;
		m_location = location;
		m_datetime = datetime;
	}

	// Getter
	public Skier getPartner() {
		return m_partner;
	}

	public Location getLocation() {
		return m_location;
	}

	public Date getDateTime() {
		return m_datetime;
	}
}