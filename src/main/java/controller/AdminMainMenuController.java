package controller;

import model.Account;
import model.Admin;
import model.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AdminMainMenuController {
    private Admin admin;

    public AdminMainMenuController(Admin admin) {
        this.admin = admin;
    }

    public void addEvent(String gameName, LocalDateTime start, LocalDateTime end, int score) {
    }

    public void showEvents() {
    }

    public void editEvent(int eventId, String field, String value) {
    }

    public void removeEvent(int eventId) {
    }

    public void addSuggestion(String username, String gameName) {
    }

    public void showSuggestions() {
    }

    public void removeSuggestion(int suggestionId) {
    }

    public void showUsers() {
    }

    public void showUserProfile(String username) {
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for(Player player : Account.getAllPlayers())
            usernames.add(player.getUsername());
        return usernames;
    }
}
