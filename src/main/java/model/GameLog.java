package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GameLog {


    private final String playerId;
    private final ArrayList<String> enemyIds;
    private final String gameName;
    private final GameLogStates result;
    private final LocalDateTime timeFinished;

    /*private int frequency;
    private int wins;
    private String gameLogId;
    private int score;
    private LocalDateTime lastPlay;
    private String gameName;
*/
    public GameLog(Player player, ArrayList<Player> enemies, String gameName, GameLogStates result, LocalDateTime timeFinished) {
        this.playerId = player.getAccountId();
        this.gameName = gameName;
        enemyIds = new ArrayList<>();
        for (Player enemy : enemies) {
            enemyIds.add(enemy.getAccountId());
        }
        this.result = result;
        this.timeFinished = timeFinished;
    }

    public String getPlayerId() {
        return playerId;
    }

    public ArrayList<String> getEnemyIds() {
        return enemyIds;
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
}


