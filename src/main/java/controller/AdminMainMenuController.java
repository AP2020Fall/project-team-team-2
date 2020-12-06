package controller;

import model.Account;
import model.Admin;
import model.Event;
import model.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static controller.Controller.generateId;

public class AdminMainMenuController {
    private Admin admin;

    public AdminMainMenuController(Admin admin) {
        this.admin = admin;
    }

    public void addEvent(String gameName, LocalDateTime start, LocalDateTime end, int score) {
        Event event = new Event(gameName,start,end,score,generateId());
        Event.addEvent(event);
    }

    public ArrayList<Event> showEvents() {
        return Event.getEvents();
    }

    public void editEvent(String eventId, String field, String value) {
    }

    public void removeEvent(String eventId) {
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

    public Player getUser(String username) {
       Account account =  Account.getAccountByUsername(username);
       if (!(account instanceof Player))
           return null;
       else
           return (Player)account;
    }
}
