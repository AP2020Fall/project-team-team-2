package model.Entry;

import model.GameLog;
import model.GameLogStates;

import java.time.LocalDateTime;

public class GameLogEntry {
    private  String gameName;
    private  String enemies;
    private  GameLogStates result;
    private  LocalDateTime timeFinished;

    public GameLogEntry(GameLog gameLog)
    {
        enemies = gameLog.getEnemyUsernames().toString();
        result = gameLog.getResult();
        timeFinished = gameLog.getTimeFinished();
        gameName = gameLog.getGameName();
    }

    public String getGameName() {
        return gameName;
    }

    public String getEnemies() {
        return enemies;
    }

    public GameLogStates getResult() {
        return result;
    }

    public LocalDateTime getTimeFinished() {
        return timeFinished;
    }
}
