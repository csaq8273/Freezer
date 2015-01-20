package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class Skier extends Model{

    @Id
    private final int id;

    private final String username;
    private final String password;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private boolean is_online;
    private Lift current_lift;
    private List<Interests> interests;

    public Skier(String username, String password) {
        this.id = 100;
        this.username = username;
        this.password = password;
    }

    public Skier(String username, String password, int id, String firstname, String lastname, Date birthdate){
        this.id = id;
        this.username=username;
        this.password=password;
        this.firstname =firstname;
        this.lastname =lastname;
        this.birthdate =birthdate;

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public Lift getCurrent_lift() {
        return current_lift;
    }

    public void setCurrent_lift(Lift current_lift) {
        this.current_lift = current_lift;
    }

    public List<Interests> getInterests() {
        return interests;
    }

    public void setInterests(List<Interests> interests) {
        this.interests = interests;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }


}