/**
 * Created by Ivan on 22.01.2015.
 */
import models.Skiarena;
import play.*;

public class Global extends GlobalSettings {
    public void onStart(Application app) {
        controllers.Application.skiarenas= Skiarena.initSkiarenas();
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
}