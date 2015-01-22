package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import play.db.ebean.Model;

import javax.persistence.*;
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

    public Skier(String username, String password) {
        this.id = 100;
        this.username = username;
        this.password = password;
        this.current_location = Skiarena.FIND.byId(1000);
        this.interests=new ArrayList<Interests>();
        this.meetings=new ArrayList<Meeting>();
    }

    public Skier(String username, String password, int id, String firstname, String lastname, Date birthdate){
        this.id = id;
        this.username=username;
        this.password=password;
        this.firstname =firstname;
        this.lastname =lastname;
        this.birthdate =birthdate;
        this.interests=new ArrayList<Interests>();
        this.meetings=new ArrayList<Meeting>();
        this.current_location = Skiarena.FIND.byId(1000);


    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public Skiarena getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(Skiarena current_location) {
        this.current_location = current_location;
    }

    public List<Interests> getInterests() {
        return interests;
    }

    public void setInterests(List<Interests> interests) {
        this.interests = interests;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public static List<Skier> searchByUsername(String username){
        return FIND.where().eq("name",username).findList();
    }

    public static List<Skier> getByAttributes(String arena, int from, int to, List<Interests> interests){
        List<Skier> byAttribute = new ArrayList<Skier>();
        int attributes=0;
        if(arena!=null) attributes+=1;
        if(from != -1) attributes+=2;
        if(interests!= null) attributes+=4;
        switch(attributes){

            case 1: return getBySkiArena(getAll(),arena);
            case 2: return getByAgeFromTo(from, to);
            case 3: return getBySkiArena(getByAgeFromTo(from, to),arena);
            case 4: return getByInterests(interests);
            case 5: return getBySkiArena(getByInterests(interests),arena);
            case 6: return getByInterests(getByAgeFromTo(from, to), interests);
            case 7: return getByInterests(getBySkiArena(getByAgeFromTo(from, to),arena),interests);
            default:return getAll();
        }
    }

    public static List<Skier> getBySkiArena(String arena){
        return getBySkiArena(getAll(),arena);
    }

    private static List<Skier> getBySkiArena(List<Skier> skier, String arena){
        List<Skier> byArena = new ArrayList<Skier>();
        for(Skier s : skier){
            if(s.getCurrent_location()!= null && s.getCurrent_location().getName().equals(arena)) byArena.add(s);
        }
        return byArena;
    }

    public static List<Skier> getByAgeFromTo(int from, int to) {
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

    public static List<Skier> getByInterests(List<Interests> interests) {
        return getByInterests(getAll(),interests);
    }

    private static List<Skier> getByInterests(List<Skier> skier, List<Interests> interests){
        List<Skier> byInterests=new ArrayList<Skier>();
        for(Skier s : skier){
            int sameInterests=0;
            for(Interests i : s.getInterests()){
                if(interests.contains(i)) sameInterests++;
            }
            if(sameInterests > (interests.size()/3)) byInterests.add(s);
        }
        return byInterests;
    }


    public static List<Skier> getAll()
    {
        return FIND.all();
    }


    public static int getMaxId(){
        int maxid=0;
        for (Skier ski : getAll()) {
            if (ski.getId() > maxid) maxid = ski.getId();
        }
        return maxid;
    }

    public static Skier authenticate(String username, String password) {
        return null;
    }
}