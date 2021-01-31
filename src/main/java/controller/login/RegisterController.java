package controller.login;


import controller.Controller;
import controller.ServerMasterController.ServerMasterController;
import main.ClientInfo;
import main.Token;
import model.Account;
import model.Admin;
import model.Player;

import java.util.ArrayList;

public class RegisterController extends Controller {

    private final ClientInfo clientInfo;
    private final Token token;

    public RegisterController(ClientInfo clientInfo) {
        super(clientInfo);
        this.token = ServerMasterController.getCurrentToken();
        this.clientInfo = clientInfo;
    }

    public boolean createAccount(String username, String password, ArrayList<String> additionalInfo) {
        //creates an account and loads either AdminMainMenu or PlayerMainMenu
        if (!Admin.isAdminExist()) {
            Admin admin = createAdmin(username, password, additionalInfo);
            clientInfo.setLoggedInUsername(admin.getUsername());
            token.setLogin(admin);
            return true;
        } else {
            Player player = createPlayer(username, password, additionalInfo);
            clientInfo.setLoggedInUsername(player.getUsername());
            token.setLogin(player);
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
