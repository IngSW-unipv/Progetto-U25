package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Flotta;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.Utils;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GuastoHandler {
    public static void handleRequest(@NotNull Context ctx) {
        String token = ctx.cookie("token");
        String vId = null;
        HashMap<String, String> request = null;

        try {
            request = ctx.bodyAsClass(HashMap.class);
            if (!Utils.checkContextFields(request, new String[]{"guasto"})) {
                ctx.status(400);
                return;
            }
        } catch (Exception e) {
            ctx.status(500);
            return;
        }

        vId = SessionManager.getInstance().getVId(token);
        Flotta.getInstance().setGuasto(vId, request.get("guasto").equals("true"));
    }
}
