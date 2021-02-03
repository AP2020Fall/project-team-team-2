package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class PlayLog {
    private final String gameName;
    private final ArrayList<String> players;
    private final String winner;
    private final LocalDateTime time;
    private final String playLogId;

    public PlayLog(String gameName,ArrayList<Player> players, Player winner, LocalDateTime time,String playLogId)
    {
        this.gameName =gameName;
        this.players = new ArrayList<>();
        for(Player player :players)
            this.players.add(player.getUsername());
        this.winner = winner.getUsername();
        this.time = time;
        this.playLogId = playLogId;
    }

    /*public Game getGame() {
        return Game.getGameByGameName(gameName);
    }*/

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
    }

    @Override
    public String toString() {
        return  gameName + ": " + time +
                ", players=" + players +
                ", winner=" + winner;
    }

}
