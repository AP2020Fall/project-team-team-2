package view;


import controller.GameMenuController;
import model.Account;
import model.Game;
import model.Player;

import java.util.ArrayList;

public class GameMenu extends Menu {
    GameMenuController controller;

    public GameMenu(Player player, Game game) {
        super((Account) player);
        GameMenuController controller = new GameMenuController(game,player);
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
        System.out.println(controller.showPoints());
    }

    private void runGame() {
        System.out.println("How many players: ");
        String input = scanner.nextLine();
        Integer numPlayers = Integer.parseInt(input);
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add(account.getUsername());
        for (int i = 1 ; i < numPlayers ;i++)
        {
            System.out.println("Please enter username: ");
            input = scanner.nextLine();
            if(!controller.isUsernameExist(input))
            {
                System.out.println("Username doesn't exist\n");
                i--;
            }
            else
            {
                if(usernames.contains(input)) {
                    System.out.println(input + "has been already added.");
                    i--;
                }
                else
                {
                    usernames.add(input);
                }
            }

        }
        controller.runGame(usernames);
    }

    private void addToFavorites() {
        controller.addToFavorites();
        System.out.println("added successfully!");
    }

    private void showPlayedCount() {
        System.out.println(controller.showPlayedCount());
    }

    private void showWinsCount() {
        System.out.println(controller.showWinsCount());
    }

    private void showLog() {
        for (String log : controller.showLog()) {
            System.out.println(log);
        }
    }

    private void details() {
        System.out.println(controller.showDetails());
    }

    private void showScoreboard() {
        System.out.println(controller.showScoreBoard());
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
