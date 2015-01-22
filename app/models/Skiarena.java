package models;

import controllers.Application;
import play.db.ebean.Model;
import reader.ReadExcel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivan on 21.01.2015.
 */
@Entity
public class Skiarena extends Model {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "current_location")
    private Set<Skier> skiers;

    public static final Model.Finder<Integer, Skiarena> FIND = new Model.Finder<Integer, Skiarena>(Integer.class,
            Skiarena.class);


    public Skiarena(int id, String name){
        this.id=id;
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

    private static List<Skiarena> all=null;
    public static List<Skiarena> initSkiarenas(){
        try {
            ReadExcel test = new ReadExcel();
            test.read();
        } catch (Exception e){
e.printStackTrace();
        } finally {

            all = getAll();
        }
        return all;
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
        return maxid+1;
    }
}
