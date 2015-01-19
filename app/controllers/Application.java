package controllers;


import models.Skier;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.immutable.HashSet;
import views.html.index;
import views.html.search;

import java.util.Date;


public class Application extends Controller {

    public static Skier example = new Skier("ivan", "lol", "Ivan", "Waldboth", new Date());


    public static Result index() {
        
        return ok(index.render(example));
    }

    public static Result searchSkier(){
        HashSet<Skier> aviable_skier = new HashSet<Skier>();
        return ok(search.render(aviable_skier.toList()));
    }

}
