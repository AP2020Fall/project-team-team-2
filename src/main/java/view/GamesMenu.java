package view;

import model.Account;
import java.util.regex.Matcher;

public class GamesMenu extends Menu {

    public GamesMenu(Account account) {
        super(account);
        gamesMenu();
    }

    private void gamesMenu() {
        System.out.println("Games:\n" +
                "1.Risk Game");
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^View account menu$").find()) {
                viewAccountMenu();
            } else if ((matcher = getMatcher(input, "^Open (\\S+)$")).find()) {
                openGame(matcher.group(1));
            } else if (getMatcher(input, "^back$").find()) {
                return;
            }
        }
    }

    private void openGame(String gameName) {

    }

    private void help() {
        System.out.println("View account menu\n" +
                "Open <game_name>\n" +
                "help\n" +
                "back");
    }
}
