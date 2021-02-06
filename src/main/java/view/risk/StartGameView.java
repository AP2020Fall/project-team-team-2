package view.risk;

import controller.ClientMasterController.ClientMasterController;
import controller.risk.StartGameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import model.Event;
import model.Player;
import view.View;
import view.ViewHandler;
import view.login.LoginMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartGameView implements View, Initializable {
    public static ClientMasterController clientMasterController;
    //private StartGameController startGameController;
    @FXML
    private ToggleButton friendToggle;
    @FXML
    private ToggleButton blizzardToggle;
    @FXML
    private ToggleButton manualPlacementToggle;
    @FXML
    private ToggleButton fogWarToggle;
    @FXML
    private TextField playerNum;
    @FXML
    private TextField mapNum;
    @FXML
    private TextField limitTimeNum;
    @FXML
    private Button startButton;


    public StartGameView(ArrayList<Player> players, Event event) {
        clientMasterController = new ClientMasterController();
        //this.startGameController = new StartGameController(players, event);
    }

    public static boolean checkToggle(boolean inputBoolean) {
        if (inputBoolean) {
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public void friendAction() {
        allianceType(checkToggle(!friendToggle.isSelected()));
    }

    @FXML
    public void blizzardAction() {
        blizzardsType(checkToggle(!blizzardToggle.isSelected()));
    }

    @FXML
    public void manualPlacement() {
        placementType(checkToggle(!manualPlacementToggle.isSelected()));
    }

    @FXML
    public void fogWar() {
        fogsType(checkToggle(!fogWarToggle.isSelected()));
    }

    @FXML
    public void playersNum() {
        String playersNum = playerNum.getText();
        changePlayersNumber(playersNum);
    }

    @FXML
    public void startButtonClick() {
        chooseMapNumber(mapNum.getText());
        changeDurationTime(limitTimeNum.getText());
        changePlayersNumber(playerNum.getText());
        int mapNum = (int) clientMasterController.getPrimitiveSettings().get("Map Number");
        if (mapNum <= 10 && mapNum >= 1) {
            ViewHandler.getViewHandler().push(this.clientMasterController.startGame());
        }
    }

    public boolean checkIntInput(Object input) {
        if (input instanceof Integer) {
            return true;
        } else {
            return false;
        }
    }

    public void changePlayersNumber(String strNumber) {
        int playerNumber = Integer.parseInt(strNumber);
        String callback = clientMasterController.setPlayerNumber(playerNumber);
       // System.out.println(callback);

    }

    public void chooseMapNumber(String strNumber) {
        int mapNumber = Integer.parseInt(strNumber);
        String callback = clientMasterController.setMapNumber(mapNumber);
       // System.out.println(callback);
    }

    public void changeDurationTime(String strNumber) {
        int number = Integer.parseInt(strNumber);
        String callback = clientMasterController.setDurationTime(number);
        //System.out.println(callback);
    }

    public void placementType(boolean type) {
        clientMasterController.setPlacementType(type);
    }

    public void allianceType(boolean selected) {
        clientMasterController.setAllianceType(selected);
    }

    public void blizzardsType(boolean type) {
        clientMasterController.setBlizzardsType(type);
    }

    public void fogsType(boolean type) {
        clientMasterController.setFogType(type);
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/game/startGame.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
        //window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
