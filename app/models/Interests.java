package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Ivan on 19.01.2015.
 */
<<<<<<< HEAD
@Entity
public class Interests extends Model {

    @Id
    private int id;

    @ManyToMany
    private List<Skier> skier;
=======
public class Interests {
    private String interest;

    public Interests() {
        interest = "";
    }

    public Interests(String interest) {
        setInterest(interest);
    }

    public boolean matchInterest(Interests interests) {
        return this.interest.compareTo(interests.getInterest()) == 0;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getInterest() {
        return this.interest;
    }
>>>>>>> origin/master
}
