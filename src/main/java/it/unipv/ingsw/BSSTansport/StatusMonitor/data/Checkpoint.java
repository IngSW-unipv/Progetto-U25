package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.nomeCheckBean;

import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

public class Checkpoint implements Cloneable {
    protected int id;
    protected int progressivo;
    protected LocalTime orario;

    public Checkpoint(int id, int progressivo, LocalTime orario) {
        this.id = id;
        this.progressivo = progressivo;
        this.orario = orario;
    }

    public String getNomeCheckpoint() {
        return NomiCheckpoint.getNome(this.id);
    }

    public Checkpoint clone() {
        return new Checkpoint(this.id, this.progressivo, this.orario);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgressivo() {
        return progressivo;
    }

    public void setProgressivo(int progressivo) {
        this.progressivo = progressivo;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }
}
