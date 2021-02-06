package view.player;

import controller.ClientMasterController.ClientMasterController;
import controller.player.PlayerEventMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import main.Client;
import model.Event;
import view.AlertMaker;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PlayerEventMenu implements Tab, Initializable {
    @FXML
    private Label gameName;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label score;
    @FXML
    private Label comment;
    @FXML
    private ImageView eventImage;
    private final ClientMasterController controller;
    private final String eventId;
    public PlayerEventMenu(String eventId) {
        this.eventId = eventId;
        controller = Client.getConnector().getController();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerEventMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client.getClientInfo().setEventId(eventId);
        initializeInfo();
    }

    @FXML
    private void join() {
        if(LocalDate.parse( controller.getEventStartDate()).isBefore(LocalDate.now())
                && LocalDate.parse(controller.getEventEndDate()).isAfter(LocalDate.now()))
        TabHandler.getTabHandler().push(new PlayerRunGameView(controller.getEventGameName(),
                Client.getClientInfo().getEventId()));
        else{
            if(!LocalDate.parse( controller.getEventStartDate()).isBefore(LocalDate.now()))
            {
                AlertMaker.showMaterialDialog(TabHandler.getTabHandler().getStackRoot(),
                        TabHandler.getTabHandler().getStackRoot().getChildren().get(0),"Okay","Unable to join",
                        "Event hasn't started yet.");
            }
            if(!LocalDate.parse(controller.getEventEndDate()).isAfter(LocalDate.now()))
            {
                AlertMaker.showMaterialDialog(TabHandler.getTabHandler().getStackRoot(),
                        TabHandler.getTabHandler().getStackRoot().getChildren().get(0),"Okay","Unable to join",
                        "Event has finished.");
            }
        }
    }

    private void initializeInfo() {
        gameName.setText(controller.getEventGameName());
        startDate.setText(controller.getEventStartDate());
        endDate.setText(controller.getEventEndDate());
        score.setText(controller.getEventScore());
        comment.setText(controller.getEventComment());
        eventImage.setImage(controller.getEventImage());
    }
}