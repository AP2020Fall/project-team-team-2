package controller;


import model.Account;
import model.Admin;
import model.Player;

import java.util.ArrayList;

public class RegisterController extends Controller {


    public boolean createAccount(String username, String password, ArrayList<String> additionalInfo) {
        //creates an account and loads either AdminMainMenu or PlayerMainMenu
        if (!Admin.isAdminExist()) {
            loggedIn = createAdmin(username, password, additionalInfo);
            return true;
        } else {
            loggedIn = createPlayer(username, password, additionalInfo);
            //ViewHandler.getViewHandler().push(new PlayerMainMenu());
            return false;
        }
    }

    private Player createPlayer(String username, String password, ArrayList<String> additionalInfo) {
        //creates a player object and returns it
        Player player = new Player(additionalInfo.get(0), additionalInfo.get(1), username, generateId(),
                password, additionalInfo.get(2), additionalInfo.get(3),
                Double.parseDouble(additionalInfo.get(4)));
        Account.getAllAccounts().add(player);
        return player;
    }

    private Admin createAdmin(String username, String password, ArrayList<String> additionalInfo) {
        //creates an admin object and returns it
        Admin admin = new Admin(additionalInfo.get(0), additionalInfo.get(1), username,
                generateId(), password, additionalInfo.get(2), additionalInfo.get(3));
        Account.getAllAccounts().add(admin);
        return admin;
    }
}
