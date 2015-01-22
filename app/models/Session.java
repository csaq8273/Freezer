package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Ivan on 22.01.2015.
 */

@Entity
public class Session extends Model {

    @Id
    private String id;

    private int skierId;

    public static final Model.Finder<String, Session> FIND = new Model.Finder<String, Session>(String.class,
            Session.class);

    public Session(String id, int skierId) {
        this.id = id;
        this.skierId = skierId;
    }

    public int getSkierId() {
        return skierId;
    }

    public void setSkierId(int skierId) {
        this.skierId = skierId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Session getById(String id){
        return FIND.byId(id);
    }

    public static List<Session> getAll()
    {
        return FIND.all();
    }

}

