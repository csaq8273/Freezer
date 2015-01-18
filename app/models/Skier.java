package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Skier extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private double m_credit;
	private Location m_location;
	private User m_user;
	private List<Interests> m_interests;

	// Default constructor
	public Skier() {
		setSkierData(0, null, null);
	}

	public Skier(double credit, Location location, User user) {
		setSkierData(credit, location, user);
	}

	// Setter
	public void setSkierData(double credit, Location location, User user) {
		m_credit = credit;
		m_location = location;
		m_user = user;
	}

	public void setInterests(List<Interests> interests) {
		m_interests = interests;
	}

	// Getter
	public double getCredit() {
		return m_credit;
	}

	public Location getLocation() {
		return m_location;
	}
}