package view.player;

import controller.ClientMasterController.ClientMasterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import main.Client;
import model.Entry.GameEntry;
import view.Tab;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayerMainMenu implements Tab, Initializable {
    private final ClientMasterController controller;
    @FXML
    private TreeTableView<GameEntry> gamesList;

    public PlayerMainMenu() {
        controller = Client.getConnector().getController();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerMainMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTreeGamesList();
    }

    private void initializeTreeGamesList() {
        TreeItem<GameEntry> favourite = new TreeItem<>(new GameEntry("Favourite Games"));
        favourite.setExpanded(true);
        for (GameEntry gameEntry : controller.favoriteGames()) {

            favourite.getChildren().add(new TreeItem<>(gameEntry));
        }
        TreeItem<GameEntry> recently = new TreeItem<>(new GameEntry("Recently Played"));
        recently.setExpanded(true);
        recently.getChildren().add(new TreeItem<>(controller.lastGamePlayed()));
        TreeItem<GameEntry> suggested = new TreeItem<>(new GameEntry("Suggested"));
        suggested.setExpanded(true);
        for (GameEntry gameEntry : controller.adminsSuggestions()) {
            suggested.getChildren().add(new TreeItem<>(gameEntry));
        }
        TreeTableColumn<GameEntry, ImageView> gameAvatar = new TreeTableColumn<>("Avatar");
        gameAvatar.setCellValueFactory(new TreeItemPropertyValueFactory<>("avatar"));
        TreeTableColumn<GameEntry, String> gameName = new TreeTableColumn<>("Name");
        gameName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<GameEntry, Hyperlink> gameOpen = new TreeTableColumn<>("Open game");
        gameOpen.setCellValueFactory(new TreeItemPropertyValueFactory<>("link"));
        TreeItem<GameEntry> gameRoot = new TreeItem<>();
        gameRoot.getChildren().addAll(favourite, recently, suggested);
        gamesList.setRoot(gameRoot);
        gamesList.setShowRoot(false);
        gamesList.getColumns().addAll(gameAvatar, gameName, gameOpen);
    }
}


