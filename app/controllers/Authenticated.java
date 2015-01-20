package controllers;

import play.mvc.*;
import play.mvc.Http.*;
import views.html.index;
import views.html.login;


/**
 * Created by Ivan on 20.01.2015.
 */
public class Authenticated extends Security.Authenticator {


        @Override
        public String getUsername(Context ctx) {
            return ctx.session().get("email");
        }

        @Override
        public Result onUnauthorized(Context ctx) {
            return ok(index.render());
        }
    }