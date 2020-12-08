package controller;

import model.Account;
import model.Admin;
import model.Player;
import view.AdminMainMenu;
import view.PlayerMainMenu;

import java.util.ArrayList;
import java.util.Objects;

public class LoginController extends Controller {
    public void delete(String username) {
        //removes the username from the list
        Account.getAllAccounts().removeIf(o -> o.getUsername().equals(username));
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
