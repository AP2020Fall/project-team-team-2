package model.Entry;

import model.GameLog;

import java.time.LocalDateTime;

public class GameLogEntry {
    private final int frequency;
    private final int wins;
    private final int score;
    private final LocalDateTime lastPlay;
    private final String gameName;

    public GameLogEntry(GameLog gameLog)
    {
        frequency = gameLog.getFrequency();
        wins = gameLog.getWins();
        score = gameLog.getScore();
        lastPlay = gameLog.getLastPlay();
        gameName = gameLog.getGameName();
    }
    public int getFrequency() {
        return frequency;
    }

    public int getWins() {
        return wins;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getLastPlay() {
        return lastPlay;
    }

    public String getGameName() {
        return gameName;
    }
}
