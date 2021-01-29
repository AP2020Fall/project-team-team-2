package controller.login;


import controller.Controller;
import main.ClientInfo;
import model.Account;
import model.Admin;
import model.Player;

import java.util.ArrayList;

public class RegisterController extends Controller {

    private final ClientInfo clientInfo;

    public RegisterController(ClientInfo clientInfo) {
        super(clientInfo);
        this.clientInfo = clientInfo;
    }

    public boolean createAccount(String username, String password, ArrayList<String> additionalInfo) {
        //creates an account and loads either AdminMainMenu or PlayerMainMenu
        if (!Admin.isAdminExist()) {
            clientInfo.setLoggedInUsername(createAdmin(username, password, additionalInfo).getUsername());
            return true;
        } else {
            clientInfo.setLoggedInUsername(createPlayer(username, password, additionalInfo).getUsername());
            //ViewHandler.getViewHandler().push(new PlayerMainMenu());
            return false;
        }

    }

    private Player createPlayer(String username, String password, ArrayList<String> additionalInfo) {
        //creates a player object and returns it
        Player player = new Player(additionalInfo.get(0), additionalInfo.get(1), username, generateId(),
                password, additionalInfo.get(3), additionalInfo.get(2),
                Double.parseDouble(additionalInfo.get(4)));
        Account.getAllAccounts().add(player);
        return player;
    }

    private Admin createAdmin(String username, String password, ArrayList<String> additionalInfo) {
        //creates an admin object and returns it
        Admin admin = new Admin(additionalInfo.get(0), additionalInfo.get(1), username,
                generateId(), password, additionalInfo.get(3), additionalInfo.get(2));
        Account.getAllAccounts().add(admin);
        return admin;
    }


}
