package controller;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static controller.Controller.generateId;

public class AdminMainMenuController {
    private Admin admin;

    public AdminMainMenuController(Admin admin) {
        this.admin = admin;
    }

    public void addEvent(String gameName, LocalDate start, LocalDate end, int score) {
        Event event = new Event(gameName, start, end, score, generateId());
        Event.addEvent(event);
    }

    public ArrayList<Event> showEvents() {
        return Event.getEvents();
    }

    public void editEventStart(String eventId, LocalDate start) {
        Event.getEventById(eventId).setStart(start);
    }
    public void editEventEnd(String eventId, LocalDate end) {
        Event.getEventById(eventId).setEnd(end);
    }
    public void editEventScore(String eventId, int score) {
        Event.getEventById(eventId).setScore(score);
    }
    public void editEventGameName(String eventId, String gameName) {
        Event.getEventById(eventId).setGameName(gameName);
    }

    public boolean eventExists(String eventId)
    {
        return Event.getEventById(eventId) != null;
    }
    public void removeEvent(String eventId) {
        Event.getEvents().removeIf(a -> a.getEventId().equals(eventId));
    }

    public void addSuggestion(String username, String gameName) {
        Player player = Player.getPlayerByUsername(username);
        Suggestion suggestion = new Suggestion(gameName, generateId(), player);
        player.setSuggestion(suggestion);
        Suggestion.addSuggestion(suggestion);
    }

    public ArrayList<Suggestion> showSuggestions() {
        return Suggestion.getSuggestions();
    }

    public void removeSuggestion(String suggestionId) {
        Suggestion suggestion = Suggestion.getSuggestionById(suggestionId);
        suggestion.getPlayer().removeSuggestion();
        Suggestion.getSuggestions().remove(suggestion);
    }


    public Player showUserProfile(String username) {
        return Player.getPlayerByUsername(username);
    }

    public ArrayList<String> showUsers() {
        ArrayList<String> usernames = new ArrayList<>();
        for (Player player : Account.getAllPlayers())
            usernames.add(player.getUsername());
        return usernames;
    }
}
