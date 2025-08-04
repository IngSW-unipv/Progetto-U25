package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.OrariBean;
import org.jetbrains.annotations.NotNull;

public class GetOrariHandler implements Handler {
    public static void handleRequest(@NotNull Context ctx) {
        try {
            Integer value = ctx.queryParamAsClass("linea", Integer.class).get();
            OrariBean[] orari = DBManager.getInstance().getOrari(value);
            ctx.json(orari);
        } catch (ValidationException e) {
            ctx.status(400);
        }

    }
}
