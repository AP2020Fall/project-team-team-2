package controller;

import model.Account;

public class AccountMenuController extends Controller {
    private Account account;

    public AccountMenuController(Account account) {
        this.account = account;
    }

    public void changePassword(String currentPass, String newPass) {
        account.setPassword(newPass);
    }

    public void showPersonalInfo() {
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
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
