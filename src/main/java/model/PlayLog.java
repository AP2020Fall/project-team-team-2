package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class PlayLog {
    private final String gameName;
    private final ArrayList<Player> players;
    private final Player winner;
    private final LocalDateTime time;

    public PlayLog(String gameName,ArrayList<Player> players, Player winner, LocalDateTime time)
    {
        this.gameName =gameName;
        this.players = new ArrayList<>(players);
        this.winner = winner;
        this.time = time;
    }

    public Game getGame() {
        return Objects.requireNonNull(Game.getGameByGameName(gameName));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getWinner() {
        return winner;
    }


    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return  gameName + ": " + time +
                ", players=" + players +
                ", winner=" + winner;
    }
}
