package view.player;

import com.jfoenix.controls.JFXButton;
import controller.player.PlayerProfileViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Entry.GameLogEntry;
import model.Entry.GameLogSummaryEntry;
import model.Player;
import view.Tab;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PlayerProfileView implements Tab, Initializable {
    private final PlayerProfileViewController controller;
    @FXML
    private Label friendRequestPending= new Label();
    @FXML
    private Label username = new Label();
    @FXML
    private Label firstName= new Label();
    @FXML
    private Label lastName= new Label();
    @FXML
    private Label email= new Label();
    @FXML
    private Label phoneNumber= new Label();
    @FXML
    private Label daysPassed= new Label();
    @FXML
    private Label bio= new Label();
    @FXML
    private Label date= new Label();
    @FXML
    private Label wins= new Label();
    @FXML
    private Label friends= new Label();
    @FXML
    private ImageView avatar;
    @FXML
    private TableView<GameLogSummaryEntry> gameHistoryList;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton removeButton;

    public PlayerProfileView(Player player) {
        controller = new PlayerProfileViewController(player);
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
    private void initializedPlayerInfo()
    {
        username.setText(controller.getUsername());
        firstName.setText(controller.getFirstName());
        lastName.setText(controller.getLastName());
        email.setText(controller.getEmail());
        phoneNumber.setText(controller.getPhoneNumber());
        daysPassed.setText(controller.getDaysPassed());
        wins.setText(controller.getWins() + " Wins");
        date.setText(controller.getDaysPassed());
        friends.setText(controller.getFriendCount());
        bio.setText(controller.getBio());
        avatar.setImage(controller.getPlayerImage());
    }
    private void initializedFriendButtons()
    {
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
    private void initializedTableGameLog()
    {
        TableColumn<GameLogSummaryEntry, String> gameNameColumn = new TableColumn<>("Game");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        TableColumn<GameLogSummaryEntry, Integer> frequencyColumn = new TableColumn<>("Frequency");
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        TableColumn<GameLogSummaryEntry, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<GameLogSummaryEntry, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        TableColumn<GameLogSummaryEntry, LocalDateTime> lastPlayColumn = new TableColumn<>("Last Play");
        lastPlayColumn.setCellValueFactory(new PropertyValueFactory<>("lastPlay"));
        gameHistoryList.setPlaceholder(new Label("Player has not played a game."));
        gameHistoryList.getColumns().addAll(gameNameColumn, frequencyColumn, winsColumn, scoreColumn, lastPlayColumn);
        gameHistoryList.getItems().addAll(controller.getGameHistory());
    }

}
