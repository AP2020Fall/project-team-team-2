package view;

import model.Account;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    public LoginMenu(Account account) {
        super(account);
        loginMenu();
    }

    private void loginMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "help").find()) {
                help();
            } else if ((matcher = getMatcher(input, "^login (\\S+)$")).find()) {
                loginAccount(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^delete (\\S+)$")).find()) {
                deleteAccount(matcher.group(1));
            } else if (getMatcher(input, "back").find()) {
                return;
            }
        }
    }

    private void loginAccount(String username) {
    }

    private void deleteAccount(String username) {

    }


    private void help() {
        System.out.println("login <username>\n" +
                "delete <username>\n" +
                "back");
    }
}