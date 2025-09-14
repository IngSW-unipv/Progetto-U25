package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Flotta;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;
import org.jetbrains.annotations.NotNull;

public class LogoutHandler implements Handler {
    public static void handleRequest(@NotNull String token) throws Exception {

        Flotta.getInstance().rimuoviVeicolo(SessionManager.getInstance().getVId(token));

        SessionManager.getInstance().logout(token);
    }

    public static void handleRequest(@NotNull Context ctx) throws Exception {
        String token = ctx.cookie("token");
        LogoutHandler.handleRequest(token);
        ctx.removeCookie("token");
    }
}
