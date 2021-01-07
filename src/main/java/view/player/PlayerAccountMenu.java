package view.player;

import controller.PlayerMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Entry.GameLogEntry;
import view.Tab;
import view.View;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PlayerAccountMenu implements Tab, Initializable {
    @FXML
    private TableView<GameLogEntry> gameHistoryList;
    @FXML
    private Label bio = new Label();
    @FXML
    private Label money = new Label();
    @FXML
    private Label date = new Label();
    @FXML
    private Label wins = new Label();
    @FXML
    private Label friends = new Label();
    @FXML
    private Label score = new Label();
    private PlayerMainMenuController controller;
    public PlayerAccountMenu() {
        controller = new PlayerMainMenuController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedInfo();
        initializeTableGameHistoryList();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/playerAccountMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }
    @FXML
    private void logout() throws IOException {
        controller.logout();
        ViewHandler.getViewHandler().logout();
    }

    @FXML
    private void editInfo(ActionEvent actionEvent) {
        //todo implement
    }
    private void initializedInfo() {
        score.setText(controller.showPoints());
        bio.setText(controller.getBio());
        money.setText(controller.getMoney());
        date.setText(controller.getDate());
        wins.setText(controller.getWins());
        friends.setText(controller.getFriendCount());
    }

    private void initializeTableGameHistoryList() {
        TableColumn<GameLogEntry, String> gameNameColumn = new TableColumn<>("Game");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        TableColumn<GameLogEntry, Integer> frequencyColumn = new TableColumn<>("Frequency");
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        TableColumn<GameLogEntry, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<GameLogEntry, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        TableColumn<GameLogEntry, LocalDateTime> lastPlayColumn = new TableColumn<>("Last Play");
        lastPlayColumn.setCellValueFactory(new PropertyValueFactory<>("lastPlay"));
        gameHistoryList.getColumns().addAll(gameNameColumn, frequencyColumn, winsColumn, scoreColumn, lastPlayColumn);
        gameHistoryList.getItems().addAll(controller.getGameHistory());
    }


}
