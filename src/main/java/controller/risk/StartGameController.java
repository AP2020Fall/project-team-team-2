package controller.risk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Map;
import model.Country;
import model.Player;
import view.RiskGameView;

import java.net.URL;
import java.util.*;


public class StartGameController implements Initializable{
    private static java.util.Map<String, Object> primitiveSettings = new HashMap<String, Object>();

    {
        primitiveSettings.put("Map Number", 1);
        primitiveSettings.put("Placement", false);
        primitiveSettings.put("Alliance", false);
        primitiveSettings.put("Blizzards", false);
        primitiveSettings.put("Fog of War", false);
        primitiveSettings.put("Duration", 0);
        primitiveSettings.put("PlayersNum", 2);
        primitiveSettings.put("Players" , null);
    }
    private static Map mainMap;
    public StartGameController(ArrayList<Player> players){
        setPrimitiveSettings("Players" , players);
    }
    public static void main(String[] args) {}

    public RiskGameView startGame() {
        return new RiskGameView(this.primitiveSettings , generateSoldiersNumber());
    }

    public static java.util.Map<String, Object> getPrimitiveSettings() {
        return primitiveSettings;
    }

    public String setMapNumber(int mapNumber) {
        boolean checkExistence = model.Map.checkMapExists(mapNumber);
        String callback = "";
        if (checkExistence) {
            setPrimitiveSettings("Map Number", mapNumber);
            callback = "Map Number Changed To " + mapNumber;
        } else {
            callback = "No Map Exists With This Number";
        }
        return callback;
    }

    public String setPlayerNumber(int playerNumber) {
        String callback = "";
        if (playerNumber > 3) {
            callback = "Invalid Number of Player, Please try a number less than 3";
        } else {
            setPrimitiveSettings("PlayersNum", playerNumber);
            callback = "Players Number Set to " + playerNumber;
        }
        return callback;
    }

    public String setDurationTime(int number) {
        String callback = "";
        if (number <= 0) {
            callback = "You should choose a number bigger than zero";
        } else if (number > 75) {
            callback = "You can't choose a number bigger than 75 seconds";
        } else {
            setPrimitiveSettings("Duration", number);
            callback = "Duration changed to " + number;
        }
        return callback;
    }

    public void setFogType(boolean type) {
        if(type){
            setPrimitiveSettings("Fog of War", true);
        }else{
            setPrimitiveSettings("Fog of War", false);
        }
    }
    public int generateSoldiersNumber(){
        int soldierNumber = 0;
        switch ((Integer)this.primitiveSettings.get("Map Number")){
            case 0: soldierNumber = 10;break;
            case 1:
            case 6:
                soldierNumber = 20;break;
            case 2:
            case 4:
                soldierNumber = 25;break;
            case 3: soldierNumber = 30;break;
            case 5: soldierNumber = 15;break;
            case 7:
            case 9:
                soldierNumber = 26;break;
            case 8: soldierNumber = 32;break;
            case 10: soldierNumber = 40;break;
        }
        return soldierNumber;
    }
    public void setAllianceType(boolean type) {
        if(type){
            setPrimitiveSettings("Alliance", true);
        }else{
            setPrimitiveSettings("Alliance", false);
        }
    }
    public void setBlizzardsType(boolean type) {
        if(type){
            setPrimitiveSettings("Blizzards", true);
        }else{
            setPrimitiveSettings("Blizzards", false);
        }
    }

    public void setPlacementType(boolean type) {
        if(type){
            setPrimitiveSettings("Placement", true);
        }else{
            setPrimitiveSettings("Placement", false);
        }
    }

    public void setPrimitiveSettings(String index, Object value) {
        this.primitiveSettings.put(index, value);
    }

    public String generateGameId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public void setMapSoldiers(Country country, int soldiers) {
        country.setSoldiers(soldiers);
    }
    @FXML
    public void friendAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
