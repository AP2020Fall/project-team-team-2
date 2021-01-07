package controller;

import model.Account;
import model.Admin;
import model.Player;

import java.util.Objects;

public class LoginController extends Controller {
    public void delete(String username) {
        //removes the username from the list and it is a player
        //throws NullPointerException if username is not a player
        Player player =Objects.requireNonNull( Player.getPlayerByUsername(username),
                "Username passed to LoginController.delete isn't a player or doesn't exist.");
        player.delete();
    }

    public boolean login(String username) {
        //logins into account
        //throws NullPointerException if username doesn't exist
        loggedIn = Objects.requireNonNull(Account.getAccountByUsername(username),
                "Username passed to LoginController.login doesn't exist.");
        if (loggedIn instanceof Admin) {
            //new AdminMainMenu(loggedIn);
            return true;
        } else if (loggedIn instanceof Player) {
            return false;
        }
        return false;
    }

}
