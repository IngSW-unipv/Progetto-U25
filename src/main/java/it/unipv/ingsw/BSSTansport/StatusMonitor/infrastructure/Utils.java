package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure;

import io.javalin.http.Context;

import java.util.HashMap;

public class Utils {
    public static boolean checkContextFields(HashMap<String, String> request, String[] requiredFields)
            throws Exception {
        for (String fieldName : requiredFields) {
            if (!request.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }

    public static Integer arrayIndexOf(Object[] array, Object element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
}
