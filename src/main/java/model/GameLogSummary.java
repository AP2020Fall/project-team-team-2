package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.ServerMasterController.SQLConnector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLogSummary {
    private int frequency;
    private int wins;
    private int score;
    private LocalDateTime lastPlay;
    private final String gameLogSummaryId;
    private final String gameName;
    private final ArrayList<String> gameLogs;

    public GameLogSummary(String gameName, String gameLogId) {
        this.gameLogSummaryId = gameLogId;
        this.gameName = gameName;
        this.wins = 0;
        this.frequency = 0;
        this.score = 0;
        lastPlay = LocalDateTime.now();
        gameLogs = new ArrayList<>();
    }

    public GameLogSummary(Map<String, Object> gameLogSummary) {
        frequency = Integer.parseInt((String) gameLogSummary.get("frequency"));
        wins = Integer.parseInt((String) gameLogSummary.get("wins"));
        score = Integer.parseInt((String) gameLogSummary.get("score"));
        gameName = (String) gameLogSummary.get("game_name");
        gameLogSummaryId = (String) gameLogSummary.get("game_log_summary_id");
        lastPlay = LocalDateTime.parse((String) gameLogSummary.get("last_play"));
        gameLogs = new Gson().fromJson((String) gameLogSummary.get("game_logs"),
                new TypeToken<ArrayList<String>>() {
                }.getType());
    }

    public void editField(String field, String value) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("game_log_summary_id", this.gameLogSummaryId);
        Map<String, Object> newValueMap = new HashMap<>();
        newValueMap.put(field, value);
        SQLConnector.updateTable(conditionMap, newValueMap, "game_log_summary");
    }


    public int getFrequency() {
        return frequency;
    }

    public int getWins() {
        return wins;
    }

    public String getGameLogSummaryId() {
        return gameLogSummaryId;
    }

    public int getLosses() {
        return frequency - wins;
    }

    public String getGameName() {
        return gameName;
    }

    public ArrayList<GameLog> getGameLogs() {
        ArrayList<GameLog> result = new ArrayList<>();
        for (String gameLogId : gameLogs) {
            result.add(GameLog.getGameLogById(gameLogId));
        }
        return result;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getLastPlay() {
        return lastPlay;
    }

    public void addGameLogSummary() {
        java.util.Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("frequency", frequency);
        resultMap.put("wins", wins);
        resultMap.put("score",score);
        resultMap.put("last_play", lastPlay.toString());
        resultMap.put("game_name", gameName);
        resultMap.put("game_logs",new Gson().toJson(gameLogs));
        resultMap.put("game_log_summary_id", gameLogSummaryId);
        SQLConnector.insertInDatabase(resultMap, "game_log_summary");
    }

    public static List<Map<String, Object>> SQLGameLogSummarySearch(String column, String value) {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put(column, value);
        List<java.util.Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "game_log_summary");
        if (thisAccount == null || thisAccount.isEmpty()) {
            System.out.println("[MODEL]: GameLogSummary with " + column + " = " + value + " couldn't be found");
            return null;
        }
        return thisAccount;
    }

    public static GameLogSummary getGameLogSummaryById(String gameLogSummaryId) {
        List<Map<String, Object>> gameLogSummary = SQLGameLogSummarySearch("game_log_summary_id",
                gameLogSummaryId);
        if (gameLogSummary == null || gameLogSummary.isEmpty()) {
            return null;
        }
        return new GameLogSummary(gameLogSummary.get(0));
    }

    public void updateForWin(int score, LocalDateTime lastPlay, GameLog gameLog) {
        //todo add game log to the database
        frequency++;
        wins++;
        this.score += score;
        this.lastPlay = lastPlay;
        gameLog.addGameLog();
        gameLogs.add(gameLog.getGameLogId());
        editField("game_logs",new Gson().toJson(gameLogs));
    }

    public void updateForLoss(int score, LocalDateTime lastPlay, GameLog gameLog) {
        frequency++;
        this.score += score;
        this.lastPlay = lastPlay;
        gameLog.addGameLog();
        gameLogs.add(gameLog.getGameLogId());
        editField("game_logs",new Gson().toJson(gameLogs));
    }

    public void updateForDraw(int score, LocalDateTime lastPlay, GameLog gameLog) {
        frequency++;
        this.score += score;
        this.lastPlay = lastPlay;
        gameLog.addGameLog();
        gameLogs.add(gameLog.getGameLogId());
        editField("game_logs",new Gson().toJson(gameLogs));
    }

    @Override
    public String toString() {
        return "game= " + gameName + '\n' +
                "number of times played= " + frequency + '\n' +
                "number of times won= " + wins + '\n' +
                "number of times lost= " + (frequency - wins) + '\n' +
                "score= " + score + '\n' +
                "last time played= " + lastPlay + "\n";
    }
}
