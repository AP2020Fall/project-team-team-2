package view.admin;

import controller.admin.AdminGamesMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.Entry.EventEntry;
import model.Entry.GameEntry;
import model.Event;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminGamesMenu implements Tab, Initializable {
    @FXML
    private TableView<GameEntry> gameList;
    @FXML
    private TreeTableView<EventEntry> eventList;
    private AdminGamesMenuController controller;

    public AdminGamesMenu()
    {
        controller = new AdminGamesMenuController();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/admin/adminGamesMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableGameList();
        initializedTreeEventList();
    }
    @FXML
    private void addGame() throws IOException {
        new AddGamePopup().openWindow();
        TabHandler.getTabHandler().refresh();
    }

    @FXML
    private void deleteGame() {
        for (GameEntry gameEntry: gameList.getSelectionModel().getSelectedItems())
        {
            controller.deleteGame(gameEntry);
        }
        TabHandler.getTabHandler().refresh();

    }


    @FXML
    private void addEvent() throws IOException {
        new AddEventPopup().openWindow();
        TabHandler.getTabHandler().refresh();
    }

    @FXML
    private void deleteEvent() {
        for (TreeItem<EventEntry> eventEntry: eventList.getSelectionModel().getSelectedItems())
        {
            if(!eventEntry.getParent().equals(eventList.getRoot()))
                controller.deleteEvent(eventEntry.getValue());
        }
        TabHandler.getTabHandler().refresh();
    }
    private void initializeTableGameList()
    {
        TableColumn<GameEntry, String> gameNameColumn = new TableColumn<>("Name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gameNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gameNameColumn.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue()));

        TableColumn<GameEntry, String> gameDetailColumn = new TableColumn<>("Detail");
        gameDetailColumn.setCellValueFactory(new PropertyValueFactory<>("detail"));
        gameDetailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gameDetailColumn.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setDetail(event.getNewValue()));

        gameList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        gameList.setEditable(true);
        gameList.getColumns().addAll(gameNameColumn, gameDetailColumn);
        gameList.getItems().addAll(controller.getGames());
    }
    private void initializedTreeEventList()
    {
        TreeItem<EventEntry> ongoing = new TreeItem<>(new EventEntry("Ongoing"));
        ongoing.setExpanded(true);
        for (EventEntry eventEntry : controller.getOngoingEvents()) {

            ongoing.getChildren().add(new TreeItem<>(eventEntry));
        }
        TreeItem<EventEntry> upcoming = new TreeItem<>(new EventEntry("Upcoming"));
        upcoming.setExpanded(true);
        for (EventEntry eventEntry : controller.getUpcomingEvents()) {

            upcoming.getChildren().add(new TreeItem<>(eventEntry));
        }
        TreeItem<EventEntry> past = new TreeItem<>(new EventEntry("Past"));
        past.setExpanded(true);
        for (EventEntry eventEntry : controller.getPastEvents()) {

            past.getChildren().add(new TreeItem<>(eventEntry));
        }

        TreeTableColumn<EventEntry, String> eventGameName = new TreeTableColumn<>("Game");
        eventGameName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));

        TreeTableColumn<EventEntry, LocalDate> eventStart = new TreeTableColumn<>("Start");
        eventStart.setCellValueFactory(new TreeItemPropertyValueFactory<>("start"));
        TreeTableColumn<EventEntry, LocalDate> eventEnd = new TreeTableColumn<>("End");
        eventEnd.setCellValueFactory(new TreeItemPropertyValueFactory<>("end"));
        TreeTableColumn<EventEntry, String> eventScore = new TreeTableColumn<>("Score");
        eventScore.setCellValueFactory(new TreeItemPropertyValueFactory<>("score"));
        TreeTableColumn<EventEntry, Hyperlink> eventEdit = new TreeTableColumn<>("Edit");
        eventEdit.setCellValueFactory(new TreeItemPropertyValueFactory<>("edit"));

        TreeItem<EventEntry> gameRoot = new TreeItem<>();
        gameRoot.getChildren().addAll(ongoing, upcoming, past);
        eventList.setRoot(gameRoot);
        eventList.setShowRoot(false);
        eventList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        eventList.getColumns().addAll(eventGameName,eventStart,eventEnd,eventScore, eventEdit);
    }

}
