package controller;

import model.*;
import view.GameMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class PlayerMainMenuController extends Controller {

    private final Player player;

    public PlayerMainMenuController() {
        this.player = Objects.requireNonNull(((Player) loggedIn),
                "Player passed to PlayerMainMenuController is null.");
    }

    public int showPoints() {
        //returns the score of player.
        return player.getScore();
    }

    public ArrayList<GameEntry> favoriteGames() {
        //returns the names of player's favourite games.
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Game game : player.getFavouriteGames())
            result.add(new GameEntry(game));
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

    public GameEntry lastGamePlayed() {
        //returns the last game played by player.
        //throws NullPointerException if gameLogs is empty.
        return new GameEntry(Objects.requireNonNull(Game.getGameByGameName(
                Objects.requireNonNull(player.getLastGamePlayed(), "Player hasn't played any games.")
                        .getGameName()),"Game doesn't exist"));
    }

    public boolean hasPlayerPlayed()
    {
        //checks if player has played a game
        return player.getLastGamePlayed() != null;
    }

    public ArrayList<GameEntry> adminsSuggestions() {
        //returns the list of suggested games to player.
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Suggestion suggestion : player.getSuggestions())
            result.add(new GameEntry(suggestion.getGame()));
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
    public ArrayList<EventEntry> getEvents()
    {
        ArrayList<EventEntry> result = new ArrayList<>();
        for(Event event: Event.getEvents())
            result.add(new EventEntry(event));
        return result;
    }
}
