package controllers;


import models.Interests;
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
import java.util.*;

import static play.libs.Json.toJson;


public class Application extends Controller {

    public static Skier loggedInSkier = new Skier("ivan", "lol", 12, "Ivan", "Waldboth", new Date());
    public static List<Skiarena> skiarenas = null;

    public static Result index() {

        return ok(index.render());
    }

    // Searches through all skiers and tries to find the best matches
    // Matching criteria are age difference and gender and the result is sorted by number of matching interests
    // The search algorithm is pretty much as inefficient as it gets, but should do for our purposes
    public static Result searchSkier() {
        // Options to be used for the algorithm
        // TODO: allow the user to change these settings
        int ageDifference = 5;
        int genderToMatch = 1; // this means: match only females

        List<Skier> skierList = new Model.Finder(String.class, Skier.class).all();
        List<Skier> matchedSkierList = new ArrayList();
        Map<Integer, Integer> interestsMap = new HashMap();

        // Go through all skiers
        for (int i = 0; i < skierList.size(); ++i) {
            Skier skier = skierList.get(i);

            // We don't wanna match ourselves
            if (skier == loggedInSkier) {
                continue;
            }

            // Is our match even within the specified age range and has the right gender
            if (Math.abs(loggedInSkier.getBirthYear() - skier.getBirthYear()) <= ageDifference && skier.getGender() == genderToMatch) {
                // Compare all interests
                for (Interests loggedInInterest : loggedInSkier.getInterests()) {
                    for (Interests interest : skier.getInterests()) {
                        // Do we have a match?
                        if (loggedInInterest.matchInterest(interest)) {
                            // Store how many matching interests we have
                            Integer matchedInterests = interestsMap.get(i);

                            if (matchedInterests == null) {
                                matchedInterests = 1;
                            } else {
                                ++matchedInterests;
                            }

                            interestsMap.put(i, matchedInterests);
                        }
                    }
                }

                matchedSkierList.add(skier);
            }
        }

        // Sort the matched skiers based on the number of matching interests
        Object[] sortMap = interestsMap.entrySet().toArray();
        Arrays.sort(sortMap, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<Integer, Integer>) o2).getValue().compareTo(((Map.Entry<Integer, Integer>) o1).getValue());
            }
        });

        List<Skier> sortedMatchedSkierList = new ArrayList();

        for (Object sortedElem : sortMap) {
            sortedMatchedSkierList.add(matchedSkierList.get(((Map.Entry<Integer, Integer>) sortedElem).getKey()));
        }

        // TODO: Return sortedMatchedSkierList somehow?!

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
