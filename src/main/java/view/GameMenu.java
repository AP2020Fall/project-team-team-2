package view;


import model.Account;

public class GameMenu extends Menu{

    public GameMenu(Account account) {
        super(account);
        gameMenu();
    }

    private void gameMenu() {
        while (true){
            String input = scanner.nextLine();
            if (getMatcher(input , "^help$").find()){
                help();
            }else if (getMatcher(input ,"View account menu").find()){
                viewAccountMenu();
            }else if (getMatcher(input ,"^Show scoreboard$").find()){
                showScoreboard();
            }else if (getMatcher(input ,"^Details$").find()){
                details();
            }else if (getMatcher(input ,"^Show log$").find()){
                showLog();
            }else if (getMatcher(input, "^Show wins count$").find()){
                showWinsCount();
            }else if (getMatcher(input ,"^Show played count$").find()){
                showPlayedCount();
            }else if (getMatcher(input ,"^Add to favorites$").find()){
                addToFavorites();
            }else if (getMatcher(input ,"^Run game$").find()){
                runGame();
            }else if (getMatcher(input , "^back$").find()){
                return;
            }
        }
    }

    private void runGame() {
    }

    private void addToFavorites() {
    }

    private void showPlayedCount() {
    }

    private void showWinsCount() {

    }

    private void showLog() {
    }

    private void details() {

    }

    private void showScoreboard() {
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
                "help\n" +
                "back");
    }
}
