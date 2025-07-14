package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CheckAuthHandler implements Handler {
    public static void handleRequest(@NotNull Context ctx) {
        String token = ctx.cookie("token");
        if (token == null) {
            ctx.status(403);
        }
        if (!SessionManager.getInstance().checkToken(token)) {
            ctx.status(403);
        }
    }

}
