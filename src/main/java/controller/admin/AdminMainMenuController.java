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
    }//done

    public void setUsername(String username) {
        admin.setUsername(username);
    }//done

    public String getPassword() {
        return admin.getPassword();
    }//done

    public void setPassword(String password) {
        admin.setPassword(password);
    }//done

    public String getDate() {
        return String.valueOf(admin.getDayOfRegister());
    }//done

    public String getFirstName() {
        return admin.getFirstName();
    }//done

    public void setFirstName(String firstName) {
        admin.setFirstName(firstName);
    }//done

    public String getLastName() {
        return admin.getLastName();
    }//done

    public void setLastName(String lastName) {
        admin.setLastName(lastName);
    }//done

    public String getEmail() {
        return admin.getEmail();
    }//done

    public void setEmail(String email) {
        admin.setEmail(email);
    }//done

    public String getPhoneNumber() {
        return admin.getPhoneNumber();
    }//done

    public void setPhoneNumber(String phoneNumber) {
        admin.setPhoneNumber(phoneNumber);
    }//done

    public Image getImage() {
        return admin.getImage();
    }//done

    public void setImage(String url) {
        admin.setImage(url);
    }//done

    public ArrayList<Suggestion> getSuggestions() {
        return Suggestion.getSuggestions();
    }//done

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
