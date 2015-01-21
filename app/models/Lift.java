package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by BlackMark on 18/01/2015.
 */
@Entity
public class Lift extends Model{

    @Id
    private String id;

    private String name;
    private Skiarena ski_arena;
    private int seats;

    // Constructor which sets the location to the specified parameters
    public Lift(String name, Skiarena ski_arena, int seats) {

        this.name = name;
        this.seats = seats;
        this.ski_arena = ski_arena;
    }

}
