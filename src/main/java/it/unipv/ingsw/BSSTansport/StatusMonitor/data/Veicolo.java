package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;

import java.time.LocalTime;
import java.util.HashMap;

public class Veicolo {
    private boolean guasto;
    private TabellaDiMarcia tabellaMarcia;

    public Veicolo(CheckpointSuLineaBean[] checkpoints, LocalTime time, int capolinea, int linea) {
        this.guasto = false;

        this.tabellaMarcia = new TabellaDiMarcia(checkpoints, time, capolinea, linea);
    }

    public boolean hasThisTabellaDiMarcia(TabellaDiMarcia tabellaMarcia) {
        return tabellaMarcia==this.tabellaMarcia;
    }

    public HashMap<String,String>[] webTabellaInfo(){
        return this.tabellaMarcia.webTabellaInfo();
    }

    public boolean nextCheckpoint(){
        if(this.guasto){
            this.guasto = false;
        }
        return this.tabellaMarcia.nextCheckpoint();
    }


    public boolean isGuasto() {
        return guasto;
    }

    public void setGuasto(boolean guasto) {
        this.guasto = guasto;
    }
}
