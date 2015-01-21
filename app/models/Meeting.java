package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

@Entity
public class Meeting extends Model{

    @Id
    private String id;

    private Skier skier1;
    private Skier skier2;
    private Lift lift;
    private Date date;

    // Default constructor
    public Meeting(Skier skier1, Skier skier2, Lift lift) {
        this.skier1=skier1;
        this.skier2= skier2;
        this.lift=lift;
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Skier getSkier1() {
        return skier1;
    }

    public void setSkier1(Skier skier1) {
        this.skier1 = skier1;
    }

    public Skier getSkier2() {
        return skier2;
    }

    public void setSkier2(Skier skier2) {
        this.skier2 = skier2;
    }

    public Lift getLift() {
        return lift;
    }

    public void setLift(Lift lift) {
        this.lift = lift;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}