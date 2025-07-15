package it.unipv.ingsw.BSSTansport.StatusMonitor.handlers;

import it.unipv.ingsw.BSSTansport.StatusMonitor.GUI.IntermediarioFlottaTabella;
import it.unipv.ingsw.BSSTansport.StatusMonitor.GUI.TabellaVeicoli;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Flotta;
import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Veicolo;

public class GuiUpdateHandler implements Handler {
    private static TabellaVeicoli gui;

    public static void handleRequest(){
        Veicolo[] veicoli = Flotta.getInstance().getClonedVeicoliArray();
        gui.update(veicoli);
    };

    public static TabellaVeicoli getGui() {
        return gui;
    }

    public static void setGui(TabellaVeicoli gui) {
        GuiUpdateHandler.gui = gui;
    }
}
