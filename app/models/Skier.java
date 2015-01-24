package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import play.data.Form;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Skier extends Model{

    @Id
    private int id;

    private final String username;
    private final String password;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private boolean is_online;

    @ManyToOne
    private Skiarena current_location;

    @ManyToMany
    private List<Interests> interests;

    @ManyToMany
    private List<Meeting> meetings;

    public static final Model.Finder<Integer, Skier> FIND = new Model.Finder<Integer, Skier>(Integer.class,
            Skier.class);

    public Skier(String username, String password, String firstname, String lastname, Date birthdate){
        this.id = getMaxId()+1;
        this.username=username;
        this.password=password;
        this.firstname =firstname;
        this.lastname =lastname;
        this.birthdate =birthdate;
        this.interests=new ArrayList<Interests>();
        this.meetings=new ArrayList<Meeting>();
        this.current_location = Skiarena.FIND.byId(1000);

    }

    public static List<Skier> search(Skier loggedInSkier) {
        String username = Form.form().bindFromRequest().get("username");
        String birthdate_to = Form.form().bindFromRequest().get("birthdate_to");
        String birthdate_from = Form.form().bindFromRequest().get("birthdate_from");
        String location = Form.form().bindFromRequest().get("loocation");
        if (location.equals("on") && loggedInSkier.getCurrent_location() != null)
            location = loggedInSkier.getCurrent_location().getName();
        else location =null;
        int from=Integer.MIN_VALUE;
        int to=Integer.MAX_VALUE;
        try{ from=Integer.parseInt(birthdate_from);} catch(Exception e){}
        try{ to=Integer.parseInt(birthdate_to);} catch(Exception e){}

        List<Interests> myInterests = new ArrayList<Interests>();
        if (Form.form().bindFromRequest().get("interests_switch").equals("on")) {
            for (Interests interests : Interests.getAll()) {
                if (Form.form().bindFromRequest().get("interest_" + interests.getId()) != null)
                    myInterests.add(interests);

            }

            loggedInSkier.setInterests(myInterests);
            loggedInSkier.save();
        }
        List<Skier> result=new ArrayList<Skier>();

        if (username.isEmpty())
            result= Skier.searchByAttributes(location, from, to, myInterests);
        else{
            result =Skier.searchByUsername(username);
        }

        if(result==null)
            result=new ArrayList<Skier>();

        if(result.contains(loggedInSkier))
            result.remove(loggedInSkier);
        return result;
    }


    public static List<Skier> searchByUsername(String username){
        return FIND.where().like("name",username).findList();
    }

    public static List<Skier> searchByAttributes(String arena, int from, int to, List<Interests> interests){
        List<Skier> byAttribute = new ArrayList<Skier>();
        int attributes=0;
        if(arena!=null) attributes+=1;
        if(from != -1) attributes+=2;
        if(interests.size()!= 0) attributes+=4;
        switch(attributes){

            case 1: return getBySkiArena(getAll(),arena);
            case 2: return searchByAgeFromTo(from, to);
            case 3: return getBySkiArena(searchByAgeFromTo(from, to), arena);
            case 4: return searchByInterests(interests);
            case 5: return getBySkiArena(searchByInterests(interests), arena);
            case 6: return searchByInterests(searchByAgeFromTo(from, to), interests);
            case 7: return searchByInterests(getBySkiArena(searchByAgeFromTo(from, to), arena), interests);
            default:return getAll();
        }
    }

    public static List<Skier> searchBySkiArena(String arena){
        return getBySkiArena(getAll(),arena);
    }

    private static List<Skier> getBySkiArena(List<Skier> skier, String arena) {
        List<Skier> byArena = new ArrayList<Skier>();
        for (Skier s : skier) {
            if (s.getCurrent_location() != null && s.getCurrent_location().getName().equals(arena)) byArena.add(s);
        }
        return byArena;
    }

    public static List<Skier> searchByAgeFromTo(int from, int to) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        try {
            return FIND.where().between("date",
                    df.parse(today.getYear() - from + "-" + (today.getDay() < 10 ? "0" + today.getDay() : today.getDay()) + "-" + (today.getMonth() < 10 ? "0" + today.getMonth() : today.getMonth())),
                    df.parse(today.getYear() - from + "-" + (today.getDay() < 10 ? "0" + today.getDay() : today.getDay()) + "-" + (today.getMonth() < 10 ? "0" + today.getMonth() : today.getMonth()))).findList();

        } catch (Exception e){
            return new ArrayList<Skier>();
        }
    }

    public static List<Skier> searchByInterests(List<Interests> interests) {
        return searchByInterests(getAll(), interests);
    }

    private static List<Skier> searchByInterests(List<Skier> skier, List<Interests> interests) {
        List<Skier> byInterests = new ArrayList<Skier>();
        for (Skier s : skier) {
            int sameInterests = 0;
            for (Interests i : s.getInterests()) {
                if (interests.contains(i)) sameInterests++;
            }
            if (sameInterests > (interests.size() / 3)) byInterests.add(s);
        }
        return byInterests;
    }


    public static List<Skier> getAll()
    {
        return FIND.all();
    }


    public static int getMaxId() {
        int maxid = 0;
        for (Skier ski : getAll()) {
            if (ski.getId() > maxid) maxid = ski.getId();
        }
        return maxid;
    }

    //GETTER & SETTER
    public int getId() {
        return id;
    }


    public List<Interests> getInterests() {
        return interests;
    }

    public Skiarena getCurrent_location() {
        return current_location;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setInterests(List<Interests> interests) {
        this.interests = interests;
    }

    public void setCurrent_location(Skiarena current_location) {
        this.current_location = current_location;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public boolean isIs_online() {
        return is_online;
    }
}