package controllers;


import models.Meeting;
import models.Skiarena;
import models.Skier;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.immutable.Set;
import views.html.home;
import views.html.index;
import views.html.login;
import views.html.search;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static play.libs.Json.toJson;


public class Application extends Controller {

    public static Skier loggedInSkier = new Skier("ivan", "lol", 12, "Ivan", "Waldboth", new Date());
    public static List<Skiarena> skiarenas = null;

    public static Result index() {

        return ok(index.render());
    }

    public static Result searchSkier(){

        return ok(index.render());
    }

    public static Result login(){

        String username=  Form.form().bindFromRequest().get("username");
        String password=  Form.form().bindFromRequest().get("password");
        String new_user= Form.form().bindFromRequest().get("new_user");
        if(new_user.equals("on")){
            Skier loggedInSkier= new Skier(username,password);
            return ok(login.render(loggedInSkier));
        } else {
            List<Skier> skierList;
            skierList = new Model.Finder(String.class,Skier.class).all();
            for (Skier skier : skierList){
                if(skier.getUsername().equals(username) && skier.getPassword().equals(password)) loggedInSkier=skier;
            }
            if(loggedInSkier!=null) {
                session().clear();
                session("id", String.valueOf(loggedInSkier.getId()));
                return renderHome();
            }else
                return ok(login.render(loggedInSkier));

        }
    }

    public static Result register(){
        try {
            String username = Form.form().bindFromRequest().get("username");
            String password = Form.form().bindFromRequest().get("password");
            String firstname = Form.form().bindFromRequest().get("firstname");
            String lastname = Form.form().bindFromRequest().get("lastname");
            String birthdate = Form.form().bindFromRequest().get("birthdate");

            List<Skier> skierList;
            skierList = new Model.Finder(String.class, Skier.class).all();
            int maxid = 0;
            for (Skier ski : skierList) {
                if (ski.getId() > maxid) maxid = ski.getId();
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Skier skier = new Skier(username, password, maxid + 1, firstname, lastname, df.parse(birthdate));
            skier.save();
            loggedInSkier=skier;
            session().clear();
            session("id", String.valueOf(skier.getId()));
            int openMeetings = 0;
            int doneMeetings = 0;
            for (Meeting m : loggedInSkier.getMeetings()) {
                if (m.getDate().before(new Date())) {
                    doneMeetings++;
                } else openMeetings++;
            }
            return renderHome();
        }catch (ParseException e){
            return ok(login.render(loggedInSkier));
        }
    }

    public static Result meet() {
        return play.mvc.Results.TODO;
    }

    public static Result visitSkier() {
        String location=Form.form().bindFromRequest().get("setLocation");
        if(location != null){
            Skiarena ss = null;
            List<Skiarena> s=new Model.Finder(String.class,Skiarena.class).all();
            for(Skiarena ski :s){
                if(ski.getName().equals(location)) ss=ski;
            }
            Logger.info(ss + " : " + location);
            if(ss!=null) {
                loggedInSkier.setCurrent_location(ss);
                List<Skier> inLocation=new Model.Finder(String.class,Skier.class).all();
                int size=0;
                for(Skier skier : inLocation){
                    if(skier.getCurrent_location()!= null && skier.getCurrent_location().getName().equals(location) && skier != loggedInSkier) size++;
                }
                return ok(toJson(size));
            } else return ok(toJson(location));
        } else {

        }
        return play.mvc.Results.TODO;
    }

    public static Result home() {
        return renderHome();
    }

    private static Result renderHome(){
        int openMeetings=0;
        int doneMeetings=0;
        for(Meeting m : loggedInSkier.getMeetings()){
            if(m.getDate().before(new Date())){
                doneMeetings++;
            } else openMeetings++;
        }
        return ok(home.render(loggedInSkier,skiarenas,Integer.valueOf(openMeetings), Integer.valueOf(doneMeetings)));
    }
}
