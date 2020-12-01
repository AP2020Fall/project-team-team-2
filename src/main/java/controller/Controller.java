package controller;

import model.Account;

import java.util.ArrayList;
import java.util.UUID;

public class Controller {
    private static ArrayList<String> ids = new ArrayList<>();

    protected static String generateId() {
        String id = "";
        while (true) {
            id = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
            if (!doesIdExist(id)) {
                ids.add(id);
                return id;
            }
        }
    }

    public boolean isUsernameExist(String username) {
        for (Account account : Account.getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameAndPasswordMatch(String username, String password) {
        for (Account account : Account.getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
                    return true;
                } else return false;
            }
        }
        return false;
    }

    private static boolean doesIdExist(String id) {
        for (String existId : ids) {
            if (existId.equals(id)) {
                return true;
            }
        }
        return false;
    }
}
