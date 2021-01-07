package view;

import controller.PlayerAccountMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Entry.GameLogEntry;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PlayerAccountMenu implements View, Initializable {
    private PlayerAccountMenuController controller;
    @FXML private TextField searchUsername;
    @FXML private TableView<GameLogEntry> gameHistory;
    @FXML private Label score;
    @FXML private Label bio;
    @FXML private Label money;
    @FXML private Label date;
    @FXML private Label wins;
    @FXML private Label friends;
    public PlayerAccountMenu() {
        //super(account);
        controller = new PlayerAccountMenuController();
        //accountMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText(controller.showPoints());
        bio.setText(controller.getBio());
        money.setText(controller.getMoney());
        date.setText(controller.getDate());
        wins.setText(controller.getWins());
        friends.setText(controller.getFriends());
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
        gameHistory.getColumns().addAll(gameNameColumn,frequencyColumn,winsColumn,scoreColumn,lastPlayColumn);
        gameHistory.getItems().addAll(controller.getGameHistory());
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/playerAccountMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }
    @FXML
    private void editInfo() throws IOException {
        System.out.println("EditInfo must be implemented");
    }
    @FXML
    private void logout() throws IOException {
        controller.logout();
        ViewHandler.getViewHandler().logout();
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
}
