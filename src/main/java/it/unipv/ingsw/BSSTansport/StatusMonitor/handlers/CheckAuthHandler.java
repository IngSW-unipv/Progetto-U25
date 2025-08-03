package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CheckAuthHandler implements Handler {
    public static void handleRequest(@NotNull Context ctx) {
        String token = ctx.cookie("token");
        if (token == null) {
            authFailed(ctx);
            return;
        }
        if (!SessionManager.getInstance().checkToken(token)) {
            authFailed(ctx);
        }
    }

    public static void authFailed(Context ctx) {
        ctx.redirect("/?causaRedirect=401");
    }

}
