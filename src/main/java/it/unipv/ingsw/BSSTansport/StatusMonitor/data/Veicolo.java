package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;

import java.time.LocalTime;
import java.util.HashMap;

public class Veicolo implements Cloneable {
    private boolean guasto;
    private final String id;
    private TabellaDiMarcia tabellaMarcia;

    public Veicolo(CheckpointSuLineaBean[] checkpoints, String id, LocalTime time, int capolinea, int linea) {
        this.guasto = false;
        this.id = id;
        this.tabellaMarcia = new TabellaDiMarcia(checkpoints, time, capolinea, linea);
    }

    public Veicolo(boolean guasto, String id, TabellaDiMarcia tabellaMarcia) {
        this.guasto = guasto;
        this.id = id;
        this.tabellaMarcia = tabellaMarcia;
    }

    public boolean hasThisTabellaDiMarcia(TabellaDiMarcia tabellaMarcia) {
        return tabellaMarcia == this.tabellaMarcia;
    }

    public HashMap<String, String>[] webTabellaInfo() {
        return this.tabellaMarcia.webTabellaInfo();
    }

    public boolean nextCheckpoint() {
        if (this.guasto) {
            this.guasto = false;
        }
        return this.tabellaMarcia.nextCheckpoint();
    }

    public void setGuasto(boolean guasto) {
        this.guasto = guasto;
    }

    public TabellaDiMarcia getTabellaMarcia() {
        return tabellaMarcia;
    }

    public String getId() {
        return id;
    }

    public boolean isGuasto() {
        return guasto;
    }

    public Veicolo clone() {
        return new Veicolo(this.guasto, this.id, this.tabellaMarcia.clone());
    }
}
