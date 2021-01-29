package view.player;

import controller.ClientMasterController.ClientMasterController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.paint.Paint;
import main.Client;
import model.Entry.GameLogEntry;
import model.Entry.ScoreboardEntry;
import model.GameLogStates;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PlayerGameMenu implements Tab, Initializable {
    @FXML
    private Label detail = new Label();
    @FXML
    private Label frequency = new Label();
    @FXML
    private Label win = new Label();
    @FXML
    private ImageView gameImage;
    @FXML
    private TableView<ScoreboardEntry> scoreboard;
    @FXML
    private FontAwesomeIconView favorite = new FontAwesomeIconView();
    public TreeTableView<GameLogEntry> gameLog;
    private final ClientMasterController controller;

    public PlayerGameMenu(String gameName) {
        Client.getClientInfo().setGameName(gameName);
        controller = Client.getConnector().getController();
    }

    @Override
    public Parent show() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerGameMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (controller.isFavorite())
            fillYellow();
        else
            unFill();
        initializeInfo();
        initializeTreeGameLogList();
        initializeScoreboardTable();
    }

    @FXML
    private void addToFavorite() {
        if (controller.isFavorite()) {
            unFill();
            controller.removeFromFavorites();
        } else {
            fillYellow();
            controller.addToFavorites();
        }
    }

    @FXML
    private void runGame() {
        TabHandler.getTabHandler().push(new PlayerRunGameView(Client.getClientInfo().getGameName(),
                controller.getCasualEvent()));
    }

    @FXML
    void scoreboardSelected(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                if (scoreboard.getSelectionModel().getSelectedItems().size() != 0) {
                    ScoreboardEntry scoreboardEntry = scoreboard.getSelectionModel().getSelectedItems().get(0);
                    TabHandler.getTabHandler().push(new PlayerProfileView(scoreboardEntry.getPlayerName()));
                }
            }

        }
    }

    private void initializeInfo() {
        detail.setText(controller.getGameDetails());
        frequency.setText(controller.getPlayedFrequency());
        win.setText(controller.getWinsCount());
        gameImage.setImage(controller.getGameImage());
        gameImage.setFitWidth(120);
        gameImage.setFitHeight(120);
    }

    private void initializeTreeGameLogList() {
        TreeTableColumn<GameLogEntry, String> gameLogGameName = new TreeTableColumn<>("Game");
        gameLogGameName.setCellValueFactory(new TreeItemPropertyValueFactory<>("gameName"));

        TreeTableColumn<GameLogEntry, String> gameLogEnemies = new TreeTableColumn<>("Enemies");
        gameLogEnemies.setCellValueFactory(new TreeItemPropertyValueFactory<>("enemies"));

        TreeTableColumn<GameLogEntry, GameLogStates> gameLogState = new TreeTableColumn<>("Result");
        gameLogState.setCellValueFactory(new TreeItemPropertyValueFactory<>("result"));

        TreeTableColumn<GameLogEntry, LocalDateTime> gameLogTime = new TreeTableColumn<>("Time Finished");
        gameLogTime.setCellValueFactory(new TreeItemPropertyValueFactory<>("timeFinished"));
        TreeItem<GameLogEntry> gameLogRoot = new TreeItem<>();
        if (controller.hasPlayedGame()) {
            for (GameLogEntry gameLogEntry : controller.getGameLog())
                gameLogRoot.getChildren().add(new TreeItem<>(gameLogEntry));
            gameLog.setRoot(gameLogRoot);
            gameLog.setShowRoot(false);
        }
        gameLog.setPlaceholder(new Label("Hasn't been played."));
        gameLog.getColumns().addAll(gameLogGameName, gameLogEnemies, gameLogState, gameLogTime);
    }

    private void fillYellow() {
        favorite.setFill(Paint.valueOf("#ffc000ff"));
    }

    private void unFill() {
        favorite.setFill(Paint.valueOf("#00000000"));
    }

    private void initializeScoreboardTable() {
        TableColumn<ScoreboardEntry, String> scoreboardNameColumn = new TableColumn<>("Player");
        scoreboardNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        TableColumn<ScoreboardEntry, Integer> scoreboardWinsColumn = new TableColumn<>("Wins");
        scoreboardWinsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<ScoreboardEntry, Integer> scoreboardFrequencyColumn = new TableColumn<>("Frequency");
        scoreboardFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("numPlayed"));

        scoreboard.setPlaceholder(new Label("No one has played the game."));
        scoreboard.getColumns().addAll(scoreboardNameColumn, scoreboardFrequencyColumn, scoreboardWinsColumn);
        scoreboard.getItems().addAll(controller.getScoreboard());
    }
}
