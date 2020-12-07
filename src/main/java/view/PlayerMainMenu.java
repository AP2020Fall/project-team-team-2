package view;

import controller.PlayerMainMenuController;
import model.Account;
import model.Player;

import java.util.regex.Matcher;

public class PlayerMainMenu extends Menu {
    PlayerMainMenuController controller;

    public PlayerMainMenu(Account account) {
        super(account);
        controller = new PlayerMainMenuController((Player) account);
        System.out.println("Player MainMenu");
        playerMainMenu();
    }

    private void playerMainMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^Show Points$").find()) {
                showPoints();
            } else if (getMatcher(input, "^View favorite games$").find()) {
                viewFavoriteGames();
            } else if (getMatcher(input, "^View platobot's messages$").find()) {
                viewPlatoBotMessages();
            } else if (getMatcher(input, "^View last played$").find()) {
                viewLastPlayed();
            } else if (getMatcher(input, "^View admin's suggestions$").find()) {
                viewAdminSuggestion();
            } else if (getMatcher(input, "^Choose suggested game$").find()) {
                chooseSuggestedGame();
            } else if ((matcher = getMatcher(input, "^Add friend (\\S+)$")).find()) {
                addFriend(matcher.group(1));
            } else if (getMatcher(input, "^View account menu$").find()) {
                viewAccountMenu(account);
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void addFriend(String username) {
        controller.addFriend(username);
    }

    private void chooseSuggestedGame() {
    }

    private void viewAdminSuggestion() {
        System.out.println(controller.showAdminsSuggestions());
    }

    private void viewLastPlayed() {
        controller.showLastPlayed();
    }

    private void viewPlatoBotMessages() {
        controller.showPlatoBotsMessages();
    }

    private void viewFavoriteGames() {
        controller.showFavoriteGames();
    }

    private void showPoints() {
        controller.showPoints();
    }

    private void help() {
        System.out.println("Show Points\n" +
                "View favorite games\n" +
                "View platobot's messages\n" +
                "View last played\n" +
                "View admin's suggestions\n" +
                "Choose suggested game\n" +
                "Add friend\n" +
                "View account menu\n");
    }
}
