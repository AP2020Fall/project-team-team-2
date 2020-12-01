package view;

import controller.LoginController;
import model.Account;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    LoginController controller;

    public LoginMenu(Account account) {
        super(account);
        System.out.println("Login Menu:");
        controller = new LoginController();
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
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void loginAccount(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist");
        } else {
            System.out.println("password: ");
            String password = scanner.nextLine();
            if (!controller.isUsernameAndPasswordMatch(username, password)) {
                System.out.println("username and password are not match");
            } else {
                controller.login(username);
            }
        }
    }

    private void deleteAccount(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist");
        } else {
            System.out.println("password: ");
            String password = scanner.nextLine();
            if (!controller.isUsernameAndPasswordMatch(username, password)) {
                System.out.println("username and password are not match");
            } else {
                controller.delete(username);
                System.out.println(username + " deleted successfully!");
            }
        }
    }


    private void help() {
        System.out.println("login <username>\n" +
                "delete <username>\n" +
                "back");
    }
}