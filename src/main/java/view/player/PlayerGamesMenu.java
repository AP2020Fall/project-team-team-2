package view.player;

import controller.PlayerMainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.Entry.EventEntry;
import model.Entry.GameEntry;
import view.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerGamesMenu implements Tab, Initializable {
    @FXML
    private TableView<GameEntry> gamesListGamesMenu;
    @FXML
    private TreeTableView<EventEntry> eventList;
    private PlayerMainMenuController controller;

    public PlayerGamesMenu() {
        controller = new PlayerMainMenuController();
    }
    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/playerGamesMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableGamesList();
        initializeTreeEventList();
    }

    private void initializeTableGamesList() {
        TableColumn<GameEntry, String> gameNameColumn = new TableColumn<>("Name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<GameEntry, Hyperlink> gameOpenColumn =
                new TableColumn<>("Open game");
        gameOpenColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        gamesListGamesMenu.getColumns().addAll(gameNameColumn, gameOpenColumn);
        gamesListGamesMenu.getItems().addAll(controller.getGames());
    }

    private void initializeTreeEventList() {
        TreeTableColumn<EventEntry, String> eventName = new TreeTableColumn<>("Name");
        eventName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<EventEntry, Hyperlink> eventJoin = new TreeTableColumn<>("Join");
        eventJoin.setCellValueFactory(new TreeItemPropertyValueFactory<>("link"));
        TreeItem<EventEntry> eventRoot = new TreeItem<>();
        for (EventEntry eventEntry : controller.getEvents())
            eventRoot.getChildren().add(new TreeItem<>(eventEntry));
        eventList.setRoot(eventRoot);
        eventList.setShowRoot(false);
        eventList.getColumns().add(eventJoin);
    }

}
