package controllers;


import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;
import views.html.index;
import views.html.login;
import views.html.meeting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static play.libs.Json.toJson;

/**
 *
 * Controller Class
 */
public class Application extends Controller {


    private static String COOKIE_NAME = "freezersession";

    /**
     * Controller for / - Request
     *
     * @return home Page if Session detected, else index Page
     */
    public static Result index() {
        Skier loggedInSkier = Session.authenticateSession(request().cookie(COOKIE_NAME));
        if(loggedInSkier!=null)
            return redirect("/home");
        else
            return ok(index.render(""));
    }

    /**
     * Controller for /home - Request
     *
     * @return home Page if Session detected, else index Page
     */
    public static Result home() {
        Skier loggedInSkier = Session.authenticateSession(request().cookie(COOKIE_NAME));
        if(loggedInSkier==null)
            return redirect("/");
        else
            return renderHome(loggedInSkier);
    }

    /**
     * Get the Data to render the Home Page
     *
     * @param loggedInSkier
     * @return home Page
     */
    private static Result renderHome(Skier loggedInSkier){
        int openMeetings=0;
        int doneMeetings=0;
        for(Meeting m : Meeting.getBySkier(loggedInSkier)){
            if(m.getDate().before(new Date())){
                doneMeetings++;
            } else openMeetings++;
        }
        List<Meeting> usermeetings =Meeting.getBySkier(loggedInSkier);
                Collections.sort(usermeetings);
        return ok(home.render(loggedInSkier,Interests.getAll(),Skiarena.getAll(),Integer.valueOf(openMeetings), Integer.valueOf(doneMeetings),usermeetings));
    }

    /**
     * Controller for /search - Request
     *
     * @return A Json list of Skier
     */
    public static Result searchSkier(){
        Skier loggedInSkier = Session.authenticateSession(request().cookie(COOKIE_NAME));
        if(loggedInSkier==null)
            return redirect("/");
        else
            return ok(toJson(Skier.search(loggedInSkier)));
    }

    /**
     * Controller for /login - Request
     * Handel the login Formular
     *
     * @return Home Page if registration was success, else login Page
     */
    public static Result login(){
        String username=  Form.form().bindFromRequest().get("username");
        String password=  Form.form().bindFromRequest().get("password");
        String new_user= Form.form().bindFromRequest().get("new_user");
        if(new_user != null){
            return ok(login.render(username,password));
        } else {
            Skier s=Session.authenticate(username,password);
            if(s==null){
                return badRequest(index.render("Invalid Username or Password"));
            } else {
                login(s);
                return redirect("/home");
            }
        }
    }

    /**
     * Save Session key and set Cookie
     *
     * @param skier logged in Skier
     */
    public static void login(Skier skier){
        String uuid=java.util.UUID.randomUUID().toString();
        response().setCookie(COOKIE_NAME, uuid);
        Session s= new Session(uuid,skier.getId());
        s.save();
    }

    /**
     * Controller for /login - Request
     *
     * @return Home Page if registration was success, else login Page
     */
    public static Result register(){
        Skier loggedInSkier=Session.register();
        if(loggedInSkier!=null) {
            login(loggedInSkier);
            return renderHome(loggedInSkier);
        }else
            return badRequest(index.render("Something went wrong!"));
    }

    /**
     * Handel the create meet formular
     *
     * @param id of the Meeting Partner
     * @return The Meetings of the logged in skier
     */
    public static Result meet(Integer id) {
        Skier loggedInSkier = Session.authenticateSession(request().cookie(COOKIE_NAME));
        if(loggedInSkier==null)
            return redirect("/");
        else {

            String time = Form.form().bindFromRequest().get("time");
            List<Skier> skierList = new ArrayList<Skier>();
            skierList.add(loggedInSkier);
            skierList.add(Skier.FIND.byId(id));
            String lift = Form.form().bindFromRequest().get("lift");
            DateFormat df = new SimpleDateFormat("HH:mm");
            Meeting m;
            try {
                        m= new Meeting(Lift.getByName(lift), skierList, new Date(new Date().getTime() + df.parse(time).getTime()));
                        m.save();
                    } catch (Exception e) {
                        return ok(toJson(e));
                    }
            return ok(toJson(Meeting.getBySkier(loggedInSkier)));



        }
    }

    /**
     * Handel the /meet - request after a found skier
     *
     * @param id of the Meeting Partner
     * @return The request meeting Dialog
     */
    public static  Result meeting(Integer id){
        Skier loggedInSkier = Session.authenticateSession(request().cookie(COOKIE_NAME));
        if(loggedInSkier==null)
            return redirect("/");
        else {
            return ok(meeting.render(loggedInSkier, Skier.FIND.byId(id), Lift.getBySkiarena(loggedInSkier.getCurrent_location().getName())));
        }
    }


    /**
     * Handle the set Location Option input on the homescreen and calculate the skier at the same location
     *
     * @return number of the skier at this location
     */
    public static Result visitSkier() {
        Skier loggedInSkier = Session.authenticateSession(request().cookie(COOKIE_NAME));
        if(loggedInSkier==null)
            return redirect("/");
        else {
            String location = Form.form().bindFromRequest().get("setLocation");
            if (location != null) {
                Skiarena ss = Skiarena.getByName(location);
                if (ss != null) {
                    loggedInSkier.setCurrent_location(ss);
                    loggedInSkier.save();
                    List<Skier> inLocation = Skier.searchBySkiArena(location);
                    int size = 0;
                    for (Skier skier : inLocation) {
                        if (!skier.equals(loggedInSkier)) size++;
                    }
                    return ok(toJson(size));
                } else return ok(toJson(0));
            } else {
                return ok(toJson(0));
            }
        }
    }

    public static Result loginFail() {
        return redirect("/");
    }
}
