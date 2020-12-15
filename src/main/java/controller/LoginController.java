package controller;

import model.Account;
import model.Admin;
import model.Player;
import view.AdminMainMenu;
import view.PlayerMainMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class LoginController extends Controller {
    public void delete(String username) {
        //removes the username from the list and it is a player
        Player player = Player.getPlayerByUsername(username);
        File file = new File("database" + "\\" + "accounts" + "\\" + "players" + "\\" + player.getUsername() + ".json");
        try {
            if (file.exists())
                file.delete();
        } catch (Exception ignored) {
        }
        Account.getAllAccounts().removeIf(o -> o.getUsername().equals(username) && o instanceof Player);

    }

    public void login(String username) {
        //logins into account
        //throws NullPointerException if username doesn't exist
        Account account = Objects.requireNonNull(Account.getAccountByUsername(username),
                "Username passed to LoginController.login doesn't exist.");
        if (account instanceof Admin) {
            new AdminMainMenu(account);
        } else if (account instanceof Player) {
            new PlayerMainMenu(account);
        }
    }

}
