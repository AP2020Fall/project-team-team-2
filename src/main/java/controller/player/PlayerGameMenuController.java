package controller.player;

import javafx.scene.image.Image;
import model.Entry.GameLogEntry;
import model.Game;
import model.GameLog;
import model.GameLogSummary;
import model.Player;
import view.StartGameView;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerGameMenuController extends PlayerMainMenuLayoutController {
    private final Game game;
    private final Player player;

    public PlayerGameMenuController(Game game) {
        this.game = Objects.requireNonNull(game, "Game passed to GameMenuController is null.");
        this.player = Objects.requireNonNull(loggedIn,"Player passed to GameMenuController is null.");
    }

    public String showScoreBoard() {
        //returns the string of Scoreboard
        return game.getScoreboard().toString();
    }

    public String getDetails() {
        //returns game's details.
        return game.getDetails();
    }
    public boolean hasPlayedGame()
    {
        return player.getGameHistory(game.getName()) != null;
    }

    public ArrayList<GameLogEntry> getGameLog() {
        ArrayList<GameLogEntry> result = new ArrayList<>();
        GameLogSummary gameLogSummary = player.getGameHistory(game.getName());
        for (GameLog gameLog : gameLogSummary.getGameLogs()) {
            result.add(new GameLogEntry(gameLog));
        }
        return result;
    }

    public String getWinsCount() {
        //returns the number of times player won the game.
        //throws NullPointerException if the player hasn't played the game.
        GameLogSummary gameLogSummary= player.getGameHistory(game.getName());
        if(gameLogSummary == null)
            return "hasn't been played";
        else
            return String.valueOf(gameLogSummary.getFrequency());
    }

    public String getPlayedFrequency() {
        //returns the number of times player played the game.
        //throws NullPointerException if the player hasn't played the game.
        GameLogSummary gameLogSummary= player.getGameHistory(game.getName());
        if(gameLogSummary == null)
            return "hasn't been played";
        else
        return String.valueOf(gameLogSummary.getWins());
    }

    public void addToFavorites() {
        player.addFavouriteGame(game);
    }

    public void removeFromFavorites() {
        player.removeFavouriteGame(game);
    }

    public void runGame(ArrayList<String> usernames) {
        ArrayList<Player> players = new ArrayList<>();
        for(String username: usernames)
        {
            players.add(Objects.requireNonNull(Player.getPlayerByUsername(username),
                    "Username passed to runGame doesn't exist."));
        }
        new StartGameView(players);
    }

    public boolean isFavorite() {
        return player.getFavouriteGames().contains(game);
    }

    public Image getGameImage() {
        return game.getImage();
    }
}
