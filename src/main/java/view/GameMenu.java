package view;


import controller.GameMenuController;
import model.Account;

public class GameMenu extends Menu {
    GameMenuController controller;
    public GameMenu(Account account) {
        super(account);
        controller = new GameMenuController(null);
        //todo change arg
        gameMenu();
    }

    private void gameMenu() {
        while (true) {
            String input = scanner.nextLine();
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "View account menu").find()) {
                viewAccountMenu(account);
            } else if (getMatcher(input, "^Show scoreboard$").find()) {
                showScoreboard();
            } else if (getMatcher(input, "^Details$").find()) {
                details();
            } else if (getMatcher(input, "^Show log$").find()) {
                showLog();
            } else if (getMatcher(input, "^Show wins count$").find()) {
                showWinsCount();
            } else if (getMatcher(input, "^Show played count$").find()) {
                showPlayedCount();
            } else if (getMatcher(input, "^Add to favorites$").find()) {
                addToFavorites();
            } else if (getMatcher(input, "^Run game$").find()) {
                runGame();
            } else if (getMatcher(input, "^Show points$").find()) {
                showPoints();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void showPoints() {
        controller.showPoints();
    }

    private void runGame() {
        controller.runGame();
    }

    private void addToFavorites() {
        controller.addToFavorites();
    }

    private void showPlayedCount() {
        controller.showPlayedCount();
    }

    private void showWinsCount() {
        controller.showWinsCount();
    }

    private void showLog() {
        controller.showLog();
    }

    private void details() {
        controller.showDetails();
    }

    private void showScoreboard() {
        controller.showScoreBoard();
    }

    private void help() {
        System.out.println("View account menu\n" +
                "Show scoreboard\n" +
                "Details\n" +
                "Show log\n" +
                "Show wins count\n" +
                "Show played count\n" +
                "Add to favorites\n" +
                "Run game\n" +
                "Show points\n" +
                "help\n" +
                "back");
    }
}
