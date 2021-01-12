package controller.admin;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
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

    public String getUsername(){
        return admin.getUsername();
    }

    public String getPassword(){
        return admin.getPassword();
    }

    public String getDate(){
        return String.valueOf(admin.getDayOfRegister());
    }

    public String getFirstName(){
        return admin.getFirstName();
    }

    public String getLastName(){
        return admin.getLastName();
    }

    public String getEmail(){
        return admin.getEmail();
    }

    public String getPhoneNumber(){
        return admin.getPhoneNumber();
    }
/*
    public Image getImage(){
        return admin.getImage();
    }*/

    public void setUsername(String username){admin.setUsername(username);}
    public void setPassword(String password) {
        admin.setPassword(password);
    }
    public void setFirstName(String firstName) {
        admin.setFirstName(firstName);
    }
    public void setLastName(String lastName) {
        admin.setLastName(lastName);
    }
    public void setEmail(String email) {
        admin.setEmail(email);
    }
    public void setPhoneNumber(String phoneNumber) {
        admin.setPhoneNumber(phoneNumber);
    }
    public void setImage(Image image){//admin.setImage(image);
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
