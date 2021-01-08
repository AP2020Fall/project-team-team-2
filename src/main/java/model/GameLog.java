package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GameLog {


    private final String playerUsername;
    private final ArrayList<String> enemyUsernames;
    private final String gameName;
    private final GameLogStates result;
    private final LocalDateTime timeFinished;

    public GameLog(Player player, ArrayList<Player> enemies, String gameName, GameLogStates result,
                   LocalDateTime timeFinished) {
        this.playerUsername = player.getUsername();
        this.gameName = gameName;
        enemyUsernames = new ArrayList<>();
        for (Player enemy : enemies) {
            enemyUsernames.add(enemy.getUsername());
        }
        this.result = result;
        this.timeFinished = timeFinished;
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
}


