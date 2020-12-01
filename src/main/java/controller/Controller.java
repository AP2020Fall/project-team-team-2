package controller;

import com.google.gson.internal.$Gson$Preconditions;
import model.Account;

import java.util.ArrayList;
import java.util.UUID;

public class Controller {
    private static ArrayList<String> ids = new ArrayList<>();
    protected static String generateId() {
        String id = "";
        while (true){
            id = UUID.randomUUID().toString().toUpperCase().substring(0 , 8);
            if (!doesIdExist(id)){
                ids.add(id);
                return id;
            }
        }
    }

    private static boolean doesIdExist(String id) {
        for (String existId : ids) {
            if (existId.equals(id)){
                return true;
            }
        }
        return false;
    }
}
