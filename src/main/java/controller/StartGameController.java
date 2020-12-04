package controller;
import model.Map;
import view.RiskGameView;

import javax.print.DocFlavor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;


public class StartGameController {
    private java.util.Map<String, Object> primitiveSettings = new HashMap<String, Object>();
    {
        primitiveSettings.put("Map Number", 0);
        primitiveSettings.put("Placement", false);
        primitiveSettings.put("Alliance", false);
        primitiveSettings.put("Blizzards", false);
        primitiveSettings.put("Fog of War", false);
        primitiveSettings.put("Duration", 0);
        primitiveSettings.put("PlayersNum", 0);
    }

    public void startGame() {
        String gameId = generateGameId();
        String filename = "src/main/resources/gameIDs/"+gameId+".txt";
        System.out.println("Game Started with ID " + gameId);
        Path path = Paths.get(filename);
        try {
            Files.writeString(path, "start", StandardCharsets.UTF_8);
        } catch (IOException ex) {
            // Handle exception
        }
        RiskGameView riskGame = new RiskGameView(this.primitiveSettings , generateGameId());
        riskGame.riskGameView();
    }

    public java.util.Map<String, Object> getPrimitiveSettings() {
        return primitiveSettings;
    }
    public String setMapNumber(int mapNumber){
        boolean checkExistence = model.Map.checkMapExists(mapNumber);
        String callback = "";
        if(checkExistence){
            setPrimitiveSettings("Map Number" , mapNumber);
            callback = "Map Number Changed To " + mapNumber;
        }else{
            callback = "No Map Exists With This Number";
        }
        return callback;
    }
    public String setPlayerNumber(int playerNumber){
        String callback = "";
        if(playerNumber > 5){
            callback = "Invalid Number of Player, Please try a number less than 5";
        }else{
            setPrimitiveSettings("PlayersNum" , playerNumber);
            callback = "Players Number Set to " + playerNumber;
        }
        return callback;
    }
    public String setDurationTime(int number){
        String callback = "";
        if(number <= 0){
            callback = "You should choose a number bigger than zero";
        }else if (number > 75){
            callback = "You can't choose a number bigger than 75 seconds";
        }else{
            setPrimitiveSettings("Duration" , number);
            callback = "Duration changed to " + number;
        }
        return callback;
    }
    public String setFogType(String type){
        String callback = "";
        switch (type.toLowerCase()) {
            case "on":
                setPrimitiveSettings("Fog of War", true);
                callback = "Fog type changed to " + type;
                break;
            case "off":
                setPrimitiveSettings("Fog of War", false);
                callback = "Fog type changed to " + type;
                break;
            default: callback = "Please choose between 'on' And 'off'";break;
        }
        return callback;
    }
    public String setAllianceType(String type){
        String callback = "";
        switch (type.toLowerCase()) {
            case "on":
                setPrimitiveSettings("Alliance", true);
                callback = "Alliance changed to " + type;
                break;
            case "off":
                setPrimitiveSettings("Alliance", false);
                callback = "Alliance changed to " + type;
                break;
            default:callback = "Please choose between 'on' And 'off'";break;
        }
        return callback;
    }
    public String setBlizzardsType(String type){
        String callback = "";
        switch (type) {
            case "on":
                setPrimitiveSettings("Blizzards", true);
                callback = "Blizzards changed to " + type;
                break;
            case "off":
                setPrimitiveSettings("Blizzards", false);
                callback = "Blizzards changed to " + type;
                break;
            default:callback = "Please choose between 'on' And 'off'";break;
        }
        return callback;
    }
    public String setPlacementType(String type){
        String callback = "";
        switch (type) {
            case "on":
                setPrimitiveSettings("Placement", true);
                callback = "Placement changed to " + type;
                break;
            case "off":
                setPrimitiveSettings("Placement", false);
                callback = "Placement changed to " + type;
                break;
            default:callback = "Please choose between 'on' And 'off'";break;
        }
        return callback;
    }
    public void setPrimitiveSettings(String index, Object value) {
        this.primitiveSettings.put(index, value);
    }
    public String generateGameId() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
