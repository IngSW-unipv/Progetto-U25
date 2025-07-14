package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;

import java.time.LocalTime;

public class Treno extends Veicolo {
    TipiTreno tipo;

    public Treno(CheckpointSuLineaBean[] checkpoints, LocalTime time, int capolinea, int linea, String tipo) throws IllegalArgumentException {
        super(checkpoints, time, capolinea, linea);

        this.tipo = TipiTreno.valueOf(tipo);
    }


    enum TipiTreno{
        LOCALE,
        REGIONALE,
        ESPRESSO
    }
}