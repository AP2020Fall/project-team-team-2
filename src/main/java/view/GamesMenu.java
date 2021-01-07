package view;

import controller.GamesMenuController;
import controller.PlayerMainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import model.Account;
import model.Entry.EventEntry;
import model.Entry.GameEntry;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class GamesMenu implements View, Initializable {
    @FXML private TextField searchUsername;
    @FXML private TreeTableView<GameEntry> games;
    @FXML private Label score;
    private PlayerMainMenuController controller;

    public GamesMenu() {
        controller = new PlayerMainMenuController();
      //  gamesMenu();
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
        games.getColumns().addAll(gameName,gameOpen);

    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/gamesMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }
    @FXML
    private void viewFriendsMenu() throws IOException {
        ViewHandler.getViewHandler().push(new FriendsMenu());
    }

    @FXML
    private void viewGamesMenu() throws IOException {
        ViewHandler.getViewHandler().push(new GamesMenu());
    }

    @FXML
    private void viewAccountMenu() throws IOException {
        ViewHandler.getViewHandler().push(new PlayerAccountMenu());
    }
    @FXML
    private void viewMainMenu() throws IOException {
        //ViewHandler.getViewHandler().push(new PlayerMainMenu());
    }

    @FXML
    private void back() throws IOException {
        ViewHandler.getViewHandler().mainMenuBack();
    }

    @FXML
    private void platoMsg() throws IOException {
        ViewHandler.getViewHandler().push(new PlatoMessageView());
    }
    public void search() throws IOException {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else {
            ViewHandler.getViewHandler().push(new
                    ProfileView(controller.searchPlayer(searchUsername.getText())));
        }
    }
   /* private void gamesMenu() {
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
*//*
    private void openGame(String gameName) {
        if (!controller.gameIsListed(gameName)) {
            System.out.println("invalid game!");
        } else {
            controller.run(gameName,(Player)account);
        }
    }
*/
    private void help() {
        System.out.println("View account menu\n" +
                "Open <game_name>\n" +
                "help\n" +
                "back");
    }

}
