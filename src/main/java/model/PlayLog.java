package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.ServerMasterController.SQLConnector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayLog {
    private final String gameName;
    private final ArrayList<String> players;
    private final String winner;
    private final LocalDateTime time;
    private final String playLogId;

    public PlayLog(String gameName,ArrayList<Gamer> players, Player winner, LocalDateTime time,String playLogId)
    {
        this.gameName =gameName;
        this.players = new ArrayList<>();
        for(Gamer player :players)
            this.players.add(player.getUsername());
        this.winner = winner.getUsername();
        this.time = time;
        this.playLogId = playLogId;
    }

    public PlayLog(Map<String, Object> game) {
        this.gameName = (String) game.get("game_name");
        this.playLogId = (String) game.get("play_log_id");
        this.winner = (String) game.get("winner");
        this.players = new Gson().fromJson((String) game.get("usernames"), new TypeToken<ArrayList<String>>() {
        }.getType());
        this.time = LocalDateTime.parse((String) game.get("timestamp"));
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public String getWinner() {
        return winner;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getGameName() {
        return gameName;
    }

    public String getPlayLogId() {
        return playLogId;
    }

    public void addPlayLog() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("game_name", gameName);
        resultMap.put("play_log_id", playLogId);
        resultMap.put("winner", winner);
        resultMap.put("usernames",new Gson().toJson(players));
        resultMap.put("timestamp", time.toString());

        SQLConnector.insertInDatabase(resultMap, "play_logs");
    }

    @Override
    public String toString() {
        return  gameName + ": " + time +
                ", players=" + players +
                ", winner=" + winner;
    }

}
