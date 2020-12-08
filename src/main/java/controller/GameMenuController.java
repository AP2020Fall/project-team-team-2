package controller;

import model.Game;
import model.PlayLog;
import model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class GameMenuController {
    private Game game;
    private Player player;

    public GameMenuController(Game game,Player player) {
        this.game = Objects.requireNonNull(game, "Game passed to GameMenuController is null.");
        this.player = Objects.requireNonNull(player,"Player passed to GameMenuController is null.");
    }

    public void showScoreBoard() {
    }

    public String showDetails() {
        //returns game's details.
        return game.getDetails();
    }

    public ArrayList<String> showLog() {
        //returns the game's logs.
        ArrayList<String> result = new ArrayList<>();
        for(PlayLog playLog : game.getPlayLogs())
            result.add(playLog.toString());
        return result;
    }

    public int showWinsCount() {
        //returns the number of times player won the game.
        //throws NullPointerException if the player hasn't played the game.
        return Objects.requireNonNull(player.getGameHistory(game.getName()),
                "Player hasn't played this game.").getWins();
    }

    public int showPlayedCount() {
        //returns the number of times player played the game.
        //throws NullPointerException if the player hasn't played the game.
        return Objects.requireNonNull(player.getGameHistory(game.getName()),
                "Player hasn't played this game.").getFrequency();
    }

    public void addToFavorites() {
        player.getFavouriteGames().add(game);
    }

    public void runGame() {
    }

    public int showPoints() {
        //returns the score of player in the game.
        //throws NullPointerException if the player hasn't played the game.
        return Objects.requireNonNull(player.getGameHistory(game.getName()),
                "Player hasn't played this game.").getScore();
    }
}
