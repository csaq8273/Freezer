package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Meeting extends Model{

    @Id
    private String id;

    @ManyToMany
    private List<Skier> skiers;
    private Lift lift;
    private Date date;

    // Default constructor
    public Meeting(Lift lift , List<Skier> skiers) {
        this.skiers=skiers;
        this.lift=lift;
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Skier> getSkiers() {
        return skiers;
    }

    public void setSkiers(List<Skier> skiers) {
        this.skiers = skiers;
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