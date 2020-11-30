package view;

import model.Account;

import java.util.regex.Matcher;

public class PlayerMainMenu extends Menu {

    public PlayerMainMenu(Account account) {
        super(account);
        playerMainMenu();
    }

    private void playerMainMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "Show Points").find()) {
                showPoints();
            } else if (getMatcher(input, "View favorite games").find()) {
                viewFavoriteGames();
            } else if (getMatcher(input, "View platobot's messages").find()) {
                viewPlatoBotMessages();
            } else if (getMatcher(input, "View last played").find()) {
                viewLastPlayed();
            } else if (getMatcher(input, "View admin's suggestions").find()) {
                viewAdminSuggestion();
            } else if (getMatcher(input, "Choose suggested game").find()) {
                chooseSuggestedGame();
            } else if (getMatcher(input, "Add friend").find()) {
                addFriend();
            } else if (getMatcher(input, "View account menu").find()) {
                viewAccountMenu();
            }
        }
    }

    private void addFriend() {

    }

    private void chooseSuggestedGame() {

    }

    private void viewAdminSuggestion() {

    }

    private void viewLastPlayed() {

    }

    private void viewPlatoBotMessages() {
    }

    private void viewFavoriteGames() {
    }

    private void showPoints() {
    }

    private void help() {
        System.out.println("Show Points\n" +
                "View favorite games\n" +
                "View platobot's messages\n" +
                "View last played\n" +
                "View admin's suggestions\n" +
                "Choose suggested game\n" +
                "Add friend\n" +
                "View account menu");
    }
}
