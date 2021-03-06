package view.player;

import controller.ClientMasterController.ClientMasterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Client;
import model.Entry.EventEntry;
import model.Entry.GameEntry;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PlayerGamesMenu implements Tab, Initializable {
    private final ClientMasterController controller;
    @FXML
    private TableView<GameEntry> gamesListGamesMenu;
    @FXML
    private TreeTableView<EventEntry> eventList;

    public PlayerGamesMenu() {
        controller = Client.getConnector().getController();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerGamesMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableGamesList();
        initializeTreeEventList();
    }

    @FXML
    void eventTableSelect(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                if (eventList.getSelectionModel().getSelectedItems().size() != 0) {
                    EventEntry eventEntry = eventList.getSelectionModel().getSelectedItems().get(0).getValue();
                    TabHandler.getTabHandler().push(new PlayerEventMenu(eventEntry.getEventId()));
                }
            }

        }

    }

    @FXML
    void gameTableSelect(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                if (gamesListGamesMenu.getSelectionModel().getSelectedItems().size() != 0) {
                    GameEntry gameEntry = gamesListGamesMenu.getSelectionModel().getSelectedItems().get(0);
                    TabHandler.getTabHandler().push(new PlayerGameMenu(gameEntry.getName()));
                }
            }

        }
    }

    private void initializeTableGamesList() {
        TableColumn<GameEntry, ImageView> gameAvatarColumn = new TableColumn<>("Avatar");
        gameAvatarColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        TableColumn<GameEntry, String> gameNameColumn = new TableColumn<>("Name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        gamesListGamesMenu.setPlaceholder(new Label("No game has been added."));
        gamesListGamesMenu.getColumns().addAll(gameAvatarColumn, gameNameColumn);
        gamesListGamesMenu.getItems().addAll(controller.getGames());
    }

    private void initializeTreeEventList() {
        TreeTableColumn<EventEntry, ImageView> eventImage = new TreeTableColumn<>("Avatar");
        eventImage.setCellValueFactory(new TreeItemPropertyValueFactory<>("avatar"));
        TreeTableColumn<EventEntry, String> eventName = new TreeTableColumn<>("Game name");
        eventName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<EventEntry, LocalDate> eventStart = new TreeTableColumn<>("Start");
        eventStart.setCellValueFactory(new TreeItemPropertyValueFactory<>("start"));
        TreeTableColumn<EventEntry, LocalDate> eventEnd = new TreeTableColumn<>("End");
        eventEnd.setCellValueFactory(new TreeItemPropertyValueFactory<>("end"));
        TreeItem<EventEntry> eventRoot = new TreeItem<>();
        for (EventEntry eventEntry : controller.getEvents())
            eventRoot.getChildren().add(new TreeItem<>(eventEntry));
        eventList.setRoot(eventRoot);
        eventList.setShowRoot(false);
        eventList.setPlaceholder(new Label("No event has been created."));
        eventList.getColumns().addAll(eventImage, eventName, eventStart, eventEnd);
    }

}
