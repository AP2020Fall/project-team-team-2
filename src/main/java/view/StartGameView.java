package view;

import controller.risk.StartGameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import model.Player;
import view.login.LoginMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartGameView implements View, Initializable {
    private StartGameController startGameController;
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


    public StartGameView(ArrayList<Player> players) {
        this.startGameController = new StartGameController(players);
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
    public void mapNum() {
        String mapsNum = mapNum.getText();
        changeMapNumber(mapsNum);
    }

    private void changeMapNumber(String mapsNum) {
        int mapNumber = Integer.parseInt(mapsNum);
        startGameController.setMapNumber(mapNumber);
    }

    @FXML
    public void limitNum() {
        String limitTime = limitTimeNum.getText();
        changeDurationTime(limitTime);
    }

    @FXML
    public void startButtonClick() {
        /*if (!checkIntInput(limitTimeNum.getText())) {

        } else if (!checkIntInput(mapNum.getText())) {

        } else if (!checkIntInput(playerNum.getText())) {

        } else*/ {
            int mapNum = (int) startGameController.getPrimitiveSettings().get("Map Number");
            if(mapNum <= 10 && mapNum >= 1) {
                System.out.println("run");
                ViewHandler.getViewHandler().push(this.startGameController.startGame());
            }
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

    public void placementType(boolean type) {
        startGameController.setPlacementType(type);
    }

    public void allianceType(boolean selected) {
        startGameController.setAllianceType(selected);
    }

    public void blizzardsType(boolean type) {
        startGameController.setBlizzardsType(type);
    }

    public void fogsType(boolean type) {
        startGameController.setFogType(type);
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

    public static boolean checkToggle(boolean inputBoolean) {
        if (inputBoolean) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
