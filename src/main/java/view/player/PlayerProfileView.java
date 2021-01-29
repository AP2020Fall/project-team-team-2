package view.player;

import com.jfoenix.controls.JFXButton;
import controller.ClientMasterController.ClientMasterController;
import controller.player.PlayerProfileViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Client;
import model.Entry.GameEntry;
import model.Entry.GameLogEntry;
import model.Entry.GameLogSummaryEntry;
import model.GameLogSummary;
import model.Player;
import view.Tab;
import view.TabHandler;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PlayerProfileView implements Tab, Initializable {
    @FXML
    private Label friendRequestPending = new Label();
    @FXML
    private Label username = new Label();
    @FXML
    private Label firstName = new Label();
    @FXML
    private Label lastName = new Label();
    @FXML
    private Label email = new Label();
    @FXML
    private Label phoneNumber = new Label();
    @FXML
    private Label bio = new Label();
    @FXML
    private Label date = new Label();
    @FXML
    private Label wins = new Label();
    @FXML
    private Label friends = new Label();
    @FXML
    private ImageView avatar;
    @FXML
    private TableView<GameLogSummaryEntry> gameHistoryList;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton removeButton;
    private final ClientMasterController controller;

    public PlayerProfileView(String playerUsername) {
        Client.getClientInfo().setPlayerUsername(playerUsername);
        controller = Client.getConnector().getController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedPlayerInfo();
        initializedFriendButtons();
        initializedTableGameLog();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerProfileView.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @FXML
    private void addFriend() {
        controller.addFriend();
        addButton.setVisible(false);
        removeButton.setVisible(false);
        friendRequestPending.setVisible(true);
    }

    @FXML
    private void removeFriend() {
        controller.removeFriend();
        addButton.setVisible(true);
        removeButton.setVisible(false);
        friendRequestPending.setVisible(false);
    }

    @FXML
    void gameLogSummaryTableSelected(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 2){
                if(gameHistoryList.getSelectionModel().getSelectedItems().size() != 0)
                {
                    GameLogSummaryEntry gameEntry = gameHistoryList.getSelectionModel().getSelectedItems().get(0);
                    TabHandler.getTabHandler().push(new PlayerGameMenu(gameEntry.getGameName()));
                }
            }

        }
    }

    private void initializedPlayerInfo() {
        username.setText(controller.getViewPlayerUsername());
        firstName.setText(controller.getViewPlayerFirstName());
        lastName.setText(controller.getViewPlayerLastName());
        email.setText(controller.getViewPlayerEmail());
        phoneNumber.setText(controller.getViewPlayerPhoneNumber());
        wins.setText(controller.getViewPlayerWins() + " Wins");
        date.setText(controller.getViewPlayerDaysPassed());
        friends.setText(controller.getViewPlayerFriendCount());
        bio.setText(controller.getViewPlayerBio());
        avatar.setImage(controller.getViewPlayerImage());
    }

    private void initializedFriendButtons() {
        friendRequestPending.setVisible(false);
        addButton.setVisible(!controller.areFriends());
        removeButton.setVisible(controller.areFriends());
        if (controller.HasFriendRequestBeenSent()) {
            addButton.setVisible(false);
            removeButton.setVisible(false);
            friendRequestPending.setVisible(true);
        }
        if (controller.areTheSame()) {
            addButton.setVisible(false);
            removeButton.setVisible(false);
            friendRequestPending.setVisible(false);
        }
    }

    private void initializedTableGameLog() {
        TableColumn<GameLogSummaryEntry, String> gameNameColumn = new TableColumn<>("Game");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        TableColumn<GameLogSummaryEntry, Integer> frequencyColumn = new TableColumn<>("Frequency");
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        TableColumn<GameLogSummaryEntry, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<GameLogSummaryEntry, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        TableColumn<GameLogSummaryEntry, LocalDate> lastPlayColumn = new TableColumn<>("Last Play");
        lastPlayColumn.setCellValueFactory(new PropertyValueFactory<>("lastPlay"));
        gameHistoryList.setPlaceholder(new Label("Player has not played a game."));
        gameHistoryList.getColumns().addAll(gameNameColumn, frequencyColumn, winsColumn, scoreColumn, lastPlayColumn);
        gameHistoryList.getItems().addAll(controller.getGameHistory());
    }
}
