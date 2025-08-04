package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Flotta;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.TabellaDiMarcia;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.SessionManager;

import java.util.HashMap;

public class FineLineaHandler implements Handler {
    public static void handeRequest(TabellaDiMarcia tabellaMarcia) {
        String vId = Flotta.getInstance().trovaVeicoloId(tabellaMarcia);
        String token = SessionManager.getInstance().trovaToken(vId);
        try {
            LogoutHandler.handleRequest(token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    };
}
