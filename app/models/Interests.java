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

    @ManyToMany
    private List<Skier> skier;
}
