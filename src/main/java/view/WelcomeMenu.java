package view;

import model.Account;

public class WelcomeMenu extends Menu {
    public WelcomeMenu(Account account) {
        super(account);
        System.out.println("Welcome Menu");
        welcomeMenu();

    }

    private void welcomeMenu() {
        while (true) {
            String input = scanner.nextLine();

            if (getMatcher(input, "^register$").find()) {
                openRegisterMenu();
            } else if (getMatcher(input, "^login$").find()) {
                openLoginMenu();
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^exit$").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void help() {
        System.out.println("register\n" +
                "login\n" +
                "help\n" +
                "exit");
    }

    private void openLoginMenu() {
        new LoginMenu(null);
    }

    private void openRegisterMenu() {
        new RegisterMenu(null);
    }

}
