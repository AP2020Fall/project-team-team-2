package view.admin;

import com.jfoenix.controls.JFXButton;
import controller.admin.AdminProfileViewController;
import controller.player.PlayerProfileViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import main.Client;
import model.Entry.GameLogSummaryEntry;
import model.Player;
import view.Tab;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AdminProfileView implements Tab, Initializable {

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

    private final AdminProfileViewController controller;

    public AdminProfileView(Player player) {
        controller = new AdminProfileViewController(Client.getClientInfo());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedPlayerInfo();
        initializedTableGameLog();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/admin/adminProfileView.fxml"));
        loader.setController(this);
        return loader.load();
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

    private void initializedTableGameLog()
    {
        TableColumn<GameLogSummaryEntry, ImageView> gameImageColumn = new TableColumn<>("Avatar");
        gameImageColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
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
        gameHistoryList.getColumns().addAll(gameImageColumn,gameNameColumn, frequencyColumn, winsColumn, scoreColumn, lastPlayColumn);
        gameHistoryList.getItems().addAll(controller.getGameHistory());
    }

}
