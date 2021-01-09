package controller.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import model.Entry.*;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerMainMenuController extends PlayerMainMenuLayoutController {

    private final Player player;

    public PlayerMainMenuController() {
        this.player = Objects.requireNonNull(loggedIn,
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
        GameLogSummary gameLogSummary = player.getGameLogLastGamePlayed();
        if (gameLogSummary == null) {
            return new GameEntry("No game has been played");
        }
        else
        {
            return new GameEntry(Objects.requireNonNull(Game.getGameByGameName(gameLogSummary.getGameName()),
                    "Game doesn't exist"));
        }
    }

    public ArrayList<GameEntry> adminsSuggestions() {
        //returns the list of suggested games to player.
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Suggestion suggestion : player.getSuggestions())
            result.add(new GameEntry(suggestion.getGame()));
        return result;
    }

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
        return String.valueOf( Objects.requireNonNull(player,
                "Logged in account in Controller.showPoints is null").getScore());
    }
}