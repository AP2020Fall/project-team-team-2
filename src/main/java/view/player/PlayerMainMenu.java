package view.player;

import com.jfoenix.controls.JFXTreeTableView;
import controller.player.PlayerMainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.Entry.GameEntry;
import model.Entry.SuggestionEntry;
import view.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerMainMenu implements Tab, Initializable {

    @FXML
    private TreeTableView<GameEntry> gamesList;

    @FXML
    private TableView<SuggestionEntry> suggestionTable;

    PlayerMainMenuController controller;

    public PlayerMainMenu() {
        controller = new PlayerMainMenuController();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerMainMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedSuggestionTable();
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
        TreeTableColumn<GameEntry, String> gameName = new TreeTableColumn<>("Name");
        gameName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<GameEntry, Hyperlink> gameOpen = new TreeTableColumn<>("Open game");
        gameOpen.setCellValueFactory(new TreeItemPropertyValueFactory<>("link"));
        TreeItem<GameEntry> gameRoot = new TreeItem<>();
        gameRoot.getChildren().addAll(favourite, recently, suggested);
        gamesList.setRoot(gameRoot);
        gamesList.setShowRoot(false);
        gamesList.getColumns().addAll(gameName, gameOpen);
    }
    private void initializedSuggestionTable()
    {
        //todo
    }



}


