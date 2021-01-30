package controller.admin;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import main.ClientInfo;
import model.Entry.EventEntry;
import model.Entry.GameEntry;
import model.Event;
import model.Game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class AdminGamesMenuController extends Controller {

    public AdminGamesMenuController(ClientInfo clientInfo) {
        super(clientInfo);
    }

    public ArrayList<Game> getGames() {
        return Game.getGames();
    }

    public void deleteGame(String gameName) {
        //removes the selected games from the list.
        //throws NullPointerException if gameName doesn't exist.
        Objects.requireNonNull(Game.getGameByGameName(gameName),
                "Game passed to AdminGamesController.removeGame doesn't exist.").delete();
    }


    public ArrayList<Event> getUpcomingEvents() {
        ArrayList<Event> result = new ArrayList<>();
        for (Event event : Event.getEvents()) {
            if (event.getStart().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) {
                result.add(event);
            }
        }
        return result;
    }

    public ArrayList<EventEntry> getPastEvents() {
        ArrayList<EventEntry> result = new ArrayList<>();
        for (Event event : Event.getEvents()) {
            if (event.getEnd().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
                result.add(new EventEntry(event));
            }
        }
        return result;
    }

    public ArrayList<EventEntry> getOngoingEvents() {
        ArrayList<EventEntry> result = new ArrayList<>();
        for (Event event : Event.getEvents()) {
            if (event.getStart().isBefore(ChronoLocalDate.from(LocalDateTime.now())) &&
                    event.getEnd().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) {
                result.add(new EventEntry(event));
            }
        }
        return result;
    }

    public void deleteEvent(String eventId) {
        //removes the selected events from the list
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventId),
                "Event passed AdminMainMenuController.deleteEvent doesn't exist.").delete();
    }


    public Event getEvent(EventEntry eventEntry) {
        //gets the selected event
        //throws NullPointerException if eventId doesn't exist.
        return  Objects.requireNonNull(Event.getEventById(eventEntry.getEventId()),
                "Event passed AdminMainMenuController.getEvent doesn't exist.");
    }

    public Game getGame(GameEntry gameEntry) {
        //gets the selected event
        //throws NullPointerException if eventId doesn't exist.
        return  Objects.requireNonNull(Game.getGameByGameName(gameEntry.getName()),
                "Game passed AdminMainMenuController.getGame doesn't exist.");
    }
}
