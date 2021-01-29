package view.player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Client;
import main.ClientMasterController;
import model.Entry.GameLogSummaryEntry;
import view.Tab;
import view.TabHandler;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PlayerAccountMenu implements Tab, Initializable {
    private final ClientMasterController controller;
    @FXML
    private TableView<GameLogSummaryEntry> gameHistoryList;
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
    private ImageView avatar;

    public PlayerAccountMenu() {
        controller = Client.getConnector().getController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedInfo();
        initializeTableGameHistoryList();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerAccountMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @FXML
    private void logout() throws IOException {
        PlayerMainMenuLayout.getAudioClip().stop();
        //controller.logout();
        TabHandler.getTabHandler().logout();
        ViewHandler.getViewHandler().logout();
    }

    @FXML
    private void editInfo(ActionEvent actionEvent) {
        TabHandler.getTabHandler().push(new PlayerEditProfile());
    }

    @FXML
    void gameLogSummaryTableSelected(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                if (gameHistoryList.getSelectionModel().getSelectedItems().size() != 0) {
                    GameLogSummaryEntry gameEntry = gameHistoryList.getSelectionModel().getSelectedItems().get(0);
                    TabHandler.getTabHandler().push(new PlayerGameMenu(gameEntry.getGameName()));
                }
            }

        }
    }

    private void initializedInfo() {
        username.setText(controller.getPlayerUsername());
        firstName.setText(controller.getPlayerFirstName());
        lastName.setText(controller.getPlayerLastName());
        email.setText(controller.getPlayerEmail());
        phoneNumber.setText(controller.getPlayerPhoneNumber());
        score.setText(controller.getPlayerPoints());
        bio.setText(controller.getPlayerBio());
        money.setText(controller.getPlayerMoney());
        date.setText(controller.getPlayerDate());
        wins.setText(controller.getPlayerWins() + " Wins");
        friends.setText(controller.getPlayerFriendCount());
        avatar.setImage(controller.getPlayerImage());
    }

    private void initializeTableGameHistoryList() {
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
        gameHistoryList.setPlaceholder(new Label("You have not played a game."));

        gameHistoryList.getColumns().addAll(gameImageColumn, gameNameColumn, frequencyColumn, winsColumn, scoreColumn, lastPlayColumn);
        gameHistoryList.getItems().addAll(controller.getGameHistory());
    }
}
