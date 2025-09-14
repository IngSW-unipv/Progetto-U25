package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure;

import io.javalin.http.Context;

import java.util.HashMap;

public class Utils {
    /**
     * checks if a hashmap has the required keys
     */
    public static boolean checkContextFields(HashMap<String, String> request, String[] requiredFields)

            throws Exception {
        for (String fieldName : requiredFields) {
            if (!request.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given an array of objects X and an object i, returns the index of i in X (or -1 if i is not in X)
     */
    public static Integer arrayIndexOf(Object[] array, Object element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
}
