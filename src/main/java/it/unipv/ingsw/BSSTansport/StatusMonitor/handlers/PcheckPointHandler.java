package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Flotta;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;
import org.jetbrains.annotations.NotNull;

public class PcheckPointHandler {
    public static void handleRequest(@NotNull Context ctx) {
        String token = ctx.cookie("token");

        String vId = SessionManager.getInstance().getVId(token);

        boolean finished = Flotta.getInstance().nextCheckpoint(vId);
        if (finished) {
            ctx.status(205);
        } else {
            webInfoHandler.handleRequest(ctx, vId);
        }
    }
}
