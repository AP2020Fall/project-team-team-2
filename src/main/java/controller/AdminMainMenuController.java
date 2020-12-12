package controller;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static controller.Controller.generateId;

public class AdminMainMenuController extends Controller {
    private Admin admin;

    public AdminMainMenuController(Admin admin) {
        this.admin = Objects.requireNonNull(admin, "Admin passed to AdminMainMenuController is null.");
    }

    public void addEvent(String gameName, String start, String end, int score) {
        //creates a new event and adds it to the list of events.
        LocalDate startDate = createLocalDate(start);
        LocalDate endDate = createLocalDate(end);
        Event event = new Event(gameName, startDate, endDate, score, generateId());
        Event.addEvent(event);
    }

    public ArrayList<Event> showEvents() {
        // returns the list events.
        return Event.getEvents();
    }

    public void editEventStart(String eventId, String start) {
        LocalDate startDate = createLocalDate(start);
        //changes the start field of eventId.
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventId), "EventId passed" +
                " AdminMainMenuController.editEventStart doesn't exist.").setStart(startDate);
    }

    public void editEventEnd(String eventId, String end) {
        LocalDate endDate = createLocalDate(end);
        //changes the end field of eventId.
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventId), "EventId passed" +
                " AdminMainMenuController.editEventEnd doesn't exist.").setEnd(endDate);
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
        Player player = Objects.requireNonNull(Player.getPlayerByUsername(username),
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


    public String showUserProfile(String username) {
        //returns the username.toString().
        //throws NullPointerException if username doesn't exist.
        return Objects.requireNonNull(Player.getPlayerByUsername(username),
                "Username passed to AdminMainMenu.showUserProfile doesn't exist.").toString();
    }

    public ArrayList<String> showUsers() {
        //returns the list of players' usernames
        ArrayList<String> usernames = new ArrayList<>();
        for (Player player : Account.getAllPlayers())
            usernames.add(player.getUsername());
        return usernames;
    }

    public void sendMessage(String message, String username) {
        //sends message to username
        //throws NullPointerException if username doesn't exist
        Message message1 = new Message(message, generateId(),
                Objects.requireNonNull(Player.getPlayerByUsername(username),
                        "Username passed to AdminMainMenu.sendMessage doesn't exist."), null);
        message1.sendMessage();
    }

    public void addGame(String gameName, String gameDetail)
    {
        //creates a game and adds it to the list of games
        Game game = new Game(gameName,generateId(),gameDetail);
        Game.addGame(game);
    }

    public void changeGameName(String currGameName,String newGameName)
    {
        Game game =Objects.requireNonNull( Game.getGameByGameName(currGameName),
                "Game passed to AdminMainMenuController.changeGameName doesn't exist.");
        game.setName(newGameName);
    }

    public void removeGame(String gameName)
    {
        Game game =Objects.requireNonNull( Game.getGameByGameName(gameName),
                "Game passed to AdminMainMenuController.removeGame doesn't exist.");
        Game.getGames().remove(game);
    }

}
