package view.player;

import controller.GameMenuController;
import controller.PlayerEventMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.Entry.GameLogEntry;
import model.Event;
import model.GameLogStates;
import view.Tab;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PlayerEventMenu implements Tab, Initializable {

    @FXML private Label gameName;
    @FXML private Label startData;
    @FXML private Label endData;
    @FXML private Label score;
    PlayerEventMenuController controller;

    public PlayerEventMenu(Event event) {
        controller = new PlayerEventMenuController(event);
    }
    @Override
    public Parent show() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/playerEventMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void join() {
    }
    private void InitializeInfo()
    {
       /* detail.setText(controller.getDetails());
        frequency.setText(controller.getPlayedFrequency());
        win.setText(controller.getWinsCount());*/
    }
/*
    @FXML private void addToFavorite() {
        if(!favoriteButton.isSelected()) {
            controller.removeFromFavorites();
        }
        else {
            controller.addToFavorites();
        }
    }

    @FXML private void runGame() {
        System.out.println("must run game");
    }

    @FXML private void scoreboard(ActionEvent actionEvent) {
    }


    private void InitializeTreeGameLogList()
    {
        TreeTableColumn<GameLogEntry, String> gameLogGameName = new TreeTableColumn<>("Game");
        gameLogGameName.setCellValueFactory(new TreeItemPropertyValueFactory<>("gameName"));

        TreeTableColumn<GameLogEntry, String> gameLogEnemies = new TreeTableColumn<>("Enemies");
        gameLogEnemies.setCellValueFactory(new TreeItemPropertyValueFactory<>("enemies"));

        TreeTableColumn<GameLogEntry, GameLogStates> gameLogState = new TreeTableColumn<>("Result");
        gameLogState.setCellValueFactory(new TreeItemPropertyValueFactory<>("result"));

        TreeTableColumn<GameLogEntry, LocalDateTime> gameLogTime = new TreeTableColumn<>("Time Finished");
        gameLogTime.setCellValueFactory(new TreeItemPropertyValueFactory<>("timeFinished"));
        TreeItem<GameLogEntry> gameLogRoot = new TreeItem<>();
        if(controller.hasPlayedGame()) {
            for (GameLogEntry gameLogEntry : controller.getGameLog())
                gameLogRoot.getChildren().add(new TreeItem<>(gameLogEntry));
            gameLog.setRoot(gameLogRoot);
            gameLog.setShowRoot(false);
        }
        else
        {
            gameLog.setPlaceholder(new Label("Hasn't been played."));
        }

        gameLog.getColumns().addAll(gameLogGameName,gameLogEnemies,gameLogState,gameLogTime);
    }*/
}