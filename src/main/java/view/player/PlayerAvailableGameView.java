package view.player;

import controller.ClientMasterController.ClientMasterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.Client;
import model.Player;
import view.Tab;
import view.TabHandler;
import view.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerAvailableGameView implements Tab, Initializable {

    @FXML
    public HBox hBoxContainer;
    @FXML
    private Button back;

    private final ClientMasterController controller;

    public PlayerAvailableGameView(String availableGameId){
        controller = Client.getConnector().getController();
        Client.getClientInfo().setAvailableGameId(availableGameId);
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerAvailableGameView.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateVBox();
    }
    public void updateVBox(){
        hBoxContainer.getChildren().clear();
        for(Player player: controller.getJoinedPlayers()) {
            Circle circle = new Circle(60);
            circle.setFill(new ImagePattern(player.getImage()));
            Label playerName = new Label(player.getUsername());
            VBox playerVBox = new VBox(circle,playerName);
            playerVBox.setAlignment(Pos.CENTER);
            playerVBox.setSpacing(30);
            hBoxContainer.getChildren().add(playerVBox);
        }
    }

    @FXML
    void refresh() {
        updateVBox();
    }



    @FXML
    private Button ready;

    @FXML
    void leave() {
        TabHandler.getTabHandler().back();

    }

    @FXML
    void playerReady() {

    }



}
