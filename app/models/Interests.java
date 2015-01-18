package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * Created by BlackMark on 18/01/2015.
 */
@Entity
public class Interests extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String m_interest;

	// Default constructor
	public Interests() {
		setInterest("");
	}

	// Constructor which sets interest
	public Interests(String interest) {
		setInterest(interest);
	}

	// Setter
	public void setInterest(String interest) {
		m_interest = interest;
	}

	// Getter
	public String getInterest() {
		return m_interest;
	}
}
