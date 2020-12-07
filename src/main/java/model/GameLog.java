package model;

public class GameLog {

    private int frequency;
    private int wins;
    private String gameLogId;
    private int score;

    private Game game;

    public int getFrequency() {
        return frequency;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses()
    {
        return frequency - wins;
    }

    public Game getGame()
    {
        return game;
    }

    public String getGameLogId() {
        return gameLogId;
    }

    public int getScore() {
        return score;
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


}
