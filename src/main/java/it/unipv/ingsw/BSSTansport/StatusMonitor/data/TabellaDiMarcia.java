package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import it.unipv.ingsw.BSSTansport.StatusMonitor.handlers.FineLineaHandler;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TabellaDiMarcia {
    private int linea;
    private Duration ritardo;
    private int ultimoCheck; //valore 'progressivo' dell'ultimo checkpoint visitato dal veicolo
    private Checkpoint[] checkpoints;

    public TabellaDiMarcia(CheckpointSuLineaBean[] checkpointBeans, LocalTime time, int capolinea, int linea) {
        this.linea = linea;
        ultimoCheck = 0;
        this.ritardo = Duration.between(time, LocalTime.now());
        if (this.ritardo.compareTo(Duration.ZERO) < 0) {
            this.ritardo = Duration.ZERO;
        }

        //ordina checkpoints in base al loro ordine di fermata sulla linea
        Arrays.sort(checkpointBeans);
        //controlla direzione linea
        int lunghezzaBean = checkpointBeans.length;
        if (capolinea == checkpointBeans[0].getNumero()) { //inverti l'ordine delle stazioni se il capolinea è il primo elemento
            for (int i = 0; i < lunghezzaBean; i++) {

                //inverti il l'ordine
                checkpointBeans[i].setNumero(Math.abs(checkpointBeans[i].getNumero() - lunghezzaBean - 1));

                //scala i tempi avanti di una posizione (il primo diventa 0)
                int j = lunghezzaBean - 1 - i;
                if (j >= 1) {
                    checkpointBeans[j].setDelayS(checkpointBeans[j - 1].getDelayS());
                } else if (j == 0) {
                    checkpointBeans[j].setDelayS(0);
                }

            }
            Arrays.sort(checkpointBeans);
        }

        //crea l'attributo checkpoints
        ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
        for (int i = 0; i < lunghezzaBean; i++) {
            CheckpointSuLineaBean c = checkpointBeans[i];
            LocalTime arrivo = null;
            if (i == 0) {
                arrivo = time;
            } else {
                arrivo = checkpoints.get(i - 1).getOrario().plus(Duration.ofSeconds(checkpointBeans[i - 1].getDelayS()));
            }

            if (c.getLunghezza() == null) {
                checkpoints.add(new Checkpoint(c.getCheckpoint(), c.getNumero(), arrivo));
            } else {
                checkpoints.add(new Stazione(c.getCheckpoint(), c.getNumero(), arrivo, c.getLunghezza(), Duration.ofSeconds(c.getDurataFermataS())));
            }
        }

        this.checkpoints = checkpoints.toArray(new Checkpoint[0]);
    }

    public boolean nextCheckpoint() {  //return finished trip value
        int currentIndex = getCheckpointIndexFromProgressivo(this.ultimoCheck);

        //aggiorna ritardo
        if (this.ultimoCheck != 0) {
            this.ritardo = Duration.between(this.checkpoints[currentIndex].getOrario(), LocalTime.now());
        }

        //aggiorna ultimo checkpoint
        this.ultimoCheck++;

        //controlla se il veicolo ha completato la linea (il progressivo più alto deve essere uguale alla lunghezza dell'array)
        if (ultimoCheck > this.checkpoints.length-1) {
            this.fineLinea();
            return true;
        }
        return false;
    }

    public int getCheckpointIndexFromProgressivo(int progressivo) {
        for (int i = 0; i < this.checkpoints.length; i++) {
            if (checkpoints[i].getProgressivo() == progressivo) return i;
        }
        return -1;
    }

    public void fineLinea() {
        FineLineaHandler.handeRequest(this);
    }

    public HashMap<String, String>[] webTabellaInfo() {
        HashMap<String, String>[] output = new HashMap[2];

        for (int i = 0; i < 2; i++) {
            output[i] = new HashMap<String, String>();
            int index = getCheckpointIndexFromProgressivo(this.ultimoCheck+i);
            output[i].put("nome", index == -1 ? "" : this.checkpoints[index].getNomeCheckpoint());
            output[i].put("orarioPianificato", index == -1 ? "" : this.checkpoints[index].getOrario().toString());
        }

        return output;
    }

}