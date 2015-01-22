package models;

import controllers.Application;
import play.db.ebean.Model;
import reader.ReadExcel;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Ivan on 21.01.2015.
 */
@Entity
public class Skiarena extends Model {

    @Id
    private String id;

    private String name;

    public Skiarena(int id, String name){
        this.id="ski_"+id;
        this.name=name;
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

        } finally {

            all = new Model.Finder(String.class, Skiarena.class).all();
        }
        return all;
    }
}
