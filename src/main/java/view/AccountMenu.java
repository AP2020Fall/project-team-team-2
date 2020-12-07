package view;

import controller.AccountMenuController;
import model.Account;
import model.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AccountMenu extends Menu {
    AccountMenuController controller;

    public AccountMenu(Account account) {
        super(account);
        controller = new AccountMenuController(account);
        accountMenu();
    }

    private void accountMenu() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^View personal info$").find()) {
                viewPersonalInfo();
            } else if ((matcher = getMatcher(input, "^Change password (\\S+), (\\S+)$")).find()) {
                changePassword(matcher.group(1), matcher.group(2));
            } else if ((matcher = getMatcher(input, "^Edit (\\S+), (\\S+)$")).find()) {
                editField(matcher.group(1), matcher.group(2));
            } else if (getMatcher(input, "^View plato statistics$").find()) {
                viewPlatoStatistics();
            } else if (getMatcher(input, "^Games history$").find()) {
                gameHistory();
            } else if ((matcher = getMatcher(input, "^Game statistics (\\S+)$")).find()) {
                gameStatistics(matcher.group(1));
            } else if (getMatcher(input, "^Logout$").find()) {
                logout();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void logout() {
        new WelcomeMenu(null);
    }

    private void gameStatistics(String gameName) {
        if (!gameName.equals("RiskGame")) {
            System.out.println("Game dose not exists!");
        } else {
            ArrayList<String> results = controller.showGameStatistics(gameName);
            System.out.println("frequency: " + results.get(0) +
                    "\nwins: " + results.get(1) +
                    "\nlosses: " + results.get(2));
        }
    }

    private void gameHistory() {
    }

    private void viewPlatoStatistics() {
        if (account instanceof Player) {
            ArrayList<Integer> results = controller.viewPlatoStatistics();
            System.out.println("number of friends: " + results.get(0) +
                    "\nnumber of wins: " + results.get(1) +
                    "\nnumber of day passed: " + results.get(2));
        } else {
            System.out.println("you are not a player!");
        }
    }

    private void editField(String field, String newValue) {
        if (!isFieldCanChange(field)) {
            System.out.println("invalid field or this field is not changeable");
            return;
        }
        if (field.equals("email") && !checkEmail(newValue)) {
            System.out.println("invalid email");
            return;
        }
        if (field.equals("phoneNumber") && !checkPhoneNumber(newValue)) {
            System.out.println("invalid phone number");
            return;
        }
        if (field.equals("username") && controller.isUsernameExist(newValue)) {
            System.out.println("this username exists");
            return;
        }
        controller.editField(field, newValue);

    }

    private boolean isFieldCanChange(String field) {
        if (field.equals("firstName") || field.equals("lastName") || field.equals("email") || field.equals("phoneNumber") || field.equals("username")) {
            return true;
        } else {
            return false;
        }
    }

    private void help() {
        System.out.println("View personal info\n" +
                "Change password <current_pass>, <new_pass>\n" +
                "Edit <field>, <new_value>\n" +
                "View plato statistics\n" +
                "Games history\n" +
                "Game statistics <game_name>\n" +
                "Logout\n" +
                "back");
    }

    private void changePassword(String currentPassword, String newPassword) {
        if (!account.getPassword().equals(currentPassword)) {
            System.out.println("password is not correct");
        } else {
            controller.changePassword(newPassword);
            System.out.println("password changed successfully");
        }
    }

    private void viewPersonalInfo() {
        ArrayList<String> infos = controller.showPersonalInfo();
        System.out.println("firstName: " + infos.get(0) +
                "\nlastName: " + infos.get(1) +
                "\nusername: " + infos.get(2) +
                "\nEmail: " + infos.get(3) +
                "\nPhoneNumber: " + infos.get(4));
    }
}
