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
    private Integer id;

    @ManyToMany
    private List<Skier> skiers;
    
    
    private Lift lift;
    
    private Date date;
    
    public static final Model.Finder<Integer, Meeting> FIND = new Model.Finder<Integer, Meeting>(Integer.class,
    		Meeting.class);

    // Default constructor
    public Meeting(Lift lift , List<Skier> skiers, Date date) {
    	this.id=Meeting.getMaxId();
    	if(this.id==0)
    	this.id=Integer.valueOf(1);
    	
        this.skiers=skiers;
        this.lift=lift;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    
    public static List<Meeting> getAll()
    {
        return FIND.all();
    }

    public static int getMaxId(){
        int maxid=0;
        for (Meeting ski : getAll()) {
            if (ski.getId() > maxid) maxid = ski.getId();
        }
        return maxid;
    }
}