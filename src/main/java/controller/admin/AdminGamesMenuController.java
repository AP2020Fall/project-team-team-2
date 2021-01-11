package controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import model.Entry.EventEntry;
import model.Entry.GameEntry;
import model.Event;
import model.Game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class AdminGamesMenuController extends AdminMainMenuLayoutController{

    public ObservableList<GameEntry> getGames() {
       ObservableList<GameEntry> result = FXCollections.observableArrayList();
        for (Game game : Game.getGames())
            result.add(new GameEntry(game));
        return result;
    }

    public void deleteGame(GameEntry gameEntry) {
        //removes gameName from the list.
        //throws NullPointerException if gameName doesn't exist.
        Objects.requireNonNull(Game.getGameByGameName(gameEntry.getName()),
                "Game passed to AdminMainMenuController.removeGame doesn't exist.").delete();
    }
    public void addGame(String gameName, String gameDetail, Image gameImage) {
        //creates a game and adds it to the list of games
        Game game = new Game(gameName, generateId(), gameDetail,gameImage);
        Game.addGame(game);
    }

    public ArrayList<EventEntry> getUpcomingEvents() {
        ArrayList<EventEntry> result = new ArrayList<>();
        for (Event event : Event.getEvents()) {
            if (event.getStart().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) {
                result.add(new EventEntry(event));
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

    public void deleteEvent(EventEntry eventEntry) {
        //removes eventId from the list
        //throws NullPointerException if eventId doesn't exist.
        Objects.requireNonNull(Event.getEventById(eventEntry.getEventId()),
                "EventId passed AdminMainMenuController.removeEvent doesn't exist.").delete();
    }
    public void addEvent(String gameName, LocalDate start, LocalDate end, int score,Image image) {
        //creates a new event and adds it to the list of events.
        //LocalDate startDate = createLocalDate(start);
       // LocalDate endDate = createLocalDate(end);
        Event event = new Event(gameName, start, end, score, generateId(),image);
        Event.addEvent(event);
    }
}
