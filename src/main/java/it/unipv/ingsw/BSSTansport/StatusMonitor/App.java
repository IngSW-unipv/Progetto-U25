package it.unipv.ingsw.BSSTansport.StatusMonitor;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import it.unipv.ingsw.BSSTansport.StatusMonitor.handlers.*;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;

import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        DBManager dbm = DBManager.getInstance();
        Javalin webServer = createWebServer();
    }


    static private Javalin createWebServer() {
        Javalin app = Javalin.create(config -> {
                    config.router.ignoreTrailingSlashes = true;
                    config.router.caseInsensitiveRoutes = true;
                    config.staticFiles.add(staticFiles -> {
                        staticFiles.hostedPath = "/";
                        staticFiles.directory = "web";
                        staticFiles.location = Location.EXTERNAL;
                    });
                }
        );

        //papi pubbliche
        app.get("/api/getLinee", GetLineeHandler::handleRequest);
        app.get("/api/getOrari", GetOrariHandler::handleRequest);
        app.post("/api/login", LoginHandler::handleRequest);

        //api riservate
        app.before("/reserved/*", CheckAuthHandler::handleRequest);
        app.before("/api/reserved/*", CheckAuthHandler::handleRequest);
        app.get("/api/reserved/logout", WebLogoutHandler::handleRequest);
        app.post("/api/reserved/registraViaggio", RegistraViaggioHandler::handleRequest);
        app.get("/api/reserved/prossimoCheckpoint", PcheckPointHandler::handleRequest);
        app.post("/api/reserved/guasto", GuastoHandler::handleRequest);

        app.start(8080);

        return app;
    }
}
