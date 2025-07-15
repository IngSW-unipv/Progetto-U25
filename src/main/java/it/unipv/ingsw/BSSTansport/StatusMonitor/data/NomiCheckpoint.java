package it.unipv.ingsw.BSSTansport.StatusMonitor.data;

import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.DBManager;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.nomeCheckBean;

import java.util.concurrent.ConcurrentHashMap;

public class NomiCheckpoint {
    private static ConcurrentHashMap<Integer, String> nomi;

    public static String getNome(int id){
        if (NomiCheckpoint.nomi==null)
        {
            NomiCheckpoint.nomi = getNomi();
        }
        if (id<0 || id>=NomiCheckpoint.nomi.size())
        {
            return "";
        }
        return nomi.get(id);
    }

    private static ConcurrentHashMap<Integer, String> getNomi(){
        ConcurrentHashMap<Integer, String> nomi= new ConcurrentHashMap<Integer, String>();

        nomeCheckBean[] nomiArr = DBManager.getInstance().getCheckpointNames();

        for(nomeCheckBean e:nomiArr){
            nomi.put(e.getId(), e.getNome());
        }

        return nomi;
    }
}
