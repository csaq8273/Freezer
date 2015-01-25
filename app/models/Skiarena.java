package models;

import play.db.ebean.Model;
import reader.ReadExcel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

/**
 * Skiarena Model
 *
 * Created by Ivan on 21.01.2015.
 */
@Entity
public class Skiarena extends Model {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "current_location")
    private Set<Skier> skiers;

    @OneToMany(mappedBy = "ski_arena")
    private List<Lift> lifts;
    
    public static final Model.Finder<Integer, Skiarena> FIND = new Model.Finder<Integer, Skiarena>(Integer.class,
            Skiarena.class);


    public Skiarena(String name){
        this.id=getMaxId()+1;
        this.name=name;
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

    public static void initSkiarenas(){
        try {
            ReadExcel test = new ReadExcel();
            test.read();
        } catch (Exception e){
e.printStackTrace();
        }
    }

    public static Skiarena getByName(String name){
        return FIND.where().eq("name",name).findUnique();
    }

    public static List<Skiarena> getAll()
    {
        return FIND.all();
    }

    public static int getMaxId(){
        int maxid=0;
        for (Skiarena ski : getAll()) {
            if (ski.getId() > maxid) maxid = ski.getId();
        }
        return maxid;
    }
}
