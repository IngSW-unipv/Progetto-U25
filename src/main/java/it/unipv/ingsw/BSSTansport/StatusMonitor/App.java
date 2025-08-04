package it.unipv.ingsw.BSSTansport.StatusMonitor;

import com.fasterxml.jackson.databind.SerializationFeature;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.json.JavalinJackson;
import it.unipv.ingsw.BSSTansport.StatusMonitor.GUI.IntermediarioFlottaTabella;
import it.unipv.ingsw.BSSTansport.StatusMonitor.GUI.TabellaVeicoli;
import it.unipv.ingsw.BSSTansport.StatusMonitor.handlers.*;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) {
        HashMap<String, Boolean> arguments = parseArguments(args);

        DBManager dbm = DBManager.getInstance();
        Javalin webServer = createWebServer();

        if (arguments.get("show_GUI")) {
            TabellaVeicoli gui = new TabellaVeicoli(new IntermediarioFlottaTabella());
            GuiUpdateHandler.setGui(gui);
            GuiUpdateHandler.handleRequest();
        }
    }

    static private HashMap<String, Boolean> parseArguments(String[] args) {
        List<String> input = Arrays.asList(args);
        HashMap<String, Boolean> arguments = new HashMap<String, Boolean>();

        arguments.put("show_GUI", !input.contains("--no-gui"));

        return arguments;
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

            JavalinJackson mapper = new JavalinJackson();
            mapper.getMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            config.jsonMapper(mapper);
        });

        // api pubbliche
        app.get("/api/getLinee", GetLineeHandler::handleRequest);
        app.get("/api/getOrari", GetOrariHandler::handleRequest);
        app.post("/api/login", LoginHandler::handleRequest);

        // api riservate
        app.before("/reserved/*", CheckAuthHandler::handleRequest);
        app.before("/api/reserved/*", CheckAuthHandler::handleRequest);
        app.get("/api/reserved/logout", WebLogoutHandler::handleRequest);
        app.post("/api/reserved/registraViaggio", RegistraViaggioHandler::handleRequest);
        app.get("/api/reserved/prossimoCheckpoint", PcheckPointHandler::handleRequest);
        app.get("/api/reserved/infoCheckpoint", webInfoHandler::handleRequest);
        app.post("/api/reserved/guasto", GuastoHandler::handleRequest);

        app.start(8080);

        return app;
    }
}
