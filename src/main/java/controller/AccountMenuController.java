package controller;

import model.Account;
import model.GameLog;
import model.Player;
import view.WelcomeMenu;

import java.util.ArrayList;
import java.util.Objects;

public class AccountMenuController extends Controller {
    private Account account;

    public AccountMenuController(Account account) {

        this.account = Objects.requireNonNull(account, "Account passed to AccountMenuController is null.");
    }

    public void changePassword(String newPass) {
        account.setPassword(newPass);
    }

    public String showPersonalInfo() {
        //returns array of string in the following order, first name, last name,username,email,phone number
        //and day passed since registration if the account is Player.
       return account.toString();
    }

    public void editField(String field, String value) {
        //sets field to value.
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

    public ArrayList<Integer> viewPlatoStatistics() {
        //returns an arraylist of Integer including number of friends,number of wins,number of days passed in that order.
        ArrayList<Integer> result = new ArrayList<>();
        if (account instanceof Player) //must be checked in view
        {
            result.add(((Player) account).getFriends().size());
            result.add(((Player) account).getNumberOfWins());
            result.add(((Player) account).getDayOfRegister());
        }
        return result;
    }

    public ArrayList<String> showGamesHistory() {
        return null;
    }

    public ArrayList<String> showGameStatistics(String gameName) {
        //returns arraylist of String, frequency, wins, losses
        //throws NullPointerException if players hasn't played gameName
        ArrayList<String> result = new ArrayList<>();
        if (account instanceof Player)//must be checked in view
        {
            GameLog gameLog = Objects.requireNonNull(((Player) account).getGameHistory(gameName),
                    "Game passed to AccountMenuController.showGameStatistics hasn't been played.");
            result.add(String.valueOf(gameLog.getFrequency()));
            result.add(String.valueOf(gameLog.getWins()));
            result.add(String.valueOf(gameLog.getLosses()));
            return result;

        }
        return null;
    }

    public boolean playerHasGame(String gameName) {
        //returns true if player has played gameName, false otherwise.
        if (account instanceof Player)//must be checked in view
            return ((Player) account).getGameHistory(gameName) != null;
        return false;
    }

    public void logOut() {
        new WelcomeMenu(null);
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
