package controllers;


import models.Meeting;
import models.Session;
import models.Skiarena;
import models.Skier;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Http;
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

    public static List<Skiarena> skiarenas = null;

    private static String COOKIE_NAME = "freezersession";

    public static Skier authenticate(){

        Http.Cookie cookie = request().cookie(COOKIE_NAME);
        if(cookie != null) {
            String key = cookie.value();
            if (key != null) {
                Session s = Session.getById(key);
                return Skier.FIND.byId(s.getSkierId());
            }
        }
            return null;
    }

    public static Result index() {
        Skier loggedInSkier = authenticate();
        if(loggedInSkier!=null)
            return renderHome(loggedInSkier);
        else return ok(index.render(""));
    }

    public static Result searchSkier(){
        Skier loggedInSkier = authenticate();
    if(loggedInSkier!=null)
        return redirect("/home");
        else
        return ok(index.render(""));
    }

    public static Result login(){

        String username=  Form.form().bindFromRequest().get("username");
        String password=  Form.form().bindFromRequest().get("password");
        String new_user= Form.form().bindFromRequest().get("new_user");
        if(new_user != null){
            Skier loggedInSkier= new Skier(username,password);
            return ok(login.render(loggedInSkier));
        } else {
            Skier s=Skier.authenticate(username,password);
            if(s==null){
                return badRequest(index.render("Invalid Username or Password"));
            } else {
                login(s);
                return renderHome(s);
            }
        }
    }

    public static void login(Skier skier){
        String uuid=java.util.UUID.randomUUID().toString();
        response().setCookie(COOKIE_NAME, uuid);
        Session s= new Session(uuid,skier.getId());
        s.save();
    }


    public static Result register(){
            String username = Form.form().bindFromRequest().get("username");
            String password = Form.form().bindFromRequest().get("password");
            String firstname = Form.form().bindFromRequest().get("firstname");
            String lastname = Form.form().bindFromRequest().get("lastname");
            String birthdate = Form.form().bindFromRequest().get("birthdate");

            int maxid = Skier.getMaxId();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Skier skier = new Skier(username, password, maxid + 1, firstname, lastname, df.parse(birthdate));
            skier.save();
            login(skier);
            return renderHome(skier);
        }catch (ParseException e){
            return ok(login.render(new Skier(username,password)));
        }


    }

    public static Result meet() {
        Skier loggedInSkier = authenticate();
        return play.mvc.Results.TODO;
    }

    public static Result visitSkier() {
        Skier loggedInSkier = authenticate();

        String location=Form.form().bindFromRequest().get("setLocation");
        if(location != null){
            Skiarena ss = Skiarena.getByName(location);
            Logger.info(ss + " : " + location);
            if(ss!=null) {
                loggedInSkier.setCurrent_location(ss);
                loggedInSkier.save();
                List<Skier> inLocation=Skier.getBySkiArena(location);
                int size=0;
                for(Skier skier : inLocation){
                    if(skier != loggedInSkier) size++;
                }
                return ok(toJson(size));
            } else return ok(toJson(location));
        } else {

        }
        return play.mvc.Results.TODO;
    }

    public static Result home() {
        Skier loggedInSkier = authenticate();
        if(loggedInSkier!=null)
            return renderHome(loggedInSkier);
        else return ok(index.render(""));
    }

    private static Result renderHome(Skier loggedInSkier){
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
