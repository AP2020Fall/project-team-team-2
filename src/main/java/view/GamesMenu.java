package view;

import controller.GamesMenuController;
import model.Account;
import model.Player;

import java.util.regex.Matcher;

public class GamesMenu extends Menu {

    GamesMenuController controller;

    public GamesMenu(Account account) {
        super(account);
        controller = new GamesMenuController();
        gamesMenu();
    }

    private void gamesMenu() {
        System.out.println("Games:");
        for (String gameName : controller.listOfGames()) {
            System.out.println(gameName);
        }

        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^View account menu$").find()) {
                viewAccountMenu(account);
            } else if ((matcher = getMatcher(input, "^Open (\\S+)$")).find()) {
                openGame(matcher.group(1));
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void openGame(String gameName) {
        if (!controller.gameIsListed(gameName)) {
            System.out.println("invalid game!");
        } else {
            controller.run(gameName,(Player)account);
        }
    }

    private void help() {
        System.out.println("View account menu\n" +
                "Open <game_name>\n" +
                "help\n" +
                "back");
    }
}
