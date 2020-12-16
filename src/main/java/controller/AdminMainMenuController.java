package controller;

import model.*;

import java.io.File;
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
        Objects.requireNonNull(Event.getEventById(eventId),
                "EventId passed AdminMainMenuController.editEventStart doesn't exist.").setStart(startDate);
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

    public void removeEvent(String eventId) {
        //removes eventId from the list
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventId),
                "EventId passed AdminMainMenuController.removeEvent doesn't exist.").delete();
    }

    public void sendMessage(String messageText) {
        //sends message to all player
        Message message = new Message(messageText, generateId(), null);
        message.sendMessage();
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
        //throws NullPointerException if username doesn't exist or game doesnt exist.
        Player player = Objects.requireNonNull(Player.getPlayerByUsername(username),
                "Username passed to AdminMainMenuController.playerBeenSuggested doesn't exist.");
        Game game = Objects.requireNonNull(Game.getGameByGameName(gameName),
                "Game passed to AdminMainMenuController.playerBeenSuggested doesn't exist.");
        return player.suggestionExists(gameName);
    }

    public ArrayList<Suggestion> showSuggestions() {
        //returns the list of suggestions
        return Suggestion.getSuggestions();
    }

    public void removeSuggestion(String suggestionId) {

        //removes a suggestion for the player's suggestions
        //throws NullPointerException if suggestionId doesn't exist.
         Objects.requireNonNull(Suggestion.getSuggestionById(suggestionId),
                "SuggestionId passed to AdminMainMenuController.removeSuggestion doesn't exist.").delete();

    }

    public void addGame(String gameName, String gameDetail) {
        //creates a game and adds it to the list of games
        Game game = new Game(gameName, generateId(), gameDetail);
        Game.addGame(game);
    }

    public void editGameName(String gameName, String newGameName) {
        //edits gameName's name.
        //throws NullPointerException if gameName doesn't exist.
        Game game = Objects.requireNonNull(Game.getGameByGameName(gameName),
                "Game passed to AdminMainMenuController.changeGameName doesn't exist.");
        game.setName(newGameName);
    }

    public void editGameDetail(String gameName, String newGameDetail) {
        //edits gameName's detail.
        //throws NullPointerException if gameName doesn't exist.
        Game game = Objects.requireNonNull(Game.getGameByGameName(gameName),
                "Game passed to AdminMainMenuController.changeGameName doesn't exist.");
        game.setDetails(newGameDetail);
    }

    public void removeGame(String gameName) {
        //removes gameName from the list.
        //throws NullPointerException if gameName doesn't exist.

        Objects.requireNonNull(Game.getGameByGameName(gameName),
                "Game passed to AdminMainMenuController.removeGame doesn't exist.").delete();
    }

    public ArrayList<String> viewGames() {
        ArrayList<String> result = new ArrayList<>();
        for (Game game : Game.getGames())
            result.add(game.toString());
        return result;
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
}
