package model.Entry;

import model.Scoreboard;

public class ScoreboardEntry {
    private String playerName;
    //private int score;
    private int wins;
    private int numPlayed;
    public ScoreboardEntry(Scoreboard.Entry entry)
    {
        playerName = entry.getPlayerName();
        wins = entry.getWins();
        numPlayed = entry.getNumPlayed();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getWins() {
        return wins;
    }

    public int getNumPlayed() {
        return numPlayed;
    }
}
