package controller;

import model.Game;
import model.PlayLog;
import model.Player;
import view.StartGameView;

import java.util.ArrayList;
import java.util.Objects;

public class GameMenuController extends Controller{
    private final Game game;
    private final Player player;

    public GameMenuController(Game game,Player player) {
        this.game = Objects.requireNonNull(game, "Game passed to GameMenuController is null.");
        this.player = Objects.requireNonNull(player,"Player passed to GameMenuController is null.");
    }

    public String showScoreBoard() {
        //returns the string of Scoreboard
        return game.getScoreboard().toString();
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

    public void runGame(ArrayList<String> usernames) {
        ArrayList<Player> players = new ArrayList<>();
        for(String username: usernames)
        {
            players.add(Objects.requireNonNull(Player.getPlayerByUsername(username),
                    "Username passed to runGame doesn't exist."));
        }
        new StartGameView(players);
    }

    public int showPoints() {
        //returns the score of player in the game.
        //throws NullPointerException if the player hasn't played the game.
        return Objects.requireNonNull(player.getGameHistory(game.getName()),
                "Player hasn't played this game.").getScore();
    }

    public boolean canRunGame()
    {
        return game.getName().equals("Risk");
    }

    public boolean hasPlayedGame()
    {
        return player.getGameHistory(game.getName()) != null;
    }
}
