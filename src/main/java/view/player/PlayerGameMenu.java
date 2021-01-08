package view.player;


import com.sun.deploy.security.SelectableSecurityManager;
import controller.GameMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.Entry.EventEntry;
import model.Entry.GameLogEntry;
import model.Entry.GameLogSummaryEntry;
import model.Game;
import model.GameLog;
import model.GameLogStates;
import model.GameStates;
import view.Tab;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class PlayerGameMenu implements Tab, Initializable {
    @FXML private   Label detail = new Label();
    @FXML private  Label frequency= new Label();
    @FXML private  Label win= new Label();
    @FXML private ToggleButton favoriteButton = new ToggleButton();
    public TreeTableView<GameLogEntry> gameLog;
    GameMenuController controller;

    public PlayerGameMenu(Game game) {
        controller = new GameMenuController(game);
    }
    @Override
    public Parent show() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/playerGameMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            if(controller.isFavorite())
                favoriteButton.setSelected(true);
            InitializeInfo();
            InitializeTreeGameLogList();
    }

    @FXML private void addToFavorite() {
        if(!favoriteButton.isSelected()) {
            controller.removeFromFavorites();
        }
        else {
            controller.addToFavorites();
        }
    }

    @FXML private void runGame() {
        System.out.println("must run game");
    }

    @FXML private void scoreboard(ActionEvent actionEvent) {
    }

    private void InitializeInfo()
    {
        detail.setText(controller.getDetails());
        frequency.setText(controller.getPlayedFrequency());
        win.setText(controller.getWinsCount());
    }
    private void InitializeTreeGameLogList()
    {
        TreeTableColumn<GameLogEntry, String> gameLogGameName = new TreeTableColumn<>("Game");
        gameLogGameName.setCellValueFactory(new TreeItemPropertyValueFactory<>("gameName"));

        TreeTableColumn<GameLogEntry, String> gameLogEnemies = new TreeTableColumn<>("Enemies");
        gameLogEnemies.setCellValueFactory(new TreeItemPropertyValueFactory<>("enemies"));

        TreeTableColumn<GameLogEntry, GameLogStates> gameLogState = new TreeTableColumn<>("Result");
        gameLogState.setCellValueFactory(new TreeItemPropertyValueFactory<>("result"));

        TreeTableColumn<GameLogEntry, LocalDateTime> gameLogTime = new TreeTableColumn<>("Time Finished");
        gameLogTime.setCellValueFactory(new TreeItemPropertyValueFactory<>("timeFinished"));
        TreeItem<GameLogEntry> gameLogRoot = new TreeItem<>();
        if(controller.hasPlayedGame()) {
            for (GameLogEntry gameLogEntry : controller.getGameLog())
                gameLogRoot.getChildren().add(new TreeItem<>(gameLogEntry));
            gameLog.setRoot(gameLogRoot);
            gameLog.setShowRoot(false);
        }
        else
        {
            gameLog.setPlaceholder(new Label("Hasn't been played."));
        }

        gameLog.getColumns().addAll(gameLogGameName,gameLogEnemies,gameLogState,gameLogTime);
    }
   /* private void gameMenu() {
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
*/
  /*  private void showPoints() {
        //System.out.println(controller.showPoints());
    }*/
/*
    private void runGame() {
        if (!controller.canRunGame())
            System.out.println("can't run the game: only Risk has been implemented!");
        else {
            System.out.println("how many players:");
            String input = scanner.nextLine();
            int numPlayers = Integer.parseInt(input);
            ArrayList<String> usernames = new ArrayList<>();
            usernames.add(account.getUsername());
            for (int i = 1; i < numPlayers; i++) {
                System.out.println("please enter username:");
                input = scanner.nextLine();
                if (!controller.isUsernameExist(input)) {
                    System.out.println("username doesn't exist");
                    i--;
                } else if (usernames.contains(input)) {
                    System.out.println(input + " has been already added.");
                    i--;
                } else {
                    usernames.add(input);

                }

            }
            controller.runGame(usernames);
        }
    }
*/
  /*  private void addToFavorites() {
        controller.addToFavorites();
        System.out.println("added successfully!");
    }*/

  /* private void showPlayedCount() {
        if(!controller.hasPlayedGame())
            System.out.println("player hasn't played the game.");
        else
        System.out.println(controller.showPlayedCount());
    }*/

    /*private void showWinsCount() {
        if(!controller.hasPlayedGame())
            System.out.println("player hasn't played the game.");
        else
        System.out.println(controller.showWinsCount());
    }*/

   /* private void showLog() {
        for (String log : controller.showLog()) {
            System.out.println(log);
        }
    }*/

   /* private void details() {
        System.out.println(controller.showDetails());
    }*/

    private void showScoreboard() {
        System.out.println(controller.showScoreBoard());
    }

    /*private void help() {
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
*/
}
