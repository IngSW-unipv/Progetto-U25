package it.unipv.ingsw.BSSTansport.StatusMonitor.GUI;

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
        Duration ritardo = original.getTabellaMarcia().getRitardo();
        this.linea = original.getTabellaMarcia().getLinea();
        this.ultimaFermata=original.getTabellaMarcia().getCurrentCheckpointName();
        this.prossimaFermata=original.getTabellaMarcia().getNextCheckpointName();
        this.veicolo= original.getId();

        if(original.isGuasto()){
            this.stato = StatiRitardo.GUASTO;
            this.icona = "⚠\uFE0F";
        }
        else if (ritardo.isPositive()){
            this.stato = StatiRitardo.RITARDO;
            this.icona = "⏪";
        }
        else if (ritardo.isNegative()){
            this.stato = StatiRitardo.ANTICIPO;
            this.icona = "⏩";
        }
        else {
            this.stato = StatiRitardo.ORARIO;
            this.icona = "\uD83D\uDD3D";
        }

        this.ritardo=ritardo.toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();;
     }

    public Object[] aRiga() {
        return new Object[]{icona, stato, veicolo, linea, ultimaFermata, prossimaFermata, ritardo};
    }

    enum StatiRitardo{
        ANTICIPO,
        ORARIO,
        RITARDO,
        GUASTO
    }
}
