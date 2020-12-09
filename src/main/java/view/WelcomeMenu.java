package view;

import model.Account;
import model.Event;
import model.Suggestion;

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
                saveFiles();
                System.exit(1);
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void saveFiles() {
        try {
            Account.save();
            Event.save();
            Suggestion.save();

        } catch (Exception e) {
            e.getStackTrace();
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
