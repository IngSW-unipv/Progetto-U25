package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure;

import io.javalin.http.Context;

import java.util.HashMap;

public class Utils {
    public static boolean checkContextFields(HashMap<String, String> request, String[] requiredFields) throws Exception{
        for (String fieldName:requiredFields){
            if (!request.containsKey(fieldName)){
                return false;
            }
        }
        return true;
    }
}
