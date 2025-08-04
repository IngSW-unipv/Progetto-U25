package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Flotta;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class webInfoHandler implements Handler {
    public static void handleRequest(Context ctx, String vId) {
        HashMap<String, String>[] webTab = Flotta.getInstance().webTabellaInfo(vId);
        ctx.json(webTab);
    }

    public static void handleRequest(@NotNull Context ctx) {
        String token = ctx.cookie("token");
        String vId = SessionManager.getInstance().getVId(token);
        handleRequest(ctx, vId); // calls the other method overload
    }
}
