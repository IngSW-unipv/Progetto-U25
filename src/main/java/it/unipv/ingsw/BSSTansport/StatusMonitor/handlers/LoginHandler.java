package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.validation.ValidationException;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.LoginResult;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LoginHandler implements Handler {
    public static void handleRequest(@NotNull Context ctx) throws Exception {
        HashMap<String, String> request = new HashMap<>();

        // unpack json and validate
        try {
            request = ctx.bodyAsClass(HashMap.class);
            if (!(request.containsKey("username") && request.containsKey("password") && request.containsKey("vid"))) {
                throw new Exception("wrong fields");
            }

        } catch (Exception e) {
            ctx.status(400);
            return;
        }

        try {
            LoginResult result = SessionManager.getInstance().login(request.get("username"), request.get("password"),
                    request.get("vid"));
            if (result.isSuccess()) {
                ctx.json(result).cookie(new Cookie("token", result.getToken()));
            } else {
                ctx.status(403);
            }

        } catch (Exception e) {
            ctx.status(500);
        }
    }
}
