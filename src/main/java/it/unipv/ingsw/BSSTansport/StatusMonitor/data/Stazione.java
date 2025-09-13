package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import java.time.Duration;
import java.time.LocalTime;

public class Stazione extends Checkpoint {
    private Integer lunghezza;
    private Duration durata;

    public Stazione(int id, int progressivo, LocalTime orario, int Lunghezza, Duration durata) {
        super(id, progressivo, orario);

        this.lunghezza = Lunghezza;
        this.durata = durata;
    }

    public Stazione clone() {
        return new Stazione(super.id, super.progressivo, super.orario, this.lunghezza, this.durata);
    }

    public Duration getDurata() {
        return durata;
    }

    public Integer getLunghezza() {
        return lunghezza;
    }
}
