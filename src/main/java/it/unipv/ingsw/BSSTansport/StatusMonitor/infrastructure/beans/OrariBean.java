package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans;

import it.unipv.ingsw.BSSTansport.StatusMonitor.data.NomiCheckpoint;

import java.time.LocalTime;

public class OrariBean {
    private LocalTime orario;
    private int capolineaID;
    private String nomeCapolinea;

    public OrariBean(LocalTime orario, int capolineaID) {
        this.orario = orario;
        this.capolineaID = capolineaID;
        this.setNomeCapolinea(capolineaID);
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }

    public int getCapolinea() {
        return capolineaID;
    }

    public void setCapolinea(int capolineaID) {
        this.capolineaID = capolineaID;
        this.setNomeCapolinea(capolineaID);
    }

    public String getNomeCapolinea() {
        return nomeCapolinea;
    }

    private void setNomeCapolinea(int capolineaID) {
        this.nomeCapolinea = NomiCheckpoint.getNome(capolineaID);
    }
}
