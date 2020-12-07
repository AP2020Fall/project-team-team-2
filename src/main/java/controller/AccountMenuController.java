package controller;

import model.Account;
import view.WelcomeMenu;

import java.util.ArrayList;

public class AccountMenuController extends Controller {
    private Account account;

    public AccountMenuController(Account account) {
        this.account = account;
    }

    public void changePassword(String currentPass, String newPass) {
        account.setPassword(newPass);
    }

    public ArrayList<String> showPersonalInfo() {
        //returns array of string in the following order, first name, last name,username,email,phone number, accountId
        ArrayList<String> result = new ArrayList<>();
        result.add(account.getFirstName());
        result.add(account.getLastName());
        result.add(account.getUsername());
        result.add(account.getEmail());
        result.add(account.getPhoneNumber());
        result.add(account.getAccountId());
        return result;
    }

    public void editField(String field, String value) {
        if (field.equals("firstName")) {
            account.setFirstName(value);
        } else if (field.equals("lastName")) {
            account.setLastName(value);
        } else if (field.equals("email")) {
            account.setEmail(value);
        } else if (field.equals("phoneNumber")) {
            account.setPhoneNumber(value);
        } else if (field.equals("username")) {
            account.setUsername(value);
        }
    }

    public void viewPlatoStatistics() {
    }

    public void showGamesHistory() {
    }

    public void showGameStatistics(String gameName) {
    }

    public void logOut() {
        new WelcomeMenu(null);
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
