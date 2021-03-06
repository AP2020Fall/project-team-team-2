package controller.login;

import com.google.gson.Gson;
import controller.Controller;
import controller.ServerMasterController.ServerMasterController;
import main.ClientInfo;
import main.Token;
import model.Account;
import model.Admin;
import model.Player;

import java.util.Objects;

public class LoginController extends Controller {
    private ClientInfo clientInfo;
    private Token token;

    public LoginController(ClientInfo clientInfo) {
        super(clientInfo);
        this.clientInfo = clientInfo;
        this.token = ServerMasterController.getCurrentToken();
    }

    public void delete(String username) {
        //removes the username from the list and it is a player
        //throws NullPointerException if username is not a player
        Player player = Objects.requireNonNull(Player.getPlayerByUsername(username),
                "Username passed to LoginController.delete isn't a player or doesn't exist.");
        player.delete();
    }

    public Boolean login(String username) {
        //logins into account
        //throws NullPointerException if username doesn't exist
        Account loggedIn = Account.getAccountByUsername(username);
        if(loggedIn == null) {
            System.err.println("Username passed to LoginController.login doesn't exist.");
            return false;
        }
        //if (clientInfo != null) {
        clientInfo.setLoggedInUsername(loggedIn.getUsername());
        token.setLogin(loggedIn);
        loggedIn.setStatus("Online");
        // }
        if (loggedIn instanceof Admin) {
            //new AdminMainMenuLayoutController().login((Admin) loggedIn);
            return true;
        } else if (loggedIn instanceof Player) {
            //new PlayerMainMenuLayoutController().login((Player) loggedIn);
            return false;
        }
        return false;
    }

    public void logout() {
        Account loggedIn = Account.getAccountByUsername(clientInfo.getLoggedInUsername());
        if(loggedIn != null) loggedIn.setStatus("Offline");
        clientInfo.unsetLoggedInUsername();
        token.setLogout();
        ServerMasterController.logout();
    }

}