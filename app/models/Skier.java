package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import java.util.Date;
import java.util.List;

public class Skier extends Credentials {
    private String firstname;
    private String lastname;
    private Date birthdate;
    private boolean is_online;
    private Lift current_lift;
    private List<Interests> interests;

    public Skier(String username, String password, String firstname, String lastname, Date birthdate){
        super(username,password);
        this.firstname =firstname;
        this.lastname =lastname;
        this.birthdate =birthdate;

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