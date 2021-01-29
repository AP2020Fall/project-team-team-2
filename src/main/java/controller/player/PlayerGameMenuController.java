package controller.player;

import controller.Controller;
import main.ClientInfo;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class PlayerGameMenuController extends Controller {
    private final Game game;
    private final Player player;

    public PlayerGameMenuController(ClientInfo clientInfo) {
        super(clientInfo);
        this.game = Game.getGameByGameName(clientInfo.getGameName());
        if (game == null)
            System.err.println("Game passed to GameMenuController is null.");
        this.player = Player.getPlayerByUsername(clientInfo.getLoggedInUsername());
        if (player == null)
            System.err.println("Player passed to GameMenuController is null.");
    }

    public Boolean isFavorite() {
        return player.getFavouriteGames().contains(game);
    }

    public void removeFromFavorites() {
        player.removeFavouriteGame(game);
    }

    public void addToFavorites() {
        player.addFavouriteGame(game);
    }

    public String getDetails() {
        //returns game's details.
        return game.getDetails();
    }

    public String getPlayedFrequency() {
        //returns the number of times player played the game.
        //throws NullPointerException if the player hasn't played the game.
        GameLogSummary gameLogSummary = player.getGameHistory(game.getName());
        if (gameLogSummary == null)
            return "hasn't been played";
        else
            return String.valueOf(gameLogSummary.getFrequency());
    }

    public String getWinsCount() {
        //returns the number of times player won the game.
        //throws NullPointerException if the player hasn't played the game.
        GameLogSummary gameLogSummary = player.getGameHistory(game.getName());
        if (gameLogSummary == null)
            return "hasn't been played";
        else
            return String.valueOf(gameLogSummary.getWins());
    }

    public String getImage() {
        return game.getImageURL();
    }

    public Boolean hasPlayedGame() {
        return player.getGameHistory(game.getName()) != null;
    }

    public ArrayList<GameLog> getGameLog() {
        return player.getGameHistory(game.getName()).getGameLogs();
    }

    public ArrayList<Scoreboard.Entry> getScoreboard() {
        return game.getScoreboard().getScoreboard();

    }

    public String getCasualEvent() {
        Event casual = new Event(game.getName(), LocalDate.now(), LocalDate.now(), 0, generateId(),
                "Casual", game.getImage());
        Event.addEvent(casual);
        return casual.getEventId();
    }
}
