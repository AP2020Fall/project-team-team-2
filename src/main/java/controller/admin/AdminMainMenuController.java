package controller.admin;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import model.Entry.PlayerEntry;
import model.Entry.SuggestionEntry;

import java.util.Objects;

public class AdminMainMenuController extends AdminMainMenuLayoutController {
    private final Admin admin;

    public AdminMainMenuController()
    {
        this.admin = Objects.requireNonNull(loggedIn,
                "Admin passed to AdminMainMenuController is null.");
    }

    public Admin getAdmin(){
        return admin;
    }

    public ObservableList<SuggestionEntry> getSuggestions() {
        ObservableList<SuggestionEntry> result = FXCollections.observableArrayList();
        for (Suggestion suggestion : Suggestion.getSuggestions()) {
            result.add(new SuggestionEntry(suggestion));
        }
        return result;
    }

    public void deleteSuggestion(SuggestionEntry suggestionEntry) {
        //removes a suggestion for the player's suggestions
        //throws NullPointerException if suggestionId doesn't exist.
        Objects.requireNonNull(Suggestion.getSuggestionById(suggestionEntry.getSuggestionId()),
                "SuggestionId passed to AdminMainMenuController.removeSuggestion doesn't exist.").delete();
    }

    public void addSuggestion(String username, String gameName) {
        //adds gameName suggestion to username
        //throws NullPointerException if username doesn't exist or game doesnt exist.
        Player player = Objects.requireNonNull(Player.getPlayerByUsername(username),
                "Username passed to AdminMainMenuController.addSuggestion doesn't exist.");
        Game game = Objects.requireNonNull(Game.getGameByGameName(gameName),
                "Game passed to AdminMainMenuController.addSuggestion doesn't exist.");
        Suggestion suggestion = new Suggestion(game, generateId(), player);
        player.addSuggestion(suggestion);
        Suggestion.addSuggestion(suggestion);
    }
    public boolean playerBeenSuggested(String username, String gameName) {
        //checks if gameName been suggested to username
        //throws NullPointerException if username doesn't exist or game doesn't exist.
        Player player = Objects.requireNonNull(Player.getPlayerByUsername(username),
                "Username passed to AdminMainMenuController.playerBeenSuggested doesn't exist.");
         Objects.requireNonNull(Game.getGameByGameName(gameName),
                "Game passed to AdminMainMenuController.playerBeenSuggested doesn't exist.");
        return player.suggestionExists(gameName);
    }

    public ObservableList<PlayerEntry> getPlayers() {
        ObservableList<PlayerEntry> result = FXCollections.observableArrayList();
        for (Player player : Account.getAllPlayers()) {
            result.add(new PlayerEntry(player));
        }
        return result;
    }
}
