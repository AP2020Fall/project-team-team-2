package view.player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Player;
import view.View;
import view.ViewHandler;
import view.login.LoginMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JoinedGame implements View, Initializable {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("name","safmkl"));
        players.add(new Player("name","safmkl23"));
        players.add(new Player("name","safmkl3"));
        players.add(new Player("name","safmkl4"));
        ViewHandler.getViewHandler().push(new JoinedGame(players));
    }
    @FXML
    public static HBox hBoxContainer;
    public static List<Player> joinedPlayers;
    private Button back;
    public JoinedGame(List<Player> joinedPlayers){
        JoinedGame.joinedPlayers = joinedPlayers;
    }

    @Override
    public void show(Stage window) throws IOException {
        String fileAddress = "/game/plato/player/joinedGame.fxml";
        FXMLLoader root = new FXMLLoader(getClass().getResource(fileAddress));
        root.setController(this);
        window.setTitle("Joined Game");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public static void updateVBox(){
        JoinedGame.hBoxContainer.getChildren().clear();
        for(Player player:JoinedGame.joinedPlayers) {
            Circle circle = new Circle(60);
            circle.setFill(new ImagePattern(player.getImage()));
            Label playerName = new Label(player.getUsername());
            VBox playerVBox = new VBox(circle,playerName);
            playerVBox.setAlignment(Pos.CENTER);
            playerVBox.setSpacing(30);
            JoinedGame.hBoxContainer.getChildren().add(playerVBox);
        }
    }
    @FXML
    public void leaveTheGame(){

    }
}
