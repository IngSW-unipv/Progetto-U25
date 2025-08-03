package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import it.unipv.ingsw.BSSTansport.StatusMonitor.handlers.FineLineaHandler;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TabellaDiMarcia implements Cloneable{
    private int linea;
    private Duration ritardo;
    private int ultimoCheck; //valore 'progressivo' dell'ultimo checkpoint visitato dal veicolo
    private Checkpoint[] checkpoints;

    public TabellaDiMarcia(CheckpointSuLineaBean[] checkpointBeans, LocalTime time, int capolinea, int linea) {
        this.linea = linea;
        this.ultimoCheck = 0;
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

    private TabellaDiMarcia(int linea, Duration ritardo, int ultimoCheck, Checkpoint[] checkpoints) {
        this.linea = linea;
        this.ritardo = ritardo;
        this.ultimoCheck = ultimoCheck;
        this.checkpoints = checkpoints;
    }

    public boolean nextCheckpoint() {  //ritorna true se il viaggio è terminato
        //aggiorna ultimo checkpoint
        this.ultimoCheck++;

        //calcola l'indice della stazione corrente nella lista
        int currentIndex = getCheckpointIndexFromProgressivo(this.ultimoCheck);

        //aggiorna ritardo
        this.ritardo = Duration.between(this.checkpoints[currentIndex].getOrario(), LocalTime.now()).truncatedTo(ChronoUnit.MINUTES);

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

            if (index==-1){ //se il campo è vuoto lo segnala e continua
                output[i].put("tipo", "estremo");
                continue;
            }

            //se il campo non è vuoto aggiunge tutti i dettagli

            output[i].put("nome", this.checkpoints[index].getNomeCheckpoint());
            output[i].put("orarioPianificato", this.checkpoints[index].getOrario().toString());

            if (this.checkpoints[index].getClass()==Stazione.class) { //aggiungi i campi extra se è una stazione
                Stazione s = (Stazione) this.checkpoints[index];

                output[i].put("tipo", "stazione");
                output[i].put("lunghezza", s.getLunghezza().toString());
                output[i].put("attesa", String.valueOf(s.getDurata().toSeconds()));
            }
            else{
                output[i].put("tipo", "checkpoint");
            }
        }

        return output;
    }

    public Duration getRitardo() {
        return ritardo;
    }

    public int getLinea() {
        return linea;
    }

    public String getCurrentCheckpointName() {
        int index = getCheckpointIndexFromProgressivo(this.ultimoCheck);
        return getCheckpointName(index);
    }

    public String getNextCheckpointName() {
        int index = getCheckpointIndexFromProgressivo(this.ultimoCheck+1);
        return getCheckpointName(index);
    }

    public String getCheckpointName(int index) {
        return index == -1 ? "" : this.checkpoints[index].getNomeCheckpoint();
    }

    public TabellaDiMarcia clone(){
        Checkpoint[] checkpoints = new Checkpoint[this.checkpoints.length];
        for (int i = 0; i < this.checkpoints.length; i++) {
            checkpoints[i] = this.checkpoints[i].clone();
        }
        return new TabellaDiMarcia(this.linea, this.ritardo, this.ultimoCheck, checkpoints);
    }

}