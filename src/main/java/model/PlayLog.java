package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class PlayLog {
    private final Game game;
    private final ArrayList<Player> players;
    private final Player winner;
    private final LocalDate time;

    public PlayLog(Game game,ArrayList<Player> players, Player winner, LocalDate time)
    {
        this.game =game;
        this.players = new ArrayList<>(players);
        this.winner = winner;
        this.time = time;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getWinner() {
        return winner;
    }


    public LocalDate getTime() {
        return time;
    }

    @Override
    public String toString() {
        return  game + ": " + time +
                ", players=" + players +
                ", winner=" + winner;
    }
}
