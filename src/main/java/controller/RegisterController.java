package controller;


import model.Account;
import model.Admin;
import model.Player;

import java.util.ArrayList;

public class RegisterController extends Controller {


    public void createAccount(String username, String password, ArrayList<String> additionalInfo) {
        if (!Admin.isAdminExist()) {
            createAdmin(username, password, additionalInfo);
            //go to admin account menu
        } else {
            createPlayer(username, password, additionalInfo);
            //go to player account menu
        }
    }

    private void createPlayer(String username, String password, ArrayList<String> additionalInfo) {
        Player player = new Player(additionalInfo.get(0), additionalInfo.get(1), username, generateId(), password, additionalInfo.get(2), additionalInfo.get(3), 0, Double.parseDouble(additionalInfo.get(4)), 0);
        Account.getAllAccounts().add(player);
    }

    private void createAdmin(String username, String password, ArrayList<String> additionalInfo) {
        Admin admin = new Admin(additionalInfo.get(0), additionalInfo.get(1), username, generateId(), password, additionalInfo.get(2), additionalInfo.get(3));
        Account.getAllAccounts().add(admin);
    }
}
