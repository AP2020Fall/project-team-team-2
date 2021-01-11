package view.player;

import controller.player.PlayerEventMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.Event;
import view.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerEventMenu implements Tab, Initializable {

    @FXML private Label gameName;
    @FXML private Label startDate;
    @FXML private Label endDate;
    @FXML private Label score;
    PlayerEventMenuController controller;

    public PlayerEventMenu(Event event) {
        controller = new PlayerEventMenuController(event);
    }
    @Override
    public Parent show() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerEventMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeInfo();
    }

    public void join() {
        controller.join();
        //todo implement
    }
    private void initializeInfo()
    {
        gameName.setText(controller.getGameName());
        startDate.setText(controller.getStartDate());
        endDate.setText(controller.getEndDate());
        score.setText(controller.getScore());
    }
}