package controller;

import model.Account;
import model.Admin;
import model.Player;
import view.AdminMainMenu;
import view.PlayerMainMenu;

import java.util.ArrayList;

public class LoginController extends Controller {
    public void delete(String username) {
        ArrayList<Account> allAccounts = Account.getAllAccounts();
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                allAccounts.remove(account);
                return;
            }
        }
    }

    public void login(String username) {
        Account account = Account.getAccountByUsername(username);
        if (account != null && account instanceof Admin) {
            new AdminMainMenu(account);
        } else if (account != null && account instanceof Player) {
            new PlayerMainMenu(account);
        }
    }

}
