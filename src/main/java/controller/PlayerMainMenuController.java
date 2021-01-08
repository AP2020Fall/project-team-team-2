package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import model.Entry.*;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerMainMenuController extends Controller {

    private final Player player;

    public PlayerMainMenuController() {
        this.player = Objects.requireNonNull(((Player) loggedIn),
                "Player passed to PlayerMainMenuController is null.");
    }



    public ArrayList<GameEntry> favoriteGames() {
        //returns the names of player's favourite games.
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Game game : player.getFavouriteGames())
            result.add(new GameEntry(game));
        return result;

    }

    public GameEntry lastGamePlayed() {
        //returns the last game played by player.
        //throws NullPointerException if gameLogs is empty.
        return new GameEntry(Objects.requireNonNull(Game.getGameByGameName(
                Objects.requireNonNull(player.getGameLogLastGamePlayed(), "Player hasn't played any games.")
                        .getGameName()),"Game doesn't exist"));
    }

    public boolean hasPlayerPlayed()
    {
        //checks if player has played a game
        return player.getGameLogLastGamePlayed() != null;
    }

    public ArrayList<GameEntry> adminsSuggestions() {
        //returns the list of suggested games to player.
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Suggestion suggestion : player.getSuggestions())
            result.add(new GameEntry(suggestion.getGame()));
        return result;
    }

/*    public ArrayList<String> showPlatoBotsMessages() {
        //returns the list of messages send to player.
        ArrayList<String> result = new ArrayList<>();
        for (Message message : player.getMessages())
            result.add(message.toString());
        Collections.reverse(result);
        return result;
    }
    public void chooseSuggestedGame(String gameName) {
        //goes to the GameMenu of the suggested game.
        //throws NullPointerException if gameName is not suggested.
        Suggestion suggestion = Objects.requireNonNull(player.getSuggestionByGameName(gameName),
                "Game passed to PlayerMainMenuController.chooseSuggestedGame" +
                        " hasn't been suggested to player.");
        //new GameMenu(player, suggestion.getGame());
    }

    public boolean isGameSuggested(String gameName) {
        //checks if the gameName is suggested.
        return player.suggestionExists(gameName);
    }
 */
    public ArrayList<EventEntry> getEvents()
    {
        ArrayList<EventEntry> result = new ArrayList<>();
        for(Event event: Event.getEvents())
            result.add(new EventEntry(event));
        return result;
    }
    public String getBio() {
        return player.getBio();
    }
    public String getMoney() {
        return String.valueOf(player.getMoney());
    }
    public String getDate() {
        return String.valueOf(player.getDayOfRegister());
    }
    public String getWins() {
        return String.valueOf(player.getNumberOfWins());
    }
    public String getFriendCount() {
        return String.valueOf(player.getFriends().size());
    }

    public void logout()
    {
        loggedIn = null;
    }

    public ObservableList<GameLogSummaryEntry> getGameHistory() {
        ObservableList<GameLogSummaryEntry> result = FXCollections.observableArrayList();
        for (GameLogSummary gameLog : player.getGameLogSummaries())
            result.add(new GameLogSummaryEntry(gameLog));
        return result;
    }


    public ObservableList<GameEntry> getGames() {
        ObservableList<GameEntry> result = FXCollections.observableArrayList();
        for(Game game: Game.getGames())
            result.add(new GameEntry(game));
        return result;
    }

    public String showPoints() {
        //returns the score of player.
        return String.valueOf( Objects.requireNonNull(((Player)loggedIn),
                "Logged in account in Controller.showPoints is null").getScore());
    }
}
