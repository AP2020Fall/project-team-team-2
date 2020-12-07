package controller;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static controller.Controller.generateId;

public class AdminMainMenuController {
    private Admin admin;

    public AdminMainMenuController(Admin admin) {
        this.admin = Objects.requireNonNull(admin, "Admin passed to AdminMainMenuController is null.");
    }

    public void addEvent(String gameName, LocalDate start, LocalDate end, int score) {
        //creates a new event and adds it to the list of events.
        Event event = new Event(gameName, start, end, score, generateId());
        Event.addEvent(event);
    }

    public ArrayList<Event> showEvents() {
        // returns the list events.
        return Event.getEvents();
    }

    public void editEventStart(String eventId, LocalDate start) {
        //changes the start field of eventId.
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventId), "EventId passed" +
                " AdminMainMenuController.editEventStart doesn't exist.").setStart(start);
    }

    public void editEventEnd(String eventId, LocalDate end) {
        //changes the end field of eventId.
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventId), "EventId passed" +
                " AdminMainMenuController.editEventEnd doesn't exist.").setEnd(end);
    }

    public void editEventScore(String eventId, int score) {
        //changes the score field of eventId.
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventId), "EventId passed" +
                " AdminMainMenuController.editEventScore doesn't exist.").setScore(score);
    }

    public void editEventGameName(String eventId, String gameName) {
        //changes the gameName field of eventId.
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventId), "EventId passed" +
                " AdminMainMenuController.editEventGameName doesn't exist.").setGameName(gameName);
    }

    public boolean eventExists(String eventId) {
        //returns true if the eventId exists, else false
        return Event.getEventById(eventId) != null;
    }

    public void removeEvent(String eventId) {
        //removes eventId from the list
        Event.getEvents().removeIf(a -> a.getEventId().equals(eventId));
    }

    public void addSuggestion(String username, String gameName) {
        //adds a game suggestion to username
        //throws NullPointerException if username doesn't exist.
        Player player =Objects.requireNonNull( Player.getPlayerByUsername(username),
                "Username passed to AdminMainMenuController.addSuggestion doesn't exist.");
        Suggestion suggestion = new Suggestion(gameName, generateId(), player);
        player.setSuggestion(suggestion);
        Suggestion.addSuggestion(suggestion);
    }

    public ArrayList<Suggestion> showSuggestions() {
        //returns the list of suggestions
        return Suggestion.getSuggestions();
    }

    public void removeSuggestion(String suggestionId) {
        //removes a suggestion for the player's suggestions
        //throws NullPointerException if suggestionId doesn't exist.
        Suggestion suggestion = Objects.requireNonNull(Suggestion.getSuggestionById(suggestionId),
                "SuggestionId passed to AdminMainMenuController.removeSuggestion doesn't exist.");
        suggestion.getPlayer().removeSuggestion();
        Suggestion.getSuggestions().remove(suggestion);
    }


    public Player showUserProfile(String username) {
        //returns the username and null if it doesn't exist
        return Player.getPlayerByUsername(username);
    }

    public ArrayList<String> showUsers() {
        //returns the list of players' usernames
        ArrayList<String> usernames = new ArrayList<>();
        for (Player player : Account.getAllPlayers())
            usernames.add(player.getUsername());
        return usernames;
    }
}
