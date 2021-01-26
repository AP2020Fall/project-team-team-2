package controller.login;

import controller.Controller;
import main.ClientInfo;
import model.Account;
import model.Admin;
import model.Player;

import java.util.Objects;

public class LoginController extends Controller {
    private ClientInfo clientInfo;
    public LoginController(ClientInfo clientInfo) {
        super(clientInfo);
        this.clientInfo = clientInfo;
    }

    public void delete(String username) {
        //removes the username from the list and it is a player
        //throws NullPointerException if username is not a player
        Player player =Objects.requireNonNull( Player.getPlayerByUsername(username),
                "Username passed to LoginController.delete isn't a player or doesn't exist.");
        player.delete();
    }

    public Boolean login(String username) {
        //logins into account
        //throws NullPointerException if username doesn't exist
       Account loggedIn = Objects.requireNonNull(Account.getAccountByUsername(username),
                "Username passed to LoginController.login doesn't exist.");
        if(clientInfo != null) {
            clientInfo.setLoggedInUsername(loggedIn.getUsername());
        }
        if (loggedIn instanceof Admin) {
            //new AdminMainMenuLayoutController().login((Admin) loggedIn);
            return true;
        } else if (loggedIn instanceof Player) {
            //new PlayerMainMenuLayoutController().login((Player) loggedIn);
            return false;
        }
        return false;
    }

}
