package controllers;


import models.Skier;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.immutable.Set;
import server.Database;
import views.html.index;
import views.html.search;

import java.util.Date;


public class Application extends Controller {

    public static Skier example = new Skier("ivan", "lol", "Ivan", "Waldboth", new Date());


    public static Result index() {
        Database s = new Database();
        s.getSkier();
        s.log+="jkjjkl";
        return ok(index.render(s.log));
    }

    public static Result searchSkier(){
        return ok(index.render("LOL"));
    }

}
