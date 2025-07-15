package it.unipv.ingsw.BSSTansport.StatusMonitor.GUI;

import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Flotta;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Veicolo;
import it.unipv.ingsw.BSSTansport.StatusMonitor.handlers.GuiUpdateHandler;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class IntermediarioFlottaTabella {
    protected VeicoloGUI[] veicoli;

    public synchronized void update(Veicolo[] nuoviVeicoli) {
        ArrayList<VeicoloGUI> veicoli = new ArrayList<VeicoloGUI>();
        for (Veicolo v: nuoviVeicoli) {
            veicoli.add(new VeicoloGUI(v));
        }

        this.veicoli = veicoli.toArray(new VeicoloGUI[0]);
    }
}
