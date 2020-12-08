package model;

import java.time.LocalDateTime;

public class GameLog {

    private int frequency;
    private int wins;
    private String gameLogId;
    private int score;
    private LocalDateTime lastPlay;

    private Game game;

    public int getFrequency() {
        return frequency;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return frequency - wins;
    }

    public Game getGame() {
        return game;
    }

    public String getGameLogId() {
        return gameLogId;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getLastPlay() {
        return lastPlay;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public void setLastPlay(LocalDateTime lastPlay)
    {
        this.lastPlay = lastPlay;
    }
}
