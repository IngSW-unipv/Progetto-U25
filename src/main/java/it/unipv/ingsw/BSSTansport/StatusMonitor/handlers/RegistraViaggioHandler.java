package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import io.javalin.http.Context;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Flotta;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.Utils;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;

public class RegistraViaggioHandler implements Handler {
    public static void handleRequest(@NotNull Context ctx) {
        String token = ctx.cookie("token");
        String vId = null;
        HashMap<String,String> request = null;
        CheckpointSuLineaBean[] checkpoints = null;

        try{
            request=ctx.bodyAsClass(HashMap.class);
            if(!Utils.checkContextFields(request, new String[]{"orarioPartenza","capolinea","linea"}))
            {
                ctx.status(400);
                return;
            }
        }catch(Exception e){
            ctx.status(500);
            return;
        }

        vId=SessionManager.getInstance().getVId(token);

        try{
            checkpoints = DBManager.getInstance().getCheckpointSuLinea(Integer.parseInt(request.get("linea")));
            Flotta.getInstance().aggiungiVeicolo(
                    vId,
                    checkpoints,
                    LocalTime.parse(request.get("orarioPartenza")),
                    Integer.parseInt(request.get("capolinea")),
                    Integer.parseInt(request.get("linea"))
                    );
            ctx.json(Flotta.getInstance().webTabellaInfo(vId));
        }catch(SQLException e){
            ctx.status(500);
        }


    }

}
