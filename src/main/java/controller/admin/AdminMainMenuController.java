package controller.admin;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import main.ClientInfo;
import model.*;
import model.Entry.PlayerEntry;
import model.Entry.SuggestionEntry;
import view.TabHandler;
import view.admin.AdminProfileView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class AdminMainMenuController extends AdminMainMenuLayoutController {
    private final Admin admin;

    public AdminMainMenuController(ClientInfo clientInfo) {
        super(clientInfo);
        this.admin = Objects.requireNonNull(loggedIn,
                "Admin passed to AdminMainMenuController is null.");
    }

    public String getUsername() {
        return admin.getUsername();
    }

    public String getPassword() {
        return admin.getPassword();
    }

    public void setPassword(String password) {
        admin.setPassword(password);
    }

    public String getDate() {
        return String.valueOf(admin.getDayOfRegister());
    }

    public String getFirstName() {
        return admin.getFirstName();
    }

    public void setFirstName(String firstName) {
        admin.setFirstName(firstName);
    }

    public String getLastName() {
        return admin.getLastName();
    }

    public void setLastName(String lastName) {
        admin.setLastName(lastName);
    }

    public String getEmail() {
        return admin.getEmail();
    }

    public void setEmail(String email) {
        admin.setEmail(email);
    }

    public String getPhoneNumber() {
        return admin.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        admin.setPhoneNumber(phoneNumber);
    }

    public Image getImage() {
        return admin.getImage();
    }

    public void setImage(String url) {
        admin.setImage(url);
    }

    public void setUsername(String username) {
        admin.setUsername(username);
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

    public ObservableList<MenuItem> getSearchQuery(String searchQuery) {
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        ArrayList<Player> top5Players = new ArrayList<>();//usernameFuzzySearchTop5(searchQuery);
        for (Player player : top5Players) {
            MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(event -> TabHandler.getTabHandler().push(new AdminProfileView(player)));
            menuItem.setText(player.getUsername());
            result.add(menuItem);
        }
        if (result.isEmpty()) {
            result.add(new Menu("No similar user found."));
        }
        return result;
    }

    public Player getPlayer(PlayerEntry playerEntry) {
        return Player.getPlayerByUsername(playerEntry.getName());
    }
}
