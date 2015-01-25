package models;

import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Session Class, saves the session key of the logged in users
 *
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

    /**
     * Authenticate a Skier by a Given Cookie
     *
     * @param sessionCookie
     * @return Skier
     */
    public static Skier authenticateSession(Http.Cookie sessionCookie){

        if(sessionCookie != null) {
            String key = sessionCookie.value();
            if (key != null) {
                Session s = Session.getById(key);
                if(s!=null) {
                    Skier skier = null;
                    try {
                        skier = Skier.FIND.byId(s.getSkierId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    return skier;
                }
            }
        }
        return null;
    }


    /**
     * Authenticate a Skier by username and Password
     *
     * @param username
     * @param password
     * @return Skier
     */
    public static Skier authenticate(String username, String password) {
        try{
            Skier result=Skier.FIND.where().like("username",username).findUnique();

            if(result.getPassword().equals(password))
                return result;
            else return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Register a Skier with Form Data
     *
     * @return Skier
     */
    public static Skier register() {
        String username = Form.form().bindFromRequest().get("username");
        String password = Form.form().bindFromRequest().get("password");
        String firstname = Form.form().bindFromRequest().get("firstname");
        String lastname = Form.form().bindFromRequest().get("lastname");
        String birthdate = Form.form().bindFromRequest().get("birthdate");
        String [] birthpunkt = birthdate.split(".");

        if(birthpunkt.length!=3) {
           birthpunkt = birthdate.split("-");
        }
        if (birthpunkt[0].length() > 2) {
            birthdate=birthpunkt[0]+"-"+(birthpunkt[1].length()==1?"0"+birthpunkt[1]:birthpunkt[1])+"-"+((birthpunkt[2].length()==1)?"0"+birthpunkt[2]:birthpunkt[2]);
        }else {
            birthdate=birthpunkt[2]+"-"+(birthpunkt[1].length()==1?"0"+birthpunkt[1]:birthpunkt[1])+"-"+((birthpunkt[0].length()==1)?"0"+birthpunkt[0]:birthpunkt[0]);

        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Skier skier = new Skier(username, password, firstname, lastname, df.parse(birthdate));
            skier.save();

            return skier;
        }catch (Exception e){
                 e.printStackTrace();
            return null;
        }


    }

    public static Session getById(String id){
        return FIND.byId(id);
    }

    public static List<Session> getAll()
    {
        return FIND.all();
    }

    public int getSkierId() {
        return skierId;
    }

}

