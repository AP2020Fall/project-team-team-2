package controller;

import model.Account;
import model.GameLog;
import model.Player;
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
        //returns array of string in the following order, first name, last name,username,email,phone number,
        // day passed since registration if the account is Player
        ArrayList<String> result = new ArrayList<>();
        result.add(account.getFirstName());
        result.add(account.getLastName());
        result.add(account.getUsername());
        result.add(account.getEmail());
        result.add(account.getPhoneNumber());
        if (account instanceof Player)
        result.add(String.valueOf(((Player) account).getDayOfRegister()));
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

    public ArrayList<Integer> viewPlatoStatistics() {
        //returns an arraylist of Integer including number of friends,number of wins,number of days passed in that order
        ArrayList<Integer> result = new ArrayList<>();
        if(account instanceof Player) //must be checked in view
        {
            result.add(((Player) account).getFriends().size());
            result.add(((Player) account).getNumberOfWins());
            result.add(((Player) account).getDayOfRegister());
        }
        return result;
    }

    public void showGamesHistory() {


    }

    public ArrayList<String> showGameStatistics(String gameName) {
        //check in view that the game exist otherwise NullPointerException will occur
        //returns arraylist of String, frequency, wins, losses
        ArrayList<String> result = new ArrayList<>();
        if(account instanceof Player)//must be checked in view
        {
            GameLog gameLog = ((Player) account).getGameHistory(gameName);
            result.add(String.valueOf(gameLog.getFrequency()));
            result.add(String.valueOf(gameLog.getWins()));
            result.add(String.valueOf(gameLog.getLosses()));
            return result;

        }
        return null;
    }
    public boolean playerHasGame(String gameName)
    {
        if(account instanceof Player)//must be checked in view
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
