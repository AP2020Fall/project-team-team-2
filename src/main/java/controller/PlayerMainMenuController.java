package controller;

import model.Game;
import model.Message;
import model.Player;
import model.Suggestion;
import view.GameMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class PlayerMainMenuController extends Controller {

    private final Player player;

    public PlayerMainMenuController(Player player) {
        this.player = Objects.requireNonNull(player,
                "Player passed to PlayerMainMenuController is null.");
    }

    public int showPoints() {
        //returns the score of player.
        return player.getScore();
    }

    public ArrayList<String> showFavoriteGames() {
        //returns the names of player's favourite games.
        ArrayList<String> result = new ArrayList<>();
        for (Game game : player.getFavouriteGames())
            result.add(game.getName());
        return result;

    }

    public ArrayList<String> showPlatoBotsMessages() {
        //returns the list of messages send to player.
        ArrayList<String> result = new ArrayList<>();
        for (Message message : player.getMessages())
            result.add(message.toString());
        Collections.reverse(result);
        return result;
    }

    public String showLastPlayed() {
        //returns the last game played by player.
        //throws NullPointerException if gameLogs is empty.
        return Objects.requireNonNull(player.getLastGamePlayed(),
                "Player hasn't played any games.").getGameName();
    }

    public boolean hasPlayerPlayed()
    {
        //checks if player has played a game
        return player.getLastGamePlayed() != null;
    }

    public ArrayList<String> showAdminsSuggestions() {
        //returns the list of suggested games to player.
        ArrayList<String> result = new ArrayList<>();
        for (Suggestion suggestion : player.getSuggestions())
            result.add(suggestion.toString());
        return result;
    }

    public void chooseSuggestedGame(String gameName) {
        //goes to the GameMenu of the suggested game.
        //throws NullPointerException if gameName is not suggested.
        Suggestion suggestion = Objects.requireNonNull(player.getSuggestionByGameName(gameName),
                "Game passed to PlayerMainMenuController.chooseSuggestedGame" +
                        " hasn't been suggested to player.");
        new GameMenu(player, suggestion.getGame());
    }

    public boolean isGameSuggested(String gameName) {
        //checks if the gameName is suggested.
        return player.suggestionExists(gameName);
    }

}
