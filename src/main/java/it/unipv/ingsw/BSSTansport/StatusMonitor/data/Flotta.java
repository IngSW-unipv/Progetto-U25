package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import it.unipv.ingsw.BSSTansport.StatusMonitor.handlers.GuiUpdateHandler;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Flotta {
    private static volatile Flotta instance = null;

    ConcurrentHashMap<String, Veicolo> veicoli;

    public static Flotta getInstance() {
        Flotta result = Flotta.instance;
        if (result == null) {
            synchronized (DBManager.class) {
                if (result == null) {
                    Flotta.instance = result = new Flotta();
                }
            }
        }
        return instance;
    }

    private Flotta() {
        veicoli = new ConcurrentHashMap<String, Veicolo>();
    }

    public void rimuoviVeicolo(String vId) {
        if (this.veicoli.containsKey(vId)) {
            this.veicoli.remove(vId);
        }
        this.updateGui();
    }

    public void aggiungiVeicolo(String vId, CheckpointSuLineaBean[] checkpoints, LocalTime time, int capolinea,
            int linea) {
        this.veicoli.put(vId, new Veicolo(checkpoints, vId, time, capolinea, linea));
        this.updateGui();
    }

    public String trovaVeicoloId(TabellaDiMarcia tabellaMarcia) {
        String vId = null;
        for (HashMap.Entry<String, Veicolo> entry : this.veicoli.entrySet()) {
            if (entry.getValue().hasThisTabellaDiMarcia(tabellaMarcia)) {
                vId = entry.getKey();
            }
        }
        return vId;
    }

    public HashMap<String, String>[] webTabellaInfo(String vId) {
        return this.veicoli.get(vId).webTabellaInfo();
    }

    public boolean nextCheckpoint(String vId) {
        boolean finished = this.veicoli.get(vId).nextCheckpoint();
        this.updateGui();
        return finished;
    }

    public void setGuasto(String vId, boolean guasto) {
        this.veicoli.get(vId).setGuasto(guasto);
        this.updateGui();
    }

    public Veicolo[] getClonedVeicoliArray() {
        Veicolo[] veicoli = this.veicoli.values().toArray(new Veicolo[0]);
        for (int i = 0; i < veicoli.length; i++) {
            veicoli[i] = veicoli[i].clone();
        }
        return veicoli;
    }

    private void updateGui() {
        GuiUpdateHandler.handleRequest();
    }
}
