package view;

import controller.risk.StartGameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.util.ArrayList;

public class StartGameView implements View {
    private StartGameController startGameController;
    @FXML
    private ToggleButton friendToggle;
    @FXML
    private ToggleButton blizzardToggle;
    @FXML
    private ToggleButton manualPlacementToggle;
    @FXML
    private ToggleButton fogWarToggle;

    public StartGameView(ArrayList<Player> players){
        this.startGameController = new StartGameController(players);
    }
    public StartGameView() {
        Player newPlayer1 = new Player("new" , "mew2");
        Player newPlayer2 = new Player("new2" , "mew22");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(newPlayer1);
        players.add(newPlayer2);
        new StartGameView(players);
    }
    public void action()
    {
        System.out.println("this action works");
    }

    public void changePlayersNumber(String strNumber) {
        int playerNumber = Integer.parseInt(strNumber);
        String callback = startGameController.setPlayerNumber(playerNumber);
        System.out.println(callback);

    }

    public void chooseMapNumber(String strNumber) {
        int mapNumber = Integer.parseInt(strNumber);
        String callback = startGameController.setMapNumber(mapNumber);
        System.out.println(callback);
    }

    public void changeDurationTime(String strNumber) {
        int number = Integer.parseInt(strNumber);
        String callback = startGameController.setDurationTime(number);
        System.out.println(callback);
    }

    public void placementType(String type) {
        String callback = startGameController.setPlacementType(type);
        System.out.println(callback);
    }

    public void allianceType(String type) {
        String callback = startGameController.setAllianceType(type);
        System.out.println(callback);
    }

    public void blizzardsType(String type) {
        String callback = startGameController.setBlizzardsType(type);
        System.out.println(callback);
    }

    public void fogsType(String type) {
        String callback = startGameController.setFogType(type);
        System.out.println(callback);
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/game/startGame.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(true);
        window.show();
    }

}
