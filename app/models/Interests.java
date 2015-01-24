package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Ivan on 19.01.2015.
 */
@Entity
public class Interests extends Model {

    @Id
    private int id;

    private String name;

    public static final Model.Finder<Integer, Interests> FIND = new Model.Finder<Integer, Interests>(Integer.class,
            Interests.class);

    @ManyToMany
    private List<Skier> skier;

    public Interests (String name){
        this.id=getMaxId() +1 ;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Interests> getAll(){return FIND.all();}

    public static int getMaxId(){
        int maxid=0;
        for (Interests interest : getAll()) {
            if (interest.getId() > maxid) maxid = interest.getId();
        }
        return maxid;
    }

    public int getId() {
        return id;
    }
}
