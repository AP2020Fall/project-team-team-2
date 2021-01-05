package view;

import controller.PlayerMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import model.Entry.EventEntry;
import model.Entry.GameEntry;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerMainMenu implements View, Initializable {
    @FXML
    private TreeTableView<GameEntry> games;
    @FXML
    private TreeTableView<EventEntry> eventList;
    @FXML
    private Label score;
    PlayerMainMenuController controller = new PlayerMainMenuController();

    public PlayerMainMenu() {
        //super(account);
        // controller = new ((Player) account);
        // System.out.println("Player MainMenu");
        // playerMainMenu();
    }

    @Override
    public void show(Stage window) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/plato/playerMainMenu.fxml"));
        window.setTitle("Plato");
        window.setScene(new Scene(root));
        window.setResizable(false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText(controller.showPoints());
        TreeItem<GameEntry> favourite = new TreeItem<>(new GameEntry("Favourite Games"));
        favourite.setExpanded(true);
        for (GameEntry gameEntry : controller.favoriteGames()) {

            favourite.getChildren().add(new TreeItem<>(gameEntry));
        }

        TreeItem<GameEntry> recently = new TreeItem<>(new GameEntry("Recently Played"));
        recently.setExpanded(true);
        if (controller.hasPlayerPlayed())
            recently.getChildren().add(new TreeItem<>(controller.lastGamePlayed()));

        TreeItem<GameEntry> suggested = new TreeItem<>(new GameEntry("Suggested"));
        suggested.setExpanded(true);

        for (GameEntry gameEntry : controller.adminsSuggestions()) {
            suggested.getChildren().add(new TreeItem<>(gameEntry));
        }

        TreeTableColumn<GameEntry, String> gameName = new TreeTableColumn<>("Name");
        gameName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<GameEntry, Hyperlink> gameOpen = new TreeTableColumn<>("Open game");
        gameOpen.setCellValueFactory(new TreeItemPropertyValueFactory<>("link"));
        TreeItem<GameEntry> gameRoot = new TreeItem<>();
        gameRoot.getChildren().addAll(favourite, recently, suggested);
        games.setRoot(gameRoot);
        games.setShowRoot(false);
        games.getColumns().setAll(gameName,gameOpen);


        TreeTableColumn<EventEntry, String> eventName = new TreeTableColumn<>("Name");
        eventName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeItem<EventEntry> eventRoot = new TreeItem<>();
        for(EventEntry eventEntry: controller.getEvents())
            eventRoot.getChildren().add(new TreeItem<>(eventEntry));
        eventList.setRoot(eventRoot);
        eventList.setShowRoot(false);
        eventList.getColumns().setAll(eventName);
    }

    @FXML
    private void viewFriendsMenu() throws IOException {
       ViewHandler.getViewHandler().push(new FriendsMenu());
    }

    @FXML
    private void viewGamesMenu() {

    }

    @FXML
    private void viewAccountMenu() {

    }
    public void viewMainMenu(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {
    }

    public void platoMsg(ActionEvent actionEvent) {
    }

    /*private void playerMainMenu() {
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
            } else if ((matcher = getMatcher(input, "^Choose suggested game (\\S+)")).find()) {
                chooseSuggestedGame(matcher.group(1));
//            } else if ((matcher = getMatcher(input, "^Add friend (\\S+)$")).find()) {
//                addFriend(matcher.group(1));
            } else if (getMatcher(input, "^View account menu$").find()) {
                viewAccountMenu(account);
            } else if (getMatcher(input, "^View friends menu").find()) {
                viewFriendsMenu(account);
            } else if (getMatcher(input, "View Games menu").find()) {
                ViewGamesMenu(account);
            } else {
                System.out.println("invalid command!");
            }
        }
    }*/

//  private void addFriend(String username) {
//        controller.addFriend(username);
//    }

  /*  private void chooseSuggestedGame(String gameName) {
        if (!controller.isGameSuggested(gameName))
            System.out.println("game is not suggested!");
        else
            controller.chooseSuggestedGame(gameName);
    }*/

    /*
        private void viewAdminSuggestion() {
            //todo initialize a row to TreeTableView games
            if(controller.showAdminsSuggestions().isEmpty())
                System.out.println("no suggestions!");
            else {
                for (String suggestion : controller.showAdminsSuggestions())
                    System.out.println(suggestion);
            }
        }
    *//*
    private void viewLastPlayed() {
        //todo initialize a row to TreeTableView games
        if(!controller.hasPlayerPlayed())
            System.out.println("player has not played a game!");
        else
        System.out.println( controller.showLastPlayed());
    }
*/
    private void viewPlatoBotMessages() {
        //todo add mail icon to the menu bar
        if (controller.showPlatoBotsMessages().isEmpty())
            System.out.println("no message!");
        else {
            for (String message : controller.showPlatoBotsMessages()) {
                System.out.println(message);
            }
        }
    }

    /*
        private void viewFavoriteGames() {
            //todo initialize a row to TreeTableView games
            if(controller.showFavoriteGames().isEmpty())
                System.out.println("no favorite game!");
            else {
                for (String favoriteGame : controller.showFavoriteGames()) {
                    System.out.println(favoriteGame);
                }
            }
        }
    */
  /*  private void showPoints() {
        int points = controller.showPoints();
        System.out.println("points: " + points);
    }*/


/*
    private void help() {
        System.out.println("Show Points\n" +
                "View favorite games\n" +
                "View platobot's messages\n" +
                "View last played\n" +
                "View admin's suggestions\n" +
                "Choose suggested game\n" +
                "View account menu\n" +
                "View friends menu\n" +
                "View games menu\n");
    }
*/
}
