/**
 * Created by Ivan on 22.01.2015.
 */

import models.Interests;
import models.Skiarena;
import play.Application;
import play.GlobalSettings;
import play.Logger;

public class Global extends GlobalSettings {
    public void onStart(Application app) {
        if(Skiarena.getAll().size()==0)
        Skiarena.initSkiarenas();
        if(Interests.getAll().size()==0) {
            new Interests("Sport").save();
            new Interests("Movie").save();
            new Interests("Education").save();
            new Interests("IT").save();
            new Interests("Smartphones").save();
            new Interests("Fashion").save();
            new Interests("Politics").save();
            new Interests("Architecture").save();
            new Interests("Business").save();
            new Interests("Fashion").save();
            new Interests("Economy").save();
            new Interests("Food").save();
            new Interests("Games").save();
        }
    }


    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
}