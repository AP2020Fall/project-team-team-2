package view;

import model.Account;

import java.util.regex.Matcher;

public class AccountMenu extends Menu {
    public AccountMenu(Account account) {
        super(account);
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
            }
        }
    }

    private void logout() {
    }

    private void gameStatistics(String gameName) {

    }

    private void gameHistory() {
    }

    private void viewPlatoStatistics() {

    }

    private void editField(String field, String newValue) {

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
    }

    private void viewPersonalInfo() {
    }
}
