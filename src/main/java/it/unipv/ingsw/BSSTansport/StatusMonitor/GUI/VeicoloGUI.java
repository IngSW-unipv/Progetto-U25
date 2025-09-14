package it.unipv.ingsw.BSSTansport.StatusMonitor.GUI;

import it.unipv.ingsw.BSSTansport.StatusMonitor.data.TabellaDiMarcia;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Veicolo;

import java.time.Duration;

public class VeicoloGUI {
    private StatiRitardo stato;
    private String veicolo;
    private Integer linea;
    private String ultimaFermata;
    private String prossimaFermata;
    private String ritardo;
    private String icona;

    protected VeicoloGUI(Veicolo original) {
        TabellaDiMarcia tm = original.getTabellaMarcia();
        Duration ritardo = tm.getRitardo();
        this.linea = tm.getLinea();
        this.ultimaFermata = tm.getCurrentCheckpointName();
        this.prossimaFermata = tm.getNextCheckpointName();
        this.veicolo = original.getId();

        if (original.isGuasto()) {
            this.stato = StatiRitardo.GUASTO;
            this.icona = "⚠\uFE0F";
        } else if (ritardo.isPositive()) {
            this.stato = StatiRitardo.RITARDO;
            this.icona = "⏪";
        } else if (ritardo.isNegative()) {
            this.stato = StatiRitardo.ANTICIPO;
            this.icona = "⏩";
        } else {
            this.stato = StatiRitardo.ORARIO;
            this.icona = "\uD83D\uDD3D"; //freccia giù
        }

        this.ritardo = ritardo.toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();
        ;
    }

    public Object[] aRiga() {
        return new Object[]{icona, stato, veicolo, linea, ultimaFermata, prossimaFermata, ritardo};
    }

    public boolean isGuasto() {
        return this.stato == StatiRitardo.GUASTO;
    }

    enum StatiRitardo {
        ANTICIPO,
        ORARIO,
        RITARDO,
        GUASTO
    }
}
