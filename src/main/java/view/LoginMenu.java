package view;

import controller.AccountController;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    public LoginMenu(AccountController account) {
        super(account);
        loginMenu();
    }

    private void loginMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "help").find()) {
                help();
            } else if (getMatcher(input, "back").find()) {
                return;
            } else if ((matcher = getMatcher(input, "^login (\\S+)$")).find()) {
                loginAccount(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^delete (\\S+)$")).find()) {
                deleteAccount(matcher.group(1));
            }
        }
    }

    private void loginAccount(String group) {
    }

    private void deleteAccount(String group) {
    }

    private void help() {
        System.out.println("login <username>\n" +
                "delete <username>\n" +
                "back");
    }
}