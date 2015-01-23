package controllers;


import models.*;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import scala.collection.immutable.Set;
import views.html.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static play.libs.Json.toJson;


public class Application extends Controller {


    private static String COOKIE_NAME = "freezersession";

    public static Skier authenticate(){

        Http.Cookie cookie = request().cookie(COOKIE_NAME);
        if(cookie != null) {
            String key = cookie.value();
            if (key != null) {
                Session s = Session.getById(key);
                Skier skier=null;
                try {
                    skier=Skier.FIND.byId(s.getSkierId());
                } catch (Exception e){
                    e.printStackTrace();
                }
                return skier;
            } else return null;
        } else return null;
    }

    public static Result index() {
        Skier loggedInSkier = authenticate();
        if(loggedInSkier!=null)
            return redirect("/home");
        else
            return ok(index.render(""));
    }

    public static Result home() {
        Skier loggedInSkier = authenticate();
        if(loggedInSkier!=null)
            return renderHome(loggedInSkier);
        else return redirect("/");
    }

    public static Result searchSkier(){
        Skier loggedInSkier = authenticate();
        if(loggedInSkier!=null) {
            String username = Form.form().bindFromRequest().get("username");
            String birthdate_to = Form.form().bindFromRequest().get("birthdate_to");
            String birthdate_from = Form.form().bindFromRequest().get("birthdate_from");
            String location = Form.form().bindFromRequest().get("loocation");
            List<Interests> myInterests=new ArrayList<Interests>();

            if(Form.form().bindFromRequest().get("interests_switch").equals("on")) {
                for (Interests interests : Interests.getAll()) {
                        if (Form.form().bindFromRequest().get("interest_" + interests.getId())!=null)
                            myInterests.add(interests);

                }
            }
            loggedInSkier.setInterests(myInterests);
            loggedInSkier.save();
            List<Skier> result=Skier.getAll();
            return ok(toJson(result));
        }else
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

    public static Result meet(Integer id) {
        Skier loggedInSkier = authenticate();
        String newMeeting = Form.form().bindFromRequest().get("time");
        if(loggedInSkier.getCurrent_location()==null){
            return redirect("/home");
        } else {
            if (newMeeting != null) {
            	List<Skier> skierList=new ArrayList<Skier>();
            	skierList.add(loggedInSkier);
            	skierList.add(Skier.FIND.byId(id));
                String lift = Form.form().bindFromRequest().get("lift");
                String time = Form.form().bindFromRequest().get("time");
            	
                DateFormat df = new SimpleDateFormat("HH:mm");
                try{
                	Meeting m = new Meeting(Lift.getByName(lift),skierList,df.parse(time));
                	m.save();
            	} catch (Exception e){
//            		List<> jsn=new ArrayList<>();
//            		jsn.add(toJson(skierList));
//            		jsn.add(toJson(m));
//            		jsn.add(toJson(lift));
//            		jsn.add(toJson(e));

                	return ok(toJson(e));
                }
                return redirect("/home#viewMeetings");

            } else {
                return ok(meeting.render(loggedInSkier, Skier.FIND.byId(id), Lift.getBySkiarena(loggedInSkier.getCurrent_location().getName())));
            }
        }
    }

    public static Result visitSkier() {
        Skier loggedInSkier = authenticate();
            String location = Form.form().bindFromRequest().get("setLocation");
            if (location != null) {
                Skiarena ss = Skiarena.getByName(location);
                if (ss != null) {
                    loggedInSkier.setCurrent_location(ss);
                    loggedInSkier.save();
                    List<Skier> inLocation = Skier.getBySkiArena(location);
                    int size = 0;
                    for (Skier skier : inLocation) {
                        if (skier != loggedInSkier) size++;
                    }
                    return ok(toJson(size));
                } else return ok(toJson(0));
            } else {
                 return play.mvc.Results.TODO;
            }
    }


    private static Result renderHome(Skier loggedInSkier){
        int openMeetings=0;
        int doneMeetings=0;
        for(Meeting m : loggedInSkier.getMeetings()){
            if(m.getDate().before(new Date())){
                doneMeetings++;
            } else openMeetings++;
        }
        return ok(home.render(loggedInSkier,Interests.getAll(),Skiarena.getAll(),Integer.valueOf(openMeetings), Integer.valueOf(doneMeetings)));
    }
}
