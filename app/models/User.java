package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class User extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String m_username;
	private String m_password; // Temporary, has to be hashed / encrypted at
								// some point
	private String m_firstname;
	private String m_lastname;
	private Date m_birthday;

	// Default constructor without setting any variables
	public User() {
		setUserData(null, null, null, null, null);
	}

	// Constructor which sets basic user information
	public User(String username, String password, String firstname,
			String lastname) {
		setUserData(username, password, firstname, lastname, null);
	}

	// Constructor which sets all user information
	public User(String username, String password, String firstname,
			String lastname, Date birthday) {
		setUserData(username, password, firstname, lastname, birthday);
	}

	// Function to assign user data
	public void setUserData(String username, String password, String firstname,
			String lastname, Date birthday) {
		m_username = username;
		m_password = password;
		m_firstname = firstname;
		m_lastname = lastname;
		m_birthday = birthday;
	}

	// Bunch of getters to get all the user specific data
	public String getUsername() {
		return m_username;
	}

	public String getPassword() {
		return m_password;
	}

	public String getFirstname() {
		return m_firstname;
	}

	public String getLastname() {
		return m_lastname;
	}

	public Date getBirthday() {
		return m_birthday;
	}
}
