package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;
import org.jetbrains.annotations.NotNull;

public class GetLineeHandler implements Handler{
    public static void handleRequest(@NotNull Context ctx){
        Integer[] linee = DBManager.getInstance().getLinee();

        ctx.json(linee);
    }
}
