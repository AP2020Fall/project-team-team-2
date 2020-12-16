package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GameLog {

    private int frequency;
    private int wins;
    private String gameLogId;
    private int score;
    private LocalDateTime lastPlay;
    private String gameName;

    public GameLog(String gameName,String gameLogId)
    {
        this.gameLogId= gameLogId;
        this.gameName= gameName;
        this.wins = 0;
        this.frequency = 0;
        this.score = 0;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return frequency - wins;
    }

    public String getGameName() {
        return gameName;
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

    public void updateForWin(int score, LocalDateTime lastPlay)
    {
        frequency++;
        wins++;
        score += score;
        this.lastPlay = lastPlay;
    }
    public void updateForLoss(int score,LocalDateTime lastPlay)
    {
        frequency++;
        score += score;
        this.lastPlay= lastPlay;
    }

    public void updateForDraw(int score,LocalDateTime lastPlay)
    {
        frequency++;
        score+= score;
        this.lastPlay = lastPlay;
    }

    @Override
    public String toString() {
        return  "game= " + gameName + '\n' +
                "number of times played= " + frequency + '\n' +
                "number of times won= " + wins + '\n' +
                "number of times lost= " + (frequency - wins) + '\n' +
                "score= " + score + '\n' +
                "last time played= " + lastPlay + "\n";
    }
}
