package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GameLogSummary {
    private int frequency;
    private int wins;
    private String gameLogSummaryId;
    private int score;
    private LocalDateTime lastPlay;
    private String gameName;
    private ArrayList<GameLog> gameLogs;
    public GameLogSummary(String gameName,String gameLogId)
    {
        this.gameLogSummaryId= gameLogId;
        this.gameName= gameName;
        this.wins = 0;
        this.frequency = 0;
        this.score = 0;
        gameLogs = new ArrayList<>();
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
        return gameLogs;
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

    public void updateForWin(int score, LocalDateTime lastPlay,GameLog gameLog)
    {
        frequency++;
        wins++;
        this.score += score;
        this.lastPlay = lastPlay;
        gameLogs.add(gameLog);
    }
    public void updateForLoss(int score,LocalDateTime lastPlay,GameLog gameLog)
    {
        frequency++;
        this.score += score;
        this.lastPlay= lastPlay;
        gameLogs.add(gameLog);
    }

    public void updateForDraw(int score,LocalDateTime lastPlay,GameLog gameLog)
    {
        frequency++;
        this.score+= score;
        this.lastPlay = lastPlay;
        gameLogs.add(gameLog);

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
