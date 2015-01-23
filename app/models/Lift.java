package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackMark on 18/01/2015.
 */
@Entity
public class Lift extends Model{

    @Id
    private int id;

    private String name;
    
    @ManyToOne
    private Skiarena ski_arena;
    
    private int seats;

    public static final Model.Finder<Integer, Lift> FIND = new Model.Finder<Integer, Lift>(Integer.class,
            Lift.class);



    // Constructor which sets the location to the specified parameters
    public Lift(String name, Skiarena ski_arena, int seats) {

        this.name = name;
        this.seats = seats;
        this.ski_arena = ski_arena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Skiarena getSki_arena() {
        return ski_arena;
    }

    public void setSki_arena(Skiarena ski_arena) {
        this.ski_arena = ski_arena;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public static Lift getByName(String name){
        return FIND.where().eq("name",name).findUnique();
    }

    public static List<Lift> getBySkiarena(String arena){
        List<Lift> bySkiarena=new ArrayList<Lift>();
        for(Lift f : getAll()){
            try {
                if (f.getSki_arena().getName().equals(arena)) bySkiarena.add(f);
            }catch(Exception e){}
        }
        return bySkiarena;
    }

    public static List<Lift> bySeatsAtArena(int seats, String arena){
        List<Lift> bySeats=new ArrayList<Lift>();
        for(Lift l : getBySkiarena(arena)){
            if(l.getSeats()==seats) bySeats.add(l);
        }
        return bySeats;
    }

    public static List<Lift> getAll()
    {
        return FIND.all();
    }

    public static int getMaxId(){
        int maxid=0;
        for (Lift ski : getAll()) {
            if (ski.getId() > maxid) maxid = ski.getId();
        }
        return maxid;
    }
}
