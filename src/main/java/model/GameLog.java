package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.Controller;
import controller.ServerMasterController.SQLConnector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLog {


    private final String playerUsername;
    private final ArrayList<String> enemyUsernames;
    private final String gameName;
    private final GameLogStates result;
    private final LocalDateTime timeFinished;
    private final String gameLogId;

    public GameLog(Player player, ArrayList<Player> enemies, String gameName, GameLogStates result,
                   LocalDateTime timeFinished,String gameLogId) {
        this.playerUsername = player.getUsername();
        this.gameName = gameName;
        enemyUsernames = new ArrayList<>();
        for (Player enemy : enemies) {
            enemyUsernames.add(enemy.getUsername());
        }
        this.result = result;
        this.timeFinished = timeFinished;
        this.gameLogId = gameLogId;
    }

    public GameLog(Map<String, Object> gameLog) {
        playerUsername = (String)gameLog.get("player_username");
        gameName = (String)gameLog.get("game_name");
        enemyUsernames =new Gson().fromJson((String) gameLog.get("enemies"), new TypeToken<ArrayList<String>>() {
        }.getType());
        result = (GameLogStates) gameLog.get("status");
        gameLogId = (String)gameLog.get("game_log_id");
        timeFinished = LocalDateTime.parse( (String)gameLog.get("time_finished"));
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public ArrayList<String> getEnemyUsernames() {
        return enemyUsernames;
    }

    public GameLogStates getResult() {
        return result;
    }

    public LocalDateTime getTimeFinished() {
        return timeFinished;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameLogId() {
        return gameLogId;
    }

    public void addGameLog() {
        java.util.Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("game_name", this.getGameName());
        resultMap.put("player_username", this.getPlayerUsername());
        resultMap.put("enemies", new Gson().toJson(this.getEnemyUsernames()));
        resultMap.put("status",this.getResult());
        resultMap.put("game_log_id", this.getGameLogId());
        resultMap.put("time_finished", this.getTimeFinished().toString());
        SQLConnector.insertInDatabase(resultMap,"game_log");
    }

    public static List<java.util.Map<String, Object>> SQLGameLogSearch(String column, String value) {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put(column, value);
        List<java.util.Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "game_log");
        if (thisAccount == null || thisAccount.isEmpty()) {
            System.out.println("[MODEL]: GameLog with " + column + " = " + value + " couldn't be found");
            return null;
        }
        return thisAccount;
    }
    public static GameLog getGameLogById(String gameLogId) {
        List<Map<String,Object>> gameLog = SQLGameLogSearch("game_log_id",gameLogId);
        if(gameLog == null || gameLog.isEmpty())
        {
            return null;
        }
        return new GameLog(gameLog.get(0));
    }

}


