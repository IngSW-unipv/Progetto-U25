package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import java.time.Duration;
import java.time.LocalTime;

public class Stazione extends Checkpoint{
    private int lunghezza;
    private Duration durata;

    public Stazione(int id, int progressivo, LocalTime orario, int Lunghezza, Duration durata) {
        super(id, progressivo, orario);

        this.lunghezza = Lunghezza;
        this.durata = durata;
    }
}
